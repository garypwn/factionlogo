package net.comecraft.factionlogo;

import java.util.Optional;

import org.bukkit.OfflinePlayer;
import me.clip.placeholderapi.PlaceholderHook;
import net.comecraft.factionlogo.wrapper.Faction;
import net.comecraft.factionlogo.wrapper.FactionsPlugin;

public class FactionLogoPlaceholder extends PlaceholderHook {

	private FactionsPlugin factions;
	private Logos logos;
	
	@Override
	public String onRequest(OfflinePlayer player, String identifier) {
		Optional<Faction> faction = factions.getFactionByMember(player);
		if (!faction.isPresent()) return "";
		
		String result = logos.getLogo(faction.get().getId());
		
		// placeholder: %factionslogo_space%
		if (identifier.equals("space")) return result + " ";
		return result;		
	}
}
