package io.github.sunshinewzy.sunnyhopper.objects;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class SItem extends ItemStack {
	
	public SItem(Material type, String name, List<String> lore) {
		super(type);
		setNameAndLore(this, name, lore);
	}
	
	public SItem(Material type, String name) {
		super(type);
		setName(this, name);
	}
	
	
	public static ItemStack setNameAndLore(ItemStack item, String name, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		if(meta == null) meta = Bukkit.getItemFactory().getItemMeta(item.getType());
		
		if(meta != null) {
			meta.setDisplayName(name);
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return item;
	}
	
	public static ItemStack setName(ItemStack item, String name) {
		ItemMeta meta = item.getItemMeta();
		if(meta == null) meta = Bukkit.getItemFactory().getItemMeta(item.getType());

		if(meta != null) {
			meta.setDisplayName(name);
			item.setItemMeta(meta);
		}
		return item;
	}
	
	public static ItemStack setLore(ItemStack item, List<String> lore) {
		ItemMeta meta = item.getItemMeta();
		if(meta == null) meta = Bukkit.getItemFactory().getItemMeta(item.getType());

		if(meta != null) {
			meta.setLore(lore);
			item.setItemMeta(meta);
		}
		return item;
	}

	public static boolean isItemSimilar(ItemStack item, ItemStack theItem, boolean lore) {
		if (item == null)
			return theItem == null;
		if (theItem == null) {
			return false;
		}
		if ((item.getType() == theItem.getType()) && (item.getAmount() >= theItem.getAmount())) {
			if ((item.hasItemMeta()) && (theItem.hasItemMeta())) {
				if ((item.getItemMeta().hasDisplayName()) && (theItem.getItemMeta().hasDisplayName())) {
					if (item.getItemMeta().getDisplayName().equals(theItem.getItemMeta().getDisplayName())) {
						if (lore) {
							if ((item.getItemMeta().hasLore()) && (theItem.getItemMeta().hasLore())) {
								return item.getItemMeta().getLore().toString()
										.equals(theItem.getItemMeta().getLore().toString());
							}
							return (!item.getItemMeta().hasLore()) && (!theItem.getItemMeta().hasLore());
						}
						return true;
					}
					return false;
				}
				if ((!item.getItemMeta().hasDisplayName()) && (!theItem.getItemMeta().hasDisplayName())) {
					if (lore) {
						if ((item.getItemMeta().hasLore()) && (theItem.getItemMeta().hasLore())) {
							return item.getItemMeta().getLore().toString()
									.equals(theItem.getItemMeta().getLore().toString());
						}
						return (!item.getItemMeta().hasLore()) && (!theItem.getItemMeta().hasLore());
					}
					return true;
				}
				return false;
			}
			return (!item.hasItemMeta()) && (!theItem.hasItemMeta());
		}
		return false;
	}

	public static boolean isItemSimilar(ItemStack item, ItemStack theItem) {
		return isItemSimilar(item, theItem, true);
	}
	
}
