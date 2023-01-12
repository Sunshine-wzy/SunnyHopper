package io.github.sunshinewzy.sunnyhopper.listeners;

import io.github.sunshinewzy.sunnyhopper.objects.Position;
import io.github.sunshinewzy.sunnyhopper.objects.SItem;
import io.github.sunshinewzy.sunnyhopper.hopper.SunnyHopperItem;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;

import static io.github.sunshinewzy.sunnyhopper.SunnyHopper.getSunnyHopperData;

public class BlockListener implements Listener {
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockPlace(BlockPlaceEvent event) {
		if(SItem.isItemSimilar(event.getItemInHand(), SunnyHopperItem.DESTRUCTION_HOPPER.getItem())) {
			getSunnyHopperData().setHopper(new Position(event.getBlockPlaced().getLocation()), SunnyHopperItem.DESTRUCTION_HOPPER);
		}
	}
	
	@EventHandler(priority = EventPriority.HIGHEST, ignoreCancelled = true)
	public void onBlockBreak(BlockBreakEvent event) {
		if(event.getBlock().getType() == Material.HOPPER) {
			Location location = event.getBlock().getLocation();
			World world = location.getWorld();
			if(world == null) return;

			Position position = new Position(location);
			if(getSunnyHopperData().hasHopper(position)) {
				SunnyHopperItem hopper = getSunnyHopperData().removeHopper(position);
				if(hopper != null) {
					world.dropItemNaturally(location, hopper.getItem().clone());
				}
			}
		}
	}
	
}
