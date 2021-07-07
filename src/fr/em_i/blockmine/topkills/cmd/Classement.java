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

public class Classement implements CommandExecutor {

	private HashMap<String, Integer> classementMap = new HashMap<>();
	private Main main;

	public Classement(Main main) {
		this.main = main;
	}

	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!(sender instanceof Player)) {
			sender.sendMessage("Erreur : console");
			return false;
		}
		
		final Player p = (Player) sender;
		final String worldName = p.getWorld().getName();
		classementMap.clear();
		
		FileConfiguration classement = main.getClassementYamlOf(worldName);
		
		if (classement == null) {
			p.sendMessage("§cErreur : aucun classement n'a été configuré sur ce monde.");
			return false;
		}
		
		Map<String, Object> configMap = classement.getConfigurationSection("kills").getValues(true);
		
		for (Entry<String, Object> e : configMap.entrySet()) {
			UUID uuid = UUID.fromString(e.getKey());
			int kills = (int) e.getValue();
			classementMap.put(uuid.toString(), kills);
		}
		
		int rank = 0;
		final FileConfiguration config = main.getConfigOf(worldName);
		sender.sendMessage(config.getString("msg.classement.title"));
		
		System.out.println(config.getString("msg.classement.title"));
		
		for (Entry<String, Integer> entry : MapSorting.sortedValues(classementMap, Collections.reverseOrder())) {
			UUID uuid = UUID.fromString(entry.getKey());
			int kills = entry.getValue();
			rank++;
			if (rank > 10) {
				break;
			}
			
			int level = kills/25;
			String username = Bukkit.getOfflinePlayer(uuid).getName();
			System.out.println(config.getString("msg.classement.line"));
			sender.sendMessage(config.getString("msg.classement.line").replace("%rank%", rank+"").replace("%username%", username).replace("%amount_kills%", kills+"").replace("%level%", level+""));
		}

		return false;
	}
}
