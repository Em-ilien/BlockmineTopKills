package fr.em_i.blockmine.topkills.events;

import org.bukkit.event.Listener;

import org.bukkit.event.player.AsyncPlayerChatEvent;

import fr.em_i.blockmine.topkills.Main;

import java.io.File;

import org.bukkit.configuration.file.YamlConfiguration;
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
		
		final File classementFile = new File(main.getDataFolder(), "/classement.yml");
		final YamlConfiguration classement = YamlConfiguration.loadConfiguration(classementFile);

		int level = 0;
		if (classement.contains("Kills." + p.getUniqueId().toString())) 
			level = classement.getInt("Kills." + p.getUniqueId().toString());
		
		level = level/main.getConfig().getInt("int.level.ratio");
		if (level >= main.getConfig().getInt("int.level.limit"))
			level = main.getConfig().getInt("int.level.limit");
		
		event.setFormat(main.getConfig().getString("msg.tchat.format").replace("%level%", level+"").replace("%rest%", event.getFormat()));
	}
	
}