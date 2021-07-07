package fr.em_i.blockmine.topkills;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.Paths;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.java.JavaPlugin;

import fr.em_i.blockmine.topkills.cmd.Classement;
import fr.em_i.blockmine.topkills.events.PlayerKillEvent;
import fr.em_i.blockmine.topkills.events.PlayerSendMessageEvent;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new PlayerKillEvent(this), this);
		getServer().getPluginManager().registerEvents(new PlayerSendMessageEvent(this), this);
		getCommand("classement").setExecutor(new Classement(this));

		Path path = Paths.get(getDataFolder().getPath() + "/Worlds");
		File fileConfig = new File(getDataFolder(), "Worlds/world/config.yml");
		File fileClassement = new File(getDataFolder(), "Worlds/world/classement.yml");
		
		if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.createDirectories(path);
				Files.createDirectories(Paths.get(getDataFolder().getPath() + "/Worlds/world"));
				
				fileConfig.createNewFile();
				YamlConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);

				config.set("msg.classement.title", "§7§m        §6§lTOP 10 KILLS§7§m        ");
				config.set("msg.classement.line", "§a#%rank% §r§b%username% §7- §6%amount_kills% kills (Niv.%level%)");
				config.set("msg.tchat.format", "[Niv.%level%] %rest%");
				config.set("int.level.limit", 100);
				config.set("int.level.ratio", 25);
				config.save(fileConfig);
				
				fileClassement.createNewFile();
				YamlConfiguration classement = YamlConfiguration.loadConfiguration(fileClassement);
				
				classement.set("kills.c45a3af5-ae9b-3f40-8895-99b37479ead8", 0);
				classement.save(fileClassement);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onDisable() {
	}

	public File getConfigFileOf(String worldName) {
		File file = new File(getDataFolder(), "Worlds/" + worldName + "/config.yml");
		if (!file.exists())
			return null;
		else
			return file;
	}
	
	public File getClassementFileOf(String worldName) {
		File file = new File(getDataFolder(), "Worlds/" + worldName + "/classement.yml");
		if (!file.exists())
			return null;
		else
			return file;
	}

	public FileConfiguration getConfigOf(File file) {
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public FileConfiguration getConfigOf(String worldName) {
		File file = getConfigFileOf(worldName);
		if (file == null)
			return null;
		else
			return YamlConfiguration.loadConfiguration(file);
	}
	
	public YamlConfiguration getClassementYamlOf(File file) {
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public YamlConfiguration getClassementYamlOf(String worldName) {
		File file = getClassementFileOf(worldName);
		if (file == null)
			return null;
		else
			return YamlConfiguration.loadConfiguration(file);
	}
}