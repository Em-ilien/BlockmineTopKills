package fr.em_i.blockmine.topkills.cmd;

import java.util.Collections;

import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.UUID;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.em_i.blockmine.topkills.Main;
import fr.em_i.blockmine.topkills.tools.MapSorting;

public class Ranking implements CommandExecutor {

	private HashMap<String, Integer> rankingMap = new HashMap<>();
	private Main main;

	public Ranking(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Error : console");
			return false;
		}
		
		final Player p = (Player) sender;
		final String worldName = p.getWorld().getName();
		rankingMap.clear();
		
		final FileConfiguration ranking = main.getRankingYamlOf(worldName);
		final FileConfiguration config = main.getConfigOf(worldName);
		
		if (ranking == null) {
			p.sendMessage(main.getConfig().getString("msg.error.world.noconfigured"));
			return false;
		}
		
		if (!ranking.contains("kills")) {
			p.sendMessage(config.getString("msg.error.ranking.empty"));
			return false;
		}
		
		Map<String, Object> configMap = ranking.getConfigurationSection("kills").getValues(true);
		
		for (Entry<String, Object> e : configMap.entrySet()) {
			UUID uuid = UUID.fromString(e.getKey());
			int kills = (int) e.getValue();
			rankingMap.put(uuid.toString(), kills);
		}
		
		int rank = 0;
		sender.sendMessage(config.getString("msg.ranking.title"));
		
		for (Entry<String, Integer> entry : MapSorting.sortedValues(rankingMap, Collections.reverseOrder())) {
			UUID uuid = UUID.fromString(entry.getKey());
			int kills = entry.getValue();
			rank++;
			if (rank > config.getInt("int.top.amount")) {
				break;
			}
			
			int level = kills/25;
			if (level > config.getInt("int.level.limit"))
				level = config.getInt("int.level.limit");
			
			String username = Bukkit.getOfflinePlayer(uuid).getName();
			sender.sendMessage(config.getString("msg.ranking.line").replace("%rank%", rank+"").replace("%username%", username).replace("%amount_kills%", kills+"").replace("%level%", level+""));
		}

		return false;
	}
}
