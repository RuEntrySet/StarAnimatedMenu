package ru.entryset.item;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.FireworkMeta;

public class ItemFirework extends Item {
	
	private Integer power;
	
	public ItemFirework(Player p, String value, FileConfiguration y) {
		super(p, value, y);
		FireworkMeta meta = (FireworkMeta) getMeta();
		load(value, y, meta);
		setItemMeta(meta);
	}
	
	private void load(String value, FileConfiguration y, FireworkMeta meta) {
		if(y.contains(value + ".Power")) {
			this.power = y.getInt(value + ".Power");
			meta.setPower(getPower());
		}
	}
	
	public Integer getPower() {
		return this.power;
	}

}
