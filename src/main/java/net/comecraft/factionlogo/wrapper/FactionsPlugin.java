package net.comecraft.factionlogo.wrapper;

import java.util.Collection;
import java.util.Optional;

import org.bukkit.Server;
import org.bukkit.entity.Player;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;

/**
 * FactionsPlugin is a wrapper for the server's factions plugin. It provides
 * consistent API methods regardless of which factions plugin the server has
 * installed.
 */
public abstract class FactionsPlugin {

	/**
	 * Gets a collection containing all factions on this server.
	 * 
	 * @return The Collection of Factions.
	 */
	public abstract Collection<Faction> getFactionCollection();

	/**
	 * Gets the best match for a faction name.
	 * 
	 * @param name
	 *            The name of the faction.
	 * @return The faction, or an empty optional of no match could be found.
	 */
	public abstract Optional<Faction> getFactionByName(String name);

	/**
	 * Gets a faction from a unique identifier.
	 * 
	 * @param id
	 *            The factions unique identifier.
	 * @return The faction, or an empty optional if no faction with this id exists.
	 */
	public abstract Optional<Faction> getFactionById(String id);

	/**
	 * Gets a the faction that a player is in.
	 * 
	 * @param player
	 *            The player to get the faction for.
	 * @return The player's faction, or an empty optional if the player is
	 *         factionless.
	 */
	public abstract Optional<Faction> getFactionByMember(Player player);

	/**
	 * Gets the FactionsPlugin for a server.
	 * 
	 * @param server
	 *            The server to get the FactionsPlugin for.
	 * @return A wrapper for this server's factions plugin, or null if there is no
	 *         supported factions plugin installed.
	 * @throws InvalidPluginException 
	 */
	public static FactionsPlugin getFactionsPlugin(Server server) {
		
		// Check for legacy factions
		Plugin legacyFactions = server.getPluginManager().getPlugin("LegacyFactions");
		if (legacyFactions != null) {
			try {
				return new LegacyFactionsWrapper(legacyFactions);
			} catch (InvalidPluginException e) {
				server.getLogger().warning("[factionlogo] failed to hook into LegacyFactions");
			}
		}
		
		// Check for factionsuuid/massivecraft factions
		Plugin factions = server.getPluginManager().getPlugin("Factions");
		if (!(factions == null)) {
			try {
				return new FactionsUUIDWrapper(factions);
			} catch (InvalidPluginException e) {
				server.getLogger().warning("[factionlogo] failed to hook into Factions");
			}
		}
		return null;	
	}
}
