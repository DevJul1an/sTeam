package me.jul1an_k.steam.update;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.lang.reflect.Field;
import java.net.URL;
import java.net.URLConnection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.SimpleCommandMap;
import org.bukkit.entity.Player;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.SimplePluginManager;
import org.bukkit.plugin.UnknownDependencyException;

import me.jul1an_k.steam.sTeam;

public class FileUpdate {
	
	public void check() {
		try {
			String newVersion = getLine("http://mc.jumpy91.de/Jul1anUpdater/sTeamUpdate.txt");
			String oldVersion = sTeam.getPlugin(sTeam.class).getDescription().getVersion();
			if(!newVersion.equals(oldVersion)) {
				System.out.println("[sTeam-AutoUpdater] Found Update! Newest Version: " + newVersion + "! Current Version: " + oldVersion);
				for(Player all : Bukkit.getOnlinePlayers()) {
					if(all.hasPermission("sTeam.Update")) {
						all.sendMessage("§aAn Update for sTeam is available!");
						all.sendMessage("§cCurrent Version: " + oldVersion);
						all.sendMessage("§2New Version: " + newVersion);
						all.sendMessage("§aDownloading update...");
					}
				}

				downloadUpdate(Bukkit.getConsoleSender());

				for(Player all : Bukkit.getOnlinePlayers()) {
					if(all.hasPermission("sTeam.Update")) {
						all.sendMessage("§aDownloaded update!");
					}
				}
			}
		} catch(IOException e) {
			e.printStackTrace();
		}
	}
	
	private String getLine(String URL) throws IOException {
		URL site = new URL(URL);
		URLConnection conn = site.openConnection();
		conn.addRequestProperty("User-Agent", "Chrome/52.0");
		
		InputStreamReader isr = new InputStreamReader(conn.getInputStream());
		BufferedReader br = new BufferedReader(isr);
		
		return br.readLine();
	}
	
	public boolean downloadUpdate(final CommandSender sender) {
		String path = "http://mc.jumpy91.de/Jul1anUpdater/sTeam.jar";
		
		boolean success = false;
		String pluginPath = "update/" + sTeam.getPlugin(sTeam.class).getDescription().getName() + ".jar";

		try {
			URL site = new URL(path);
			URLConnection conn = site.openConnection();
			conn.addRequestProperty("User-Agent", "Chrome/52.0");

			InputStream is = new BufferedInputStream(conn.getInputStream());
			OutputStream os = new BufferedOutputStream(new FileOutputStream(pluginPath));

			byte[] chunk = new byte[1024];
			int chunkSize;
			int count = -1;
			while((chunkSize = is.read(chunk)) != -1) {
				os.write(chunk, 0, chunkSize);
				count++;
			}
			os.flush(); // Necessary for Java < 6
			os.close();
			is.close();

			sender.sendMessage("§aDownloading...");

			sender.sendMessage("§aPlugin downloaded! (" + count / 1024 + "KB)");
			sender.sendMessage("§aUpdate successfully. Please restart the server to apply the update.");
			success = true;

		} catch(Exception e) {
			sender.sendMessage("§cFailed to update: " + e.getMessage());
		}

		return success;
	}
	
}
