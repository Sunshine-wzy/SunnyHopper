package io.github.sunshinewzy.sunnyhopper.hopper;

import io.github.sunshinewzy.sunnyhopper.SunnyHopper;
import io.github.sunshinewzy.sunnyhopper.objects.Position;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.block.Hopper;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

public class SunnyHopperData {
	
	private final HashMap<Position, SunnyHopperItem> positionToHopperMap = new HashMap<>();
	private final File configFile = new File(SunnyHopper.getPlugin().getDataFolder(), "data/hopper.yml");
	
	private boolean isUpdated = false;
	
	
	public SunnyHopperData() throws IOException {
		if(!configFile.getParentFile().exists()) {
			configFile.getParentFile().mkdirs();
		}
		if(!configFile.exists()) {
			configFile.createNewFile();
		}

		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		config.getKeys(false).forEach(key -> {
			Position position = Position.fromString(key);
			if(position != null) {
				String string = config.getString(key);
				if(string != null) {
					positionToHopperMap.put(position, SunnyHopperItem.valueOf(string));
				}
			}
		});

		Bukkit.getScheduler().runTaskTimer(SunnyHopper.getPlugin(), () -> {
			positionToHopperMap.forEach((pos, hopperItem) -> {
				if(hopperItem == SunnyHopperItem.DESTRUCTION_HOPPER) {
					Location location = pos.toLocation();
					World world = location.getWorld();
					if(world == null) return;
					
					BlockState state = location.getBlock().getState();
					if(state instanceof Hopper) {
						Hopper hopper = (Hopper) state;
						Inventory inventory = hopper.getInventory();
						
						if(inventory.firstEmpty() != -1) {
							Block block = location.add(0, 1, 0).getBlock();
							if(block.getType() != Material.AIR) {
								block.getDrops().stream()
										.flatMap(it -> inventory.addItem(it).entrySet().stream())
										.collect(ArrayList<ItemStack>::new, (list, entry) -> list.add(entry.getValue()), ArrayList::addAll)
										.forEach(it -> world.dropItemNaturally(location, it));
								block.setType(Material.AIR);
							}
						}
					}
				}
			});
		}, 20, 20);
	}
	
	public void saveData() {
		if(positionToHopperMap.isEmpty() || !isUpdated) return;
		
		YamlConfiguration config = new YamlConfiguration();
		positionToHopperMap.forEach((pos, hopper) -> {
			config.set(pos.toString(), hopper.name());
		});
		
		try {
			config.save(configFile);
		} catch (IOException ex) {
			ex.printStackTrace();
		}
	}
	
	public SunnyHopperItem getHopper(Position position) {
		return positionToHopperMap.get(position);
	}
	
	public boolean hasHopper(Position position) {
		return positionToHopperMap.containsKey(position);
	}
	
	public SunnyHopperItem setHopper(Position position, SunnyHopperItem hopper) {
		if(!isUpdated) isUpdated = true;
		return positionToHopperMap.put(position, hopper);
	}
	
	public SunnyHopperItem removeHopper(Position position) {
		if(!isUpdated) isUpdated = true;
		return positionToHopperMap.remove(position);
	}
	
}
