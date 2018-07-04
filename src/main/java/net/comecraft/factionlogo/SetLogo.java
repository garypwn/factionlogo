package net.comecraft.factionlogo;

import java.util.ArrayList;
import java.util.Collection;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import net.comecraft.factionlogo.wrapper.Faction;

public class SetLogo implements CommandExecutor {

	private FileConfiguration langFile;
	
	public SetLogo(FileConfiguration lang) {
		super();
		this.langFile = lang;
	}
	
	@Override
	public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
		// TODO Auto-generated method stub
		return false;
	}

	public void setLogo(Faction faction, String logo) {
		// TODO
	}

	public void setLogo(Player player, String logo) {
		// TODO
	}

	/**
	 * Gets the list of /f [alias] aliases to be used in a FCommand.
	 * 
	 * @return A list containing the faction command aliases of this command.
	 */
	public Collection<String> getFCommandAliases() {
		ArrayList<String> aliases = new ArrayList<String>();
		aliases.add("logo");
		aliases.add("setlogo");
		aliases.add("icon");
		aliases.add("seticon");
		aliases.add("chattag");
		aliases.add("setchattag");
		return aliases;
	}

	public Collection<String> getFCommandArgs() {
		ArrayList<String> requiredArgs = new ArrayList<String>();
		requiredArgs.add("logo");
		return requiredArgs;
	}

	public FileConfiguration getLang() {
		return langFile;
	}
}