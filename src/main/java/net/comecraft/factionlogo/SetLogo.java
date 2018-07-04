package net.comecraft.factionlogo;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import net.comecraft.factionlogo.wrapper.Faction;

public class SetLogo implements CommandExecutor {

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
}