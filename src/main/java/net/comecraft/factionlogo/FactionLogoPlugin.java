package net.comecraft.factionlogo;

import java.io.File;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import net.comecraft.factionlogo.wrapper.FactionsPlugin;

public class FactionLogoPlugin extends JavaPlugin {
	
	private FactionsPlugin factions;
	private Logos logos;
	private FileConfiguration lang;
	private SetLogo setLogoCommand;
	
	
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
		
		// Get resource files
		this.lang = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "lang.yml"));
		
		FileConfiguration logoFile = YamlConfiguration.loadConfiguration(new File(getDataFolder(), "logos.yml"));
		
		this.logos = new Logos(logoFile, factions);
		
		// Register set logo command
		this.setLogoCommand = new SetLogo(lang, factions, logos);
		getCommand("setlogo").setExecutor(setLogoCommand);
		factions.registerSubCommand(setLogoCommand);
	}
}
