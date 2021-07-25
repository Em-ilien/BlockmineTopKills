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

import fr.em_i.blockmine.topkills.cmd.Level;
import fr.em_i.blockmine.topkills.cmd.Ranking;
import fr.em_i.blockmine.topkills.events.PlayerKillEvent;
import fr.em_i.blockmine.topkills.events.PlayerSendMessageEvent;

public class Main extends JavaPlugin {

	@Override
	public void onEnable() {
		saveDefaultConfig();
		
		getServer().getPluginManager().registerEvents(new PlayerKillEvent(this), this);
		getServer().getPluginManager().registerEvents(new PlayerSendMessageEvent(this), this);
		getCommand("classement").setExecutor(new Ranking(this));
		getCommand("level").setExecutor(new Level(this));

		Path path = Paths.get(getDataFolder().getPath() + "/Worlds");
		File fileConfig = new File(getDataFolder(), "Worlds/world/config.yml");
		File fileRanking = new File(getDataFolder(), "Worlds/world/ranking.yml");
		
		if (Files.notExists(path, LinkOption.NOFOLLOW_LINKS)) {
			try {
				Files.createDirectories(path);
				Files.createDirectories(Paths.get(getDataFolder().getPath() + "/Worlds/world"));
				
				fileConfig.createNewFile();
				YamlConfiguration config = YamlConfiguration.loadConfiguration(fileConfig);

				config.set("msg.ranking.title", "§7§m        §6§lTOP 10 KILLS§7§m        ");
				config.set("msg.ranking.line", "§a#%rank% §r§b%username% §7- §6%amount_kills% kills (Niv.%level%)");
				config.set("msg.tchat.format", "[Niv.%level%] %rest%");
				config.set("msg.rankup.message", "§aVous atteignez le level %level% ! §lFélicitations !");
				config.set("msg.print.level", "§aVotre level actuel : %level%");
				config.set("msg.error.ranking.empty", "§eLe classement est vide.");
				config.set("int.level.limit", 100);
				config.set("int.level.ratio", 25);
				config.set("int.top.amount", 10);
				config.save(fileConfig);
				
				fileRanking.createNewFile();
				YamlConfiguration ranking = YamlConfiguration.loadConfiguration(fileRanking);
				
				ranking.save(fileRanking);
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
	
	public File getRankingFileOf(String worldName) {
		File file = new File(getDataFolder(), "Worlds/" + worldName + "/ranking.yml");
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
	
	public YamlConfiguration getRankingYamlOf(File file) {
		return YamlConfiguration.loadConfiguration(file);
	}
	
	public YamlConfiguration getRankingYamlOf(String worldName) {
		File file = getRankingFileOf(worldName);
		if (file == null)
			return null;
		else
			return YamlConfiguration.loadConfiguration(file);
	}
}