package net.comecraft.factionlogo.wrapper;

import java.util.Collection;

import org.bukkit.entity.Player;

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
	 * Gets all members of this faction.
	 * @return A collection of all the players in this faction.
	 */
	public Collection<Player> getMembers();
}
