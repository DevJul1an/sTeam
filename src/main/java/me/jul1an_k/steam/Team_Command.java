package me.jul1an_k.steam;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.OfflinePlayer;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

public class Team_Command implements CommandExecutor {

    private FileConfiguration config;

	@Override
	public boolean onCommand(CommandSender cs, Command cmd, String label, String[] args) {
        config = sTeam.getPlugin(sTeam.class).getConfig();

        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Header")));

        for(String group : sTeam.permission.getGroups()) {
            if(config.getStringList("EnabledGroups").contains(group)) {
                cs.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("GroupPrefix")) + getGroupName(group) + ChatColor.translateAlternateColorCodes('&', config.getString("GroupSuffix")));
                for(OfflinePlayer op : Bukkit.getOfflinePlayers()) {
                    if(sTeam.permission.playerInGroup("world", op, group)) {
                        String name = op.getName();

                        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("PlayerPrefix")) + name + ChatColor.translateAlternateColorCodes('&', config.getString("PlayerSuffix")) + isOnline(op, (Player) cs));
                    }
                }
                if(config.getStringList("Searching").contains(group)) {
                    cs.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("SearchingMessage")));
                }
            }
        }

        cs.sendMessage(ChatColor.translateAlternateColorCodes('&', config.getString("Footer")));

        return true;
	}
	
	private String isOnline(OfflinePlayer player, Player sender) {
		String online = ChatColor.translateAlternateColorCodes('&', sTeam.getPlugin(sTeam.class).getConfig().getString("PlayerOffline"));
		if(player.isOnline()) {
			Player p = (Player) player;
			if(sender.canSee(p)) {
				online = ChatColor.translateAlternateColorCodes('&', sTeam.getPlugin(sTeam.class).getConfig().getString("PlayerOnline"));
			} else {
				online = ChatColor.translateAlternateColorCodes('&', sTeam.getPlugin(sTeam.class).getConfig().getString("PlayerOffline"));
			}
		}
		return online;
	}
	
	private String getStringStartUPPERCASE(String message) {
		String a = message.substring(0, 1).toUpperCase();
		String b = message.substring(1, message.length());
		return a + b;
	}

    private String getGroupName(String name) {
        if(config.contains("Replace." + name)) {
            name = config.getString("Replace." + name);
        }

        if(config.getBoolean("FirstCharInGroupNameToUpperCase")) {
            name = getStringStartUPPERCASE(name);
        }

        return name;
    }
	
}
