package net.nullcraft.factionlogo;

import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.nullcraft.factionlogo.wrapper.Faction;
import net.nullcraft.factionlogo.wrapper.FactionsPlugin;

public class SetLogo implements CommandExecutor {

	private Configuration config;
	private FactionsPlugin factions;
	private LogoCollection logos;
	
	public SetLogo(Configuration config, FactionsPlugin factions, LogoCollection logos) {
		super();
		this.config = config;
		this.factions = factions;
		this.logos = logos;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		
		//Check that sender included required arguments
		if (args.length < 2) return false;
		
		Optional<Faction> target;
		
		// Try to get the referenced faction
		target = factions.getFactionByName(args[0]);
		
		// Try by player name
		if (!target.isPresent()) {
			Player player = Bukkit.getPlayer(args[0]);
			if (player != null) {
				target = factions.getFactionByMember(Bukkit.getPlayer(args[0]));
			}
		}
		
		// Give up
		if (!target.isPresent()) {
			sender.sendMessage(config.getLangString("setlogo.notfound"));
			return false;
		}
		
		return setLogo(sender, target.get(), args[1]);
	}

	
	/**
	 * Called when a player tries to change their own faction logo.
	 * @param player The player.
	 * @param logo The desired logo.
	 */
	public void setLogo(Player player, String logo) {
		Optional<Faction> target = factions.getFactionByMember(player);
		
		// Player is factionless
		if (!target.isPresent()) {
			player.sendMessage(config.getLangString("setlogo.notfound"));
			return;
		}
		
		setLogo(player, target.get(), logo);
	}

	/**
	 * Tries to set a faction logo.
	 * @param sender The sender of the command.
	 * @param faction The faction to set the logo for.
	 * @param logo The new logo.
	 * @return true if successful.
	 */
	public boolean setLogo(CommandSender sender, Faction faction, String logo) {
		
		// Check availability
		if (!logos.isAvailable(logo)) {
			sender.sendMessage(config.getLangString("setlogo.taken"));
			return true;
		}
		
		// Check validity
		if (!config.getValidLogoPattern().matcher(logo).matches()) {
			sender.sendMessage(config.getLangString("setlogo.invalid"));
			return true;
		}
		
		// Set the new logo
		logos.setLogo(faction.getId(), logo);
		
		// Notify members
		String message = String.format(config.getLangString("setlogo.success"), sender.getName(), faction.getName(), logo);
		faction.getMembers()
		.stream()
		.filter(player -> player != null && player.isOnline())
		.map(player -> player.getPlayer())
		.forEach(player -> player.sendMessage(message));
		
		return true;
	}
	

	public Configuration getConfig() {
		return config;
	}
}