package net.nullcraft.factionlogo;

import java.io.File;
import java.io.IOException;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import net.nullcraft.factionlogo.wrapper.FactionsPlugin;

public class LogoCollection {

	private final File logoFile;
	private final FileConfiguration logoConfiguration;
	private final FactionsPlugin factions;
	
	public LogoCollection(File logoFile, FactionsPlugin factions) {
		this.logoFile = logoFile;
		this.logoConfiguration = YamlConfiguration.loadConfiguration(logoFile);
		this.factions = factions;
	}

	/**
	 * Checks whether a logo is available.
	 * @param logo The logo to check the availiability of.
	 * @return true if the logo is available.
	 */
	public boolean isAvailable(String logo) {
		cleanupLogos();
		return !logoConfiguration.getValues(false).values().contains(logo);
	}
	
	/**
	 * Sets the logo for the given faction id.
	 * @param id The id of the faction to set the logo for.
	 * @param logo The faction's logo.
	 */
	public void setLogo(String id, String logo) {
		logoConfiguration.set(id, logo);
		try {
			logoConfiguration.save(logoFile);
		} catch (IOException e) {
			Bukkit.getLogger().warning("[factionlogo] Could not save to logos.yml");
		}
	}
	
	/**
	 * Gets the logo for the given faction id.
	 * @param id The id of the faction to get the logo for.
	 * @return The faction's logo.
	 */
	public String getLogo(String id) {
		return logoConfiguration.getString(id);
	}
	
	/**
	 * Remove logos for nonexistent factions.
	 */
	public void cleanupLogos() {
		
		logoConfiguration.getKeys(false).stream()

				// Filter out entries for factions that actually exist.
				.filter(faction -> !factions.getFactionById(faction).isPresent())

				// Remove entries for factions that don't exist.
				.forEach(id -> logoConfiguration.set(id, null));
	}
}
