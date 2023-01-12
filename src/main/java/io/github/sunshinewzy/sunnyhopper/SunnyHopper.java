package io.github.sunshinewzy.sunnyhopper;

import io.github.sunshinewzy.sunnyhopper.listeners.BlockListener;
import io.github.sunshinewzy.sunnyhopper.hopper.SunnyHopperData;
import io.github.sunshinewzy.sunnyhopper.hopper.SunnyHopperItem;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ShapedRecipe;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.IOException;

public class SunnyHopper extends JavaPlugin {

	private final PluginManager pluginManager = Bukkit.getPluginManager();
	
	private static SunnyHopper plugin;
	private static SunnyHopperData sunnyHopperData;
	

	@Override
	public void onEnable() {
		plugin = this;
		
		pluginManager.registerEvents(new BlockListener(), this);
		
		Bukkit.addRecipe(
				new ShapedRecipe(SunnyHopperItem.DESTRUCTION_HOPPER.getKey(), SunnyHopperItem.DESTRUCTION_HOPPER.getItem())
						.shape("xpx", "xhx", " x ")
						.setIngredient('x', Material.GOLD_INGOT)
						.setIngredient('h', Material.HOPPER)
						.setIngredient('p', Material.IRON_PICKAXE)
		);

		try {
			sunnyHopperData = new SunnyHopperData();
		} catch (IOException ex) {
			throw new RuntimeException(ex);
		}
	}

	@Override
	public void onDisable() {
		sunnyHopperData.saveData();
	}
	
	
	public static SunnyHopper getPlugin() {
		return plugin;
	}

	public static SunnyHopperData getSunnyHopperData() {
		return sunnyHopperData;
	}
}
