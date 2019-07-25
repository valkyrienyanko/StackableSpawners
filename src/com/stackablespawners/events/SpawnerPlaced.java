package com.stackablespawners.events;

import org.bukkit.GameMode;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.CreatureSpawner;
import org.bukkit.configuration.Configuration;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockPlaceEvent;
import com.stackablespawners.StackableSpawners;

public class SpawnerPlaced implements Listener {
	@EventHandler
	private void blockPlaced(BlockPlaceEvent e) {
		boolean debug = true;
		if (e.getPlayer().getGameMode() != GameMode.SURVIVAL && debug != true) return;
		if (e.getBlock().getType() != Material.MOB_SPAWNER) return;
		
		Configuration config = StackableSpawners.spawnerConfig.getConfig();
		
		Block b = e.getBlock();
		CreatureSpawner type = (CreatureSpawner) b.getState();
		EntityType mobType = type.getSpawnedType();
		Location loc = b.getLocation();
		
		ConfigurationSection sectionSpawners = config.getConfigurationSection("spawners");
		
		int slot = 1;
		for (String element : sectionSpawners.getKeys(false)) {
			if (element != null) {
				slot++;
			}
		}
		
		config.set("spawners." + slot + ".loc.x", loc.getBlockX());
		config.set("spawners." + slot + ".loc.y", loc.getBlockY());
		config.set("spawners." + slot + ".loc.z", loc.getBlockZ());
		config.set("spawners." + slot + ".type", mobType.name());
		config.set("spawners." + slot + ".level", 1);
		StackableSpawners.spawnerConfig.saveConfig();
	}
}
