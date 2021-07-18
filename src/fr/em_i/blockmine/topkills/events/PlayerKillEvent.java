package fr.em_i.blockmine.topkills.events;

import java.io.File;


import java.io.IOException;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

import fr.em_i.blockmine.topkills.Main;


public class PlayerKillEvent implements Listener {
	
	private Main main;
	
	public PlayerKillEvent(Main main) {
		this.main = main;
	}

	@EventHandler
	private void onMessage(PlayerDeathEvent event) {
		if (!(event.getEntity() instanceof Player)) {
			return;
		}
		
		final Player p = event.getEntity().getKiller();
		final String uuidStr = p.getUniqueId().toString();
		final String worldName = p.getWorld().getName();
		final File rankingFile = main.getRankingFileOf(worldName);

		if (rankingFile == null)
			return;
		
		final FileConfiguration ranking = main.getRankingYamlOf(rankingFile);
		final FileConfiguration config = main.getConfigOf(worldName);
		
		int killsAmount;
		if (ranking.contains("kills." + uuidStr))
			killsAmount = ranking.getInt("kills." + uuidStr) + 1;
		else
			killsAmount = 1;
		
		ranking.set("kills." + uuidStr, killsAmount);
		
		int level = killsAmount/config.getInt("int.level.ratio");
		int lastLevel = (killsAmount-1)/config.getInt("int.level.ratio");
		
		if (level < config.getInt("int.level.limit") && level-1 == lastLevel)
			p.sendMessage(config.getString("msg.rankup.message").replace("%level%", level+""));

		try {
			ranking.save(rankingFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
