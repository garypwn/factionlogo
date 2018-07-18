package net.nullcraft.factionlogo;

import java.io.File;

import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import net.nullcraft.factionlogo.wrapper.FactionsPlugin;

public class FactionLogoPlugin extends JavaPlugin {
	
	private FactionsPlugin factions;
	private LogoCollection logos;
	private SetLogo setLogoCommand;
	
	@Override
	public void onEnable() {
		
		// Try to get the server's factions plugin
		getLogger().info("Attempting to hook into factions");
		factions = FactionsPlugin.getFactionsPlugin(getServer());
		
		// Disable if there is no factions plugin
		if (factions == null) {
			getLogger().severe("Could not find a factions plugin. Disabling factionlogo.");
			getServer().getPluginManager().disablePlugin(this);
		}
		
		// Initialize config
		Configuration config = new Configuration(this);
		
		File logoFile = new File(getDataFolder(), "logos.yml");
		this.logos = new LogoCollection(logoFile, factions);
		
		// Register set logo command
		this.setLogoCommand = new SetLogo(config, factions, logos);
		getCommand("setlogo").setExecutor(setLogoCommand);
		factions.registerSubCommand(setLogoCommand);

		// Try to hook into placeholderAPI
		if (getServer().getPluginManager().isPluginEnabled("PlaceholderAPI")) {
			PlaceholderAPI.registerPlaceholderHook(this.getName(), new FactionLogoPlaceholder(factions, logos));
			getLogger().info("Successfully hooked into placeholder API");
		} else {
			getLogger().warning("Could not find placeholder API.");
		}
	}
}
