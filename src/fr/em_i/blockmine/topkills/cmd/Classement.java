package fr.em_i.blockmine.topkills.cmd;

import java.io.File;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.em_i.blockmine.topkills.Main;
import fr.em_i.blockmine.topkills.tools.MapSorting;

public class Classement implements CommandExecutor {

	private HashMap<String, Integer> classement = new HashMap<>();
	private Main main;

	public Classement(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		classement.clear();
		
		File file = new File(main.getDataFolder(), "/classement.yml");
		YamlConfiguration fc = YamlConfiguration.loadConfiguration(file);
		Map<String, Object> configMap = fc.getConfigurationSection("Kills").getValues(true);
		
		for (Entry<String, Object> e : configMap.entrySet()) {
			UUID uuid = UUID.fromString(e.getKey());
			int kills = (int) e.getValue();
			classement.put(uuid.toString(), kills);
		}
		
		int rank = 0;
		sender.sendMessage(main.getConfig().getString("msg.classement.title"));
		
		for (Entry<String, Integer> entry : MapSorting.sortedValues(classement, Collections.reverseOrder())) {
			UUID uuid = UUID.fromString(entry.getKey());
			int kills = entry.getValue();
			rank++;
			if (rank > 10) {
				break;
			}
			
			int level = kills/25;
			String username = Bukkit.getOfflinePlayer(uuid).getName();
			
			sender.sendMessage(main.getConfig().getString("msg.classement.line").replace("%rank%", rank+"").replace("%username%", username).replace("%amount_kills%", kills+"").replace("%level%", level+""));
		}

		return false;
	}
}
