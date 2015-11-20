package vg.civcraft.mc.mercury.config;

import java.io.File;

import vg.civcraft.mc.mercury.MercuryAPI;

public class MercuryConfigManager {
	public static void initialize() {
		if (MercuryConfigManager.inBukkit()) {
			config_ = new BukkitConfiguration();
		} else if (MercuryConfigManager.inBungee()) {
			config_ = new BungeeConfiguration();
		} else {
			File file = new File("mercury_cfg.json");
			if (!file.exists()) {
				JsonConfiguration c = new JsonConfiguration();
				c.save(file);
				config_ = c;
			}
			else
				config_ = JsonConfiguration.load(file);
		}
	}

	public static String getHost(){
		return config_.getHost();
	}
	
	public static String getUserName(){
		return config_.getUserName();
	}

	public static String getPassword(){
		return config_.getPassword();
	}

	public static Integer getPort(){
		return config_.getPort();
	}

	public static String getServerName(){
		return config_.getServerName();
	}

	public static String getServiceHandler(){
		return config_.getServiceHandler();
	}

	public static ServerType getServerType() {
		if (MercuryConfigManager.serverType_ != null) {
			return MercuryConfigManager.serverType_;
		}
		try {
			Class.forName("org.bukkit.Bukkit");
			MercuryConfigManager.serverType_ = ServerType.Bukkit;
			return ServerType.Bukkit;
		} catch(ClassNotFoundException ex) {}
		try {
			Class.forName("net.md_5.bungee.BungeeCord");
			MercuryConfigManager.serverType_ = ServerType.Bungee;
			return ServerType.Bungee;
		} catch(ClassNotFoundException ex) {}
		MercuryConfigManager.serverType_ = ServerType.Standalone;
		return ServerType.Standalone;
	}

	public static boolean inBukkit() {
		return MercuryConfigManager.getServerType() == ServerType.Bukkit;
	}

	public static boolean inBungee() {
		return MercuryConfigManager.getServerType() == ServerType.Bungee;
	}

	public static boolean isStandalone() {
		return MercuryConfigManager.getServerType() == ServerType.Standalone;
	}

	public enum ServerType {
		Bukkit,
		Bungee,
		Standalone,
	};

	private static Configuration config_ = null;
	private static ServerType serverType_ = null;
}
