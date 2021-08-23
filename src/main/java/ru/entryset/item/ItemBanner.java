package ru.entryset.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.DyeColor;
import org.bukkit.block.banner.Pattern;
import org.bukkit.block.banner.PatternType;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BannerMeta;

public class ItemBanner extends Item {

	private List<Pattern> patterns = new ArrayList<Pattern>();
	
	public ItemBanner(Player p, String value, FileConfiguration y) {
		super(p, value, y);
		
		BannerMeta meta = (BannerMeta) getMeta();
		load(value, y, meta);
		setItemMeta(meta);
	}
	 
	private void load(String value, FileConfiguration y, BannerMeta meta) {
		if(y.contains(value + ".Pattern")) {
			for(String s : y.getStringList(value + ".Pattern")) {
				List<String> id = new ArrayList<String>();
				for(String s2 : s.split(":")) {
					id.add(s2);
				}
				meta.addPattern(new Pattern(DyeColor.valueOf(id.get(0)), PatternType.valueOf(id.get(1))));
				getPattetns().add(new Pattern(DyeColor.valueOf(id.get(0)), PatternType.valueOf(id.get(1))));
			}
		}
	}
	
	public List<Pattern> getPattetns() {
		return this.patterns;
	}
}
