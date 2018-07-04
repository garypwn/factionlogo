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
	
	public void setLogo(String id, String logo) {
		// TODO
	}
	
	public String getLogo(String id) {
		// TODO
		return null;
	}
	
	public void cleanupLogos() {
		// TODO
	}
}
