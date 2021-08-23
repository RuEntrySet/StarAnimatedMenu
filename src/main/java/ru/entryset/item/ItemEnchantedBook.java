package ru.entryset.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.EnchantmentStorageMeta;

public class ItemEnchantedBook extends Item {

	private List<Enchantment> storedenchants = new ArrayList<Enchantment>();
	
	public ItemEnchantedBook(Player p, String value, FileConfiguration y) {
		super(p, value, y);
		 
		EnchantmentStorageMeta meta = (EnchantmentStorageMeta) getMeta();
		load(value, y, meta);
		setItemMeta(meta);
	}
	
	@SuppressWarnings("deprecation")
	private void load(String value, FileConfiguration y, EnchantmentStorageMeta meta) {
		if(y.contains(value + ".Stored")) {
			for(String s : y.getStringList(value + ".Stored")) {
	 			List<String> id = new ArrayList<String>();
				for(String s2 : s.split(":")) {
					id.add(s2);
				}
				String a1 = id.get(1);
				int a = Integer.valueOf(a1);
				meta.addStoredEnchant(Enchantment.getByName(id.get(0))
						, a, true);
				getStoredEnchants().add(Enchantment.getByName(id.get(0)));
			}
		}
	}
	
	public List<Enchantment> getStoredEnchants() {
		return this.storedenchants;
	}
	
}
