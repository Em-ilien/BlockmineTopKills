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
		
		final File rankingFile = main.getRankingFileOf(p.getWorld().getName());
		
		if (rankingFile == null)
			return;
		
		final FileConfiguration ranking = main.getRankingYamlOf(rankingFile);

		if (ranking.contains("kills." + uuidStr)) {
			ranking.set("kills." + uuidStr, ranking.getInt("kills." + uuidStr) + 1);
		} else {
			ranking.set("kills." + uuidStr, 1);
		}
		
		try {
			ranking.save(rankingFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
