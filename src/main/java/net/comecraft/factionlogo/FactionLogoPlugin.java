package net.comecraft.factionlogo;

import org.bukkit.plugin.java.JavaPlugin;

import net.comecraft.factionlogo.wrapper.FactionsPlugin;

public class FactionLogoPlugin extends JavaPlugin {
	
	private FactionsPlugin factions;
	
	@Override
	public void onEnable() {
		
		// Try to get the server's factions plugin
		getLogger().info("[factionlogo] Attempting to hook into factions");
		factions = FactionsPlugin.getFactionsPlugin(getServer());
		
		// Disable if there is no factions plugin
		if (factions == null) {
			getLogger().severe("[factionlogo] could not find a factions plugin. Disabling factionlogo.");
			getServer().getPluginManager().disablePlugin(this);
		}
	}
}
