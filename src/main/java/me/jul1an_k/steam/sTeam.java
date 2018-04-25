package me.jul1an_k.steam;

import java.io.File;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import me.jul1an_k.steam.metrics.Metrics;
import me.jul1an_k.steam.update.FileUpdate;
import net.milkbowl.vault.permission.Permission;

public class sTeam extends JavaPlugin {
	
	private final FileConfiguration fc = YamlConfiguration.loadConfiguration(new File("plugins/sTeam", "config.yml"));
	
	static String Prefix = "";
	static String noPerm = "";
	
	public void onEnable() {
		FileUpdate fu = new FileUpdate();
		fu.check();
		setupPermissions();
		saveDefaultConfig();
		this.getCommand("Team").setExecutor(new Team_Command());
		this.getCommand("sTeam").setExecutor(new sTeam_Command());
		
		System.out.println("[sTeam] Hooked into " + permission.getName() + " for groups!");
		
		Prefix = ChatColor.translateAlternateColorCodes('&', fc.getString("Prefix"));
		noPerm = ChatColor.translateAlternateColorCodes('&', fc.getString("NoPermission").replace("%prefix%", Prefix));
		
		new Metrics(this);
	}
	
	static Permission permission = null;
	
	private void setupPermissions() {
		RegisteredServiceProvider<Permission> permissionProvider = getServer().getServicesManager().getRegistration(net.milkbowl.vault.permission.Permission.class);
		if(permissionProvider != null) {
			permission = permissionProvider.getProvider();
		}
	}
	
}
