package io.github.sunshinewzy.sunnyhopper.hopper;

import io.github.sunshinewzy.sunnyhopper.objects.SItem;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.inventory.ItemStack;

import static io.github.sunshinewzy.sunnyhopper.SunnyHopper.getPlugin;

public enum SunnyHopperItem {
	DESTRUCTION_HOPPER(new NamespacedKey(getPlugin(), "destruction_hopper"), new SItem(Material.HOPPER, "§c破坏漏斗")),
	
	;
	
	private final NamespacedKey key;
	private final ItemStack item;
	
	SunnyHopperItem(NamespacedKey key, ItemStack item) {
		this.key = key;
		this.item = item;
	}

	public NamespacedKey getKey() {
		return key;
	}

	public ItemStack getItem() {
		return item;
	}
}
