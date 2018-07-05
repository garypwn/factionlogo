package net.nullcraft.factionlogo.wrapper;

import java.util.Collection;
import java.util.Optional;
import java.util.stream.Collectors;

import org.bukkit.OfflinePlayer;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;

import net.nullcraft.factionlogo.SetLogo;
import net.redstoneore.legacyfactions.FactionsPluginBase;
import net.redstoneore.legacyfactions.Permission;
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
	public Optional<Faction> getFactionByMember(OfflinePlayer player) {
		
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

			// Initialize
			{
				// Add our command's aliases and args
				this.aliases.addAll(command.getFCommandAliases());
				this.requiredArgs.addAll(command.getFCommandArgs());

				// Copied from /f tag
				this.permission = Permission.TAG.getNode();
				this.disableOnLock = true;
				this.senderMustBePlayer = true;
				this.senderMustBeMember = false;
				this.senderMustBeModerator = true;
				this.senderMustBeAdmin = false;
			}
			
			@Override
			public String getUsageTranslation() {
				return command.getLang().getString("setlogo.description");
			}

			@Override
			public void perform() {
				command.setLogo(me, this.argAsString(0));
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
			public Collection<OfflinePlayer> getMembers() {
				return faction.getFPlayers().stream().map(FPlayer::getPlayer)
						.collect(Collectors.toSet());
			}
		};
	}
}
