package net.nullcraft.factionlogo;

import java.io.File;
import java.util.Collection;
import java.util.regex.Pattern;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Our plugin configuration, by default located in config.yml
 */
public class Configuration {
	
	private JavaPlugin plugin;
	private FileConfiguration lang;
	
	public Configuration(JavaPlugin plugin) {
		
		this.plugin = plugin;
		
		// Get resource files
		File langFile = new File(plugin.getDataFolder(), "lang.yml");
		if (!langFile.exists()) {
			plugin.saveResource("lang.yml", false);
		}
		this.lang = YamlConfiguration.loadConfiguration(langFile);
	}
	
	public Pattern getValidLogoPattern() {
		// TODO
		return null;
	}
	
	public String getLangString(String path) {
		// TODO
		return null;
	}
	
	public Collection<String> getFCommandAliases() {
		// TODO
		return null;
	}
}
