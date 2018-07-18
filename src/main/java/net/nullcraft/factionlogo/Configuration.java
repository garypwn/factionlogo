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
	
	/**
	 * Constructs a Configuration
	 * @param plugin The plugin to get the config files for
	 */
	public Configuration(JavaPlugin plugin) {
		
		this.plugin = plugin;
		
		// Save default values
		plugin.saveDefaultConfig();
		
		// Get resource files
		File langFile = new File(plugin.getDataFolder(), "lang.yml");
		if (!langFile.exists()) {
			plugin.saveResource("lang.yml", false);
		}
		this.lang = YamlConfiguration.loadConfiguration(langFile);
	}
	
	/**
	 * Get the pattern for a valid logo
	 * @return A Pattern that matches valid logos
	 */
	public Pattern getValidLogoPattern() {
		return Pattern.compile(plugin.getConfig().getString("valid-logo-pattern"));
	}
	
	/**
	 * Gets a lang string from the lang file
	 * @param path The path for the string
	 * @return The string
	 */
	public String getLangString(String path) {
		return lang.getString(path);
	}
	
	/**
	 * Gets the command aliases to be used with /f [alias]
	 * @return A collection containing the f command aliases.
	 */
	public Collection<String> getFCommandAliases() {
		return plugin.getConfig().getStringList("fcommand-aliases");
	}
}
