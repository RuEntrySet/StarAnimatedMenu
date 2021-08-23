package ru.entryset.item;

import org.bukkit.Color;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.LeatherArmorMeta;

public class ItemEquipment extends Item {

	private Color color;
	
	private Integer red;
	
	private Integer green;
	
	private Integer blue;
	
	
	public ItemEquipment(Player p, String value, FileConfiguration y) {
		super(p, value, y);
		
		this.red = y.getInt(value + ".Color.Red");
		this.green = y.getInt(value + ".Color.Green");
	 	this.blue = y.getInt(value + ".Color.Blue");
		 
		this.color = Color.fromRGB(getRed(), getGreen(), getBlue());
		 
		LeatherArmorMeta meta = (LeatherArmorMeta) getMeta();
		load(value, y, meta);
		setItemMeta(meta);
	}
	
	private void load(String value, FileConfiguration y, LeatherArmorMeta meta) {
		if(y.contains(value + ".Color")) {
			meta.setColor(getColor());
		}
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
