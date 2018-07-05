package net.comecraft.factionlogo;

import java.util.Optional;

import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.PlaceholderHook;
import net.comecraft.factionlogo.wrapper.Faction;
import net.comecraft.factionlogo.wrapper.FactionsPlugin;

public class FactionLogoPlaceholder extends PlaceholderHook {

	private FactionsPlugin factions;
	private Logos logos;
	
	/**
	 * Create a new placeholder hook with a factions plugin and logos
	 * @param factions This placeholder hook's factions plugin.
	 * @param logos This placeholder hook's logos.
	 */
	public FactionLogoPlaceholder(FactionsPlugin factions, Logos logos) {
		super();
		this.factions = factions;
		this.logos = logos;
	}

	@Override
	public String onRequest(OfflinePlayer player, String identifier) {
		Optional<Faction> faction = factions.getFactionByMember(player);
		if (!faction.isPresent()) return "";
		
		String result = logos.getLogo(faction.get().getId());
		
		if (result == null) return "";
		
		// placeholder: %factionslogo_space%
		if (identifier.equals("space")) return result + " ";
		return result;		
	}
}
