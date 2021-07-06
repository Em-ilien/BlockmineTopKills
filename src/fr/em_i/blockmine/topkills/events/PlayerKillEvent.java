package fr.em_i.blockmine.topkills.events;

import java.io.File;
import java.io.IOException;

import org.bukkit.configuration.file.YamlConfiguration;
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
		
		Player p = event.getEntity().getKiller();
		if (p.getWorld().getName() != "quake")
			return;
		
		final File classementFile = new File(main.getDataFolder(), "/classement.yml");
		final YamlConfiguration classement = YamlConfiguration.loadConfiguration(classementFile);

		if (classement.contains("Kills." + p.getUniqueId().toString())) {
			classement.set("Kills." + p.getUniqueId().toString(), classement.getInt("Kills." + p.getUniqueId().toString()) + 1);
		} else {
			classement.set("Kills." + p.getUniqueId().toString(), 1);
		}
		
		try {
			classement.save(classementFile);
		} catch (IOException e1) {
			e1.printStackTrace();
		}
	}
	
}
