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

	public boolean isAvailable(String logo) {
		// TODO
		return false;
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
	
	public void cleanupLogos() {
		// TODO
	}
}
