package net.nullcraft.factionlogo.wrapper;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.kingdoms.constants.kingdom.Kingdom;
import org.kingdoms.constants.kingdom.OfflineKingdom;
import org.kingdoms.main.Kingdoms;
import org.kingdoms.manager.game.GameManagement;

import net.nullcraft.factionlogo.SetLogo;

public class KingdomsWrapper extends FactionsPlugin {

	
	/**
	 * Create a wrapper for kingdoms
	 * 
	 * @param plugin
	 *            The plugin to wrap
	 * @throws ClassCastException
	 *             if the plugin is not an instance of
	 *             kingdoms
	 */
	protected KingdomsWrapper(Plugin plugin) throws InvalidPluginException {
		if (!(plugin instanceof Kingdoms)) {
			throw new InvalidPluginException();
		}
	}
	
	@Override
	public Collection<Faction> getFactionCollection() {
		return GameManagement.getKingdomManager()
				.getKingdomList()
				.values()
				.stream()
				.map(KingdomsWrapper::getFactionWrapper)
				.collect(Collectors.toSet());
	}

	@Override
	public Optional<Faction> getFactionByName(String name) {
		Kingdom kingdom = GameManagement.getKingdomManager().getOrLoadKingdom(name);
		return Optional.ofNullable(getFactionWrapper(kingdom));
	}

	@Override
	public Optional<Faction> getFactionById(String id) {
		Kingdom kingdom = GameManagement.getKingdomManager().getOrLoadKingdom(id);
		return Optional.ofNullable(getFactionWrapper(kingdom));
	}

	@Override
	public Optional<Faction> getFactionByMember(OfflinePlayer player) {
		GameManagement.getPlayerManager().getOfflineKingdomPlayer(player);
		return null;
	}

	@Override
	public void registerSubCommand(SetLogo command) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Wraps a kingdom with our wrapper class.
	 * @param kingdom a kingdom
	 * @return An instance of our faction wrapper that represents that kingdom.
	 */
	protected static Faction getFactionWrapper(OfflineKingdom kingdom) {
		return new Faction() {

			@Override
			public String getId() {
				return kingdom.getKingdomName();
			}

			@Override
			public String getName() {
				return kingdom.getKingdomName();
			}

			@Override
			public Collection<OfflinePlayer> getMembers() {
				return kingdom.getMembersList().stream().map(Bukkit.getServer()::getOfflinePlayer)
						.collect(Collectors.toSet());
			}
		};
	}
}
