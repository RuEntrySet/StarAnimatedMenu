package ru.entryset.item;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.SpawnEggMeta;

public class ItemEgg extends Item {
	
	private EntityType type;

	public ItemEgg(Player p, String value, FileConfiguration y) {
		super(p, value, y);
		
		SpawnEggMeta meta = (SpawnEggMeta) getMeta();
		load(value, y, meta);
		setItemMeta(meta);
	}
	 
	@SuppressWarnings("deprecation")
	private void load(String value, FileConfiguration y, SpawnEggMeta meta) {
		if(y.contains(value + ".Entity")) {
			this.type = EntityType.valueOf(y.getString(value + ".Entity"));
			meta.setSpawnedType(getType());
		}
	}
	
	public EntityType getType() {
		return this.type;
	}

}
