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
		
		final File classementFile = main.getClassementFileOf(p.getWorld().getName());
		
		if (classementFile == null)
			return;
		
		final FileConfiguration classement = main.getClassementYamlOf(classementFile);

		if (classement.contains("kills." + uuidStr)) {
			classement.set("kills." + uuidStr, classement.getInt("kills." + uuidStr) + 1);
		} else {
			classement.set("kills." + uuidStr, 1);
		}
		
		try {
			classement.save(classementFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
