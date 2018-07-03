package net.comecraft.factionlogo;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;

import net.redstoneore.legacyfactions.FactionsPluginBase;
import net.redstoneore.legacyfactions.entity.FPlayer;
import net.redstoneore.legacyfactions.entity.FactionColl;

/**
 * A wrapper for redstone/LegacyFactions
 */
public class LegacyFactionsWrapper extends FactionsPlugin {

	/**
	 * Create a wrapper for redstone/LegacyFactions
	 * 
	 * @param plugin
	 *            The plugin to wrap
	 * @throws ClassCastException
	 *             if the plugin is not an instance of
	 *             redstoneore.legacyfactions.FactionsPluginBase
	 */
	protected LegacyFactionsWrapper(Plugin plugin) throws ClassCastException {
		if (!(plugin instanceof FactionsPluginBase)) {
			throw new ClassCastException();
		}
	}

	@Override
	public Collection<Faction> getFactionCollection() {
		
		// For each redstoneore.legacyfactions.entity.Faction, map it to our wrapper class.
		return FactionColl.all().stream().map(LegacyFactionsWrapper::getFactionWrapper).collect(Collectors.toSet());
	}

	@Override
	public Optional<Faction> getFactionByName(String name) {
		Faction faction = getFactionWrapper(FactionColl.get(name));
		return Optional.ofNullable(faction);
	}

	@Override
	public Optional<Faction> getFactionById(String id) {
		Faction faction = getFactionWrapper(FactionColl.get(id));
		return Optional.ofNullable(faction);
	}

	@Override
	public Optional<Faction> getFactionByMember(Player player) {
		Faction faction = getFactionWrapper(FactionColl.get(player));
		return Optional.ofNullable(faction);
	}
	
	/**
	 * Wraps a legacyfactions faction with our wrapper class.
	 * @param faction a legacyfactions faction
	 * @return An instance of our faction wrapper that represents that faction.
	 */
	protected static Faction getFactionWrapper(net.redstoneore.legacyfactions.entity.Faction faction) {
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
