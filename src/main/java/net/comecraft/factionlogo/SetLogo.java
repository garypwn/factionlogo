package net.comecraft.factionlogo;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Optional;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.comecraft.factionlogo.wrapper.Faction;
import net.comecraft.factionlogo.wrapper.FactionsPlugin;

public class SetLogo implements CommandExecutor {

	private FileConfiguration lang;
	private FactionsPlugin factions;
	private Logos logos;
	
	public SetLogo(FileConfiguration lang, FactionsPlugin factions, Logos logos) {
		super();
		this.lang = lang;
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
			target = factions.getFactionByMember(Bukkit.getPlayer(args[0]));
		}
		
		// Give up
		if (!target.isPresent()) {
			sender.sendMessage(lang.getString("setlogo.notfound"));
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
			player.sendMessage(lang.getString("setlogo.notfound"));
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
			sender.sendMessage(lang.getString("setlogo.taken"));
			return true;
		}
		
		// Check length
		if (logo.length() > 1) {
			sender.sendMessage(lang.getString("setlogo.wronglength"));
			return true;
		}
		
		// Check for illegal characters
		if (logo.contains("&")) {
			sender.sendMessage(lang.getString("setlogo.illegalcharacter"));
			return true;
		}
		
		// Set the new logo
		logos.setLogo(faction.getId(), logo);
		
		// Notify members
		String message = String.format(lang.getString("setlogo.success"), sender.getName(), faction.getName(), logo);
		faction.getMembers()
		.stream()
		.filter(player -> player.isOnline())
		.map(player -> player.getPlayer())
		.forEach(player -> player.sendMessage(message));
		
		return true;
	}
	
	public FileConfiguration getLang() {
		return lang;
	}

	/**
	 * Gets the list of /f [alias] aliases to be used in a FCommand.
	 * 
	 * @return A list containing the faction command aliases of this command.
	 */
	public Collection<String> getFCommandAliases() {
		ArrayList<String> aliases = new ArrayList<String>();
		aliases.add("logo");
		aliases.add("setlogo");
		aliases.add("icon");
		aliases.add("seticon");
		aliases.add("chattag");
		aliases.add("setchattag");
		return aliases;
	}

	public Collection<String> getFCommandArgs() {
		ArrayList<String> requiredArgs = new ArrayList<String>();
		requiredArgs.add("logo");
		return requiredArgs;
	}
}