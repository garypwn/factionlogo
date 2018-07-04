package net.comecraft.factionlogo;

import org.bukkit.configuration.file.FileConfiguration;

import net.comecraft.factionlogo.wrapper.FactionsPlugin;

public class Logos {

	private final FileConfiguration logoFile;
	private final FactionsPlugin factions;
	
	public Logos(FileConfiguration logoFile, FactionsPlugin factions) {
		this.logoFile = logoFile;
		this.factions = factions;
	}

	/**
	 * Checks whether a logo is available.
	 * @param logo The logo to check the availiability of.
	 * @return true if the logo is available.
	 */
	public boolean isAvailable(String logo) {
		cleanupLogos();
		return !logoFile.getValues(false).values().contains(logo);
	}
	
	/**
	 * Sets the logo for the given faction id.
	 * @param id The id of the faction to set the logo for.
	 * @param logo The faction's logo.
	 */
	public void setLogo(String id, String logo) {
		logoFile.set(id, logo);
	}
	
	/**
	 * Gets the logo for the given faction id.
	 * @param id The id of the faction to get the logo for.
	 * @return The faction's logo.
	 */
	public String getLogo(String id) {
		return logoFile.getString(id);
	}
	
	/**
	 * Remove logos for nonexistent factions.
	 */
	public void cleanupLogos() {
		
		logoFile.getKeys(false).stream()

				// Filter out entries for factions that actually exist.
				.filter(faction -> !factions.getFactionById(faction).isPresent())

				// Remove entries for factions that don't exist.
				.forEach(id -> logoFile.set(id, null));
	}
}
