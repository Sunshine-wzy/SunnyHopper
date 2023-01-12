package io.github.sunshinewzy.sunnyhopper.objects;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.util.NumberConversions;

import java.util.Objects;

public class Position {
	private final String world;
	private final int x;
	private final int y;
	private final int z;

	
	public Position(String world, int x, int y, int z) {
		this.world = world;
		this.x = x;
		this.y = y;
		this.z = z;
	}
	
	public Position(Location location) {
		this.world = Objects.requireNonNull(location.getWorld()).getName();
		this.x = location.getBlockX();
		this.y = location.getBlockY();
		this.z = location.getBlockZ();
	}

	
	public String getWorld() {
		return world;
	}

	public int getX() {
		return x;
	}

	public int getY() {
		return y;
	}

	public int getZ() {
		return z;
	}
	
	
	public Location toLocation() {
		return new Location(Bukkit.getWorld(world), x, y, z);
	}


	@Override
	public boolean equals(Object o) {
		if(this == o) return true;
		if(!(o instanceof Position)) return false;
		Position position = (Position) o;
		return x == position.x && y == position.y && z == position.z && world.equals(position.world);
	}

	@Override
	public int hashCode() {
		return Objects.hash(world, x, y, z);
	}

	@Override
	public String toString() {
		return world + ';' + x + ',' + y + ',' + z;
	}
	
	public static Position fromString(String source) {
		String[] worldAndPos = source.split(";");
		if(worldAndPos.length != 2) return null;

		String[] pos = worldAndPos[1].split(",");
		if(pos.length != 3) return null;

		return new Position(worldAndPos[0], NumberConversions.toInt(pos[0]), NumberConversions.toInt(pos[1]), NumberConversions.toInt(pos[2]));
	}
}
