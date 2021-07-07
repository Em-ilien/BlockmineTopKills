package fr.em_i.blockmine.topkills.events;

import org.bukkit.event.Listener;


import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.em_i.blockmine.topkills.Main;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class PlayerSendMessageEvent implements Listener {

	private Main main;
	
	public PlayerSendMessageEvent(Main main) {
		this.main = main;
	}

	@EventHandler (priority = EventPriority.HIGHEST)
	private void onMessage(AsyncPlayerChatEvent event) {
		final Player p = event.getPlayer();
		final String worldName = p.getWorld().getName();
		final String uuidStr = p.getUniqueId().toString();
		
		final FileConfiguration ranking = main.getRankingYamlOf(worldName);
		
		if (ranking == null)
			return;
		
		final FileConfiguration config = main.getConfigOf(worldName);
		
		int level = 0;
		if (ranking.contains("kills." + uuidStr)) 
			level = ranking.getInt("kills." + uuidStr);
		
		level = level/config.getInt("int.level.ratio");
		if (level >= config.getInt("int.level.limit"))
			level = config.getInt("int.level.limit");
		
		event.setFormat(config.getString("msg.tchat.format").replace("%level%", level+"").replace("%rest%", event.getFormat()));
	}
}