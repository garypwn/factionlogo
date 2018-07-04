package net.comecraft.factionlogo;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import com.massivecraft.factions.FPlayer;
import com.massivecraft.factions.FPlayers;
import com.massivecraft.factions.Factions;
import com.massivecraft.factions.P;

/**
 * A wrapper for drtshock/factions
 */
public class FactionsUUIDWrapper extends FactionsPlugin {

	/**
	 * Create a wrapper for drtshock/factions
	 * 
	 * @param plugin
	 *            The plugin to wrap
	 * @throws ClassCastException
	 *             if the plugin is not an instance of com.massivecraft.factions
	 *             
	 */
	protected FactionsUUIDWrapper(Plugin plugin) throws ClassCastException {
		if (!(plugin instanceof P)) {
			throw new ClassCastException();
		}
	}
	
	@Override
	public Collection<Faction> getFactionCollection() {
		return Factions.getInstance().getAllFactions()
				.stream()
				
				// Wrap each faction with our wrapper
				.map(FactionsUUIDWrapper::getFactionWrapper)
				.collect(Collectors.toSet());
	}

	@Override
	public Optional<Faction> getFactionByName(String name) {
		
		// Try to get the faction by name
		com.massivecraft.factions.Faction mFaction = Factions.getInstance().getBestTagMatch(name);
		if (mFaction == null) return Optional.empty();
		
		return Optional.of(getFactionWrapper(mFaction));
	}

	@Override
	public Optional<Faction> getFactionById(String id) {
		
		// Try to get the faction by id
		com.massivecraft.factions.Faction mFaction = Factions.getInstance().getFactionById(id);
		if (mFaction == null) return Optional.empty();
		
		return Optional.of(getFactionWrapper(mFaction));
	}

	@Override
	public Optional<Faction> getFactionByMember(Player player) {
		
		// (Hopefully) get the FPlayer
		FPlayer fplayer = FPlayers.getInstance().getByPlayer(player);
		if (fplayer == null) return Optional.empty();

		// get the player's faction
		com.massivecraft.factions.Faction mFaction = fplayer.getFaction();
		if (mFaction == null) return Optional.empty();

		// If player is factionless, return empty
		if (mFaction.isWilderness()) return Optional.empty();

		// Return our faction wrapper
		Faction faction = getFactionWrapper(mFaction);
		return Optional.of(faction);
	}

	/**
	 * Wraps a massivecraft faction with our wrapper class.
	 * @param faction a massivecraft faction
	 * @return An instance of our faction wrapper that represents that faction.
	 */
	protected static Faction getFactionWrapper(com.massivecraft.factions.Faction faction) {
		return new Faction() {

			@Override
			public String getId() {
				return faction.getId();
			}

			@Override
			public String getName() {
				return faction.getTag();
			}

			@Override
			public Collection<Player> getMembers() {
				return faction.getFPlayers().stream().map(FPlayer::getPlayer)
						.collect(Collectors.toSet());
			}
		};
	}
}
