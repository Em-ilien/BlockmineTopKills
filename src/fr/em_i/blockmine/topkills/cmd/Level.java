package fr.em_i.blockmine.topkills.cmd;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;

import fr.em_i.blockmine.topkills.Main;

public class Level implements CommandExecutor {

	private Main main;

	public Level(Main main) {
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
		final String uuidStr = p.getUniqueId().toString();
		
		final FileConfiguration ranking = main.getRankingYamlOf(worldName);
		final FileConfiguration config = main.getConfigOf(worldName);
		
		if (ranking == null) {
			p.sendMessage(main.getConfig().getString("msg.error.world.noconfigured"));
			return false;
		}
		
		int level = 0;
		if (ranking.contains("kills." + uuidStr)) 
			level = ranking.getInt("kills." + uuidStr);
		
		level = level/config.getInt("int.level.ratio");
		
		if (level >= config.getInt("int.level.limit"))
			level = config.getInt("int.level.limit");
		
		p.sendMessage(config.getString("msg.print.level").replace("%level%", level+""));

		return false;
	}

}
