package net.nullcraft.factionlogo.wrapper;

import java.util.Collection;

import org.bukkit.OfflinePlayer;

/**
 * Faction represents a faction of any faction plugin type.
 */
public interface Faction {
	
	/**
	 * Gets the unique identifier for this faction.
	 * @return The unique id for this faction.
	 */
	public String getId();
	
	/**
	 * Gets the name of this faction.
	 * @return The name of this faction.
	 */
	public String getName();
	
	/**
	 * Gets all members of this faction, online or offline.
	 * @return A collection of all the players in this faction.
	 */
	public Collection<OfflinePlayer> getMembers();
}
