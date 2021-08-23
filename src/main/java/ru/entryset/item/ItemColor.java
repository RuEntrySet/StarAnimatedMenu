package ru.entryset.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.PotionMeta;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class ItemColor extends Item {
	
	private List<PotionEffect> effects = new ArrayList<PotionEffect>();
	
	private Color color;
	
	private Integer red;
	
	private Integer green;
	
	private Integer blue;
	
	 
	public ItemColor(Player p, String value, FileConfiguration y) {
		super(p, value, y);
		
		this.red = y.getInt(value + ".Color.Red");
		this.green = y.getInt(value + ".Color.Green");
		this.blue = y.getInt(value + ".Color.Blue");
		
		this.color = Color.fromRGB(getRed(), getGreen(), getBlue());
		
		PotionMeta meta = (PotionMeta) getMeta();
		load(value, y, meta);
		setItemMeta(meta);
	}
	
	private void load(String value, FileConfiguration y, PotionMeta meta) {
		if(y.contains(value + ".Color")) {
			meta.setColor(getColor());
		}
		if(y.contains(value + ".PotionEffects")) {
			for(String s : y.getStringList(value + ".PotionEffects")) {
				List<String> id = new ArrayList<String>();
				for(String s2 : s.split(":")) {
					id.add(s2);
				}
				String a1 = id.get(1);
				int a = Integer.valueOf(a1) - 1;
				String a3 = id.get(2);
				int a2 = Integer.valueOf(a3) * 20;
				meta.addCustomEffect(new PotionEffect(
						PotionEffectType.getByName(id.get(0)), a2, a), true);
				getEffects().add(new PotionEffect(
						PotionEffectType.getByName(id.get(0)), a2, a));
			}
		}
	}
	
	public List<PotionEffect> getEffects() {
		return this.effects;
	}
	
	public Color getColor() {
		return this.color;
	}
	
	public Integer getRed() {
		return this.red;
	}
	
	public Integer getGreen() {
		return this.green;
	}
	
	public Integer getBlue() {
		return this.blue;
	}
}
