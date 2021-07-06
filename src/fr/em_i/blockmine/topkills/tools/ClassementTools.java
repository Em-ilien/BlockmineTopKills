package fr.em_i.blockmine.topkills.tools;

import java.io.File;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.configuration.file.YamlConfiguration;

import fr.em_i.blockmine.topkills.Main;

public class ClassementTools {

	public static int pseudo(Main main, int place) {
		final File file = new File(main.getDataFolder(), "/classement.yml");
		final YamlConfiguration configuration = YamlConfiguration.loadConfiguration(file);
		OfflinePlayer[] list = Bukkit.getOfflinePlayers();

		int[] montants = new int[list.length];

		for (int i = 0; i < list.length; i++) {
			if (list[i].getName() != "null" && configuration.contains("Kills." + list[i].getName())) {
				montants[i] = configuration.getInt("Kills." + list[i].getName());
			} else {
				montants[i] = 0;
			}
		}

		for (int i = 0; i < list.length; i++) {
			if (i + 1 != montants.length && montants[i] < montants[i + 1]) {
				int save = montants[i];
				montants[i] = montants[i + 1];
				montants[i + 1] = save;
			}
			if (i != 0 && montants[i] > montants[i - 1]) {
				i = 0;
			}
		}
		return montants[place];
	}
}