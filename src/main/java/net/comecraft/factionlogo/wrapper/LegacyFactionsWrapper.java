package net.comecraft.factionlogo.wrapper;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;

import net.comecraft.factionlogo.SetLogo;
import net.redstoneore.legacyfactions.FactionsPluginBase;
import net.redstoneore.legacyfactions.cmd.CmdFactions;
import net.redstoneore.legacyfactions.cmd.FCommand;
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
	protected LegacyFactionsWrapper(Plugin plugin) throws InvalidPluginException {
		if (!(plugin instanceof FactionsPluginBase)) {
			throw new InvalidPluginException();
		}
	}

	@Override
	public Collection<Faction> getFactionCollection() {
		return FactionColl.all()
				.stream()
				
				// Wrap each legacyfaction with our wrapper
				.map(LegacyFactionsWrapper::getFactionWrapper)
				.collect(Collectors.toSet());
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
		
		// (Hopefully) get the player's faction
		net.redstoneore.legacyfactions.entity.Faction legacyFaction = FactionColl.get(player);
		
		// If unsuccessful, return empty
		if (legacyFaction == null) return Optional.empty();
		
		// If player is factionless, return empty
		if (FactionColl.get().getWilderness().equals(legacyFaction)) return Optional.empty();
		
		// Return our faction wrapper
		Faction faction = getFactionWrapper(legacyFaction);
		return Optional.of(faction);
	}
	
	@Override
	public void registerSubCommand(SetLogo command) {
		CmdFactions.get().addSubCommand(new FCommand() {

			@Override
			public String getUsageTranslation() {
				// TODO Auto-generated method stub
				return null;
			}

			@Override
			public void perform() {
				// TODO Auto-generated method stub
				
			}		
		});
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
