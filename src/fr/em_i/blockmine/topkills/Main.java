package fr.em_i.blockmine.topkills;

import java.io.File;
import java.io.IOException;

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
		
		File file = new File(getDataFolder(), "/classement.yml");
		if (!file.exists()) {
			try {
				file.createNewFile();
				YamlConfiguration classement = YamlConfiguration.loadConfiguration(file);
				
				try {
					classement.save(file);
				} catch (IOException e1) {
					e1.printStackTrace();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	@Override
	public void onDisable() {
	}
}
