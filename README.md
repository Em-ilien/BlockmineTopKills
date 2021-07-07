# BlockmineTopKills
A spigot plugin to print the top 10 players killers.

CE README N'EST PAS DISPONIBLE EN FRANÇAIS.
---

##Requirements
* Java
* Spigot 1.9.4 (but you can test an another version)

##Features
A ranking system for best killers players.

##Configuration
Please follow these following instructions.

The configuration system is by world. This means that for each world you want to count the kills, you need to add a single particular configuration.
To config a world, you need to go to in the directory "Worlds", then choose the world of your choice and then open the config.yml file.
The name of the folder corresponds to the world name.
So, if your world is entitled "world", do not rename the default folder name. But, if your world's name is "quake", please rename the folder to "quake".
To add a world kills counter, you can duplicate a world directory contained in "Worlds" and then configure it with config.yml subfile.

##Frequent mistakes
###§eLe classement est vide.
Translation: The ranking is empty.

This means that nobody has yet killed another player. You must be wait that a killing comes to print a ranking.

###§cErreur : aucun classement n'a été configuré sur ce monde.
Translation: No ranking was configured on this world.

This menas that you have not configured your world in the configuration system. Or maybe it's on purpose. But if not: please refer to Configuration section in this README. You have to rename the folder Worlds/world in Worlds/{YOUR_WORLD_NAME}.

If you have many worlds into you want count the kills, you have to add a new sub-folder into the folder Worlds/ by duplicated the single one, for exemple. Then, rename your sub-folder in the world name.

##Support
Please email me at emilien@emixocle.fr with details.
I can add features if you need.
