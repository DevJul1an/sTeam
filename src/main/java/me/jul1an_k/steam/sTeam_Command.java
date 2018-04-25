package me.jul1an_k.steam;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.UnknownDependencyException;

import me.jul1an_k.steam.update.FileUpdate;

public class sTeam_Command implements CommandExecutor {
	
	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
		if(args.length == 1) {
		    if(args[0].equalsIgnoreCase("update")) {
                if(!cs.hasPermission("sTeam.Update")) {
                    cs.sendMessage(sTeam.noPerm);
                    return true;
                }

                FileUpdate fu = new FileUpdate();
                fu.downloadUpdate(cs);
			} else {
				cs.sendMessage(sTeam.Prefix + "§7/sTeam Update");
			}
		} else {
			cs.sendMessage(sTeam.Prefix + "§7/sTeam Update");
		}
		return true;
	}
	
}
