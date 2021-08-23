package ru.entryset.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import ru.entryset.tools.MenuFiles;

@SuppressWarnings("deprecation")
public class Item {

	private Player p;

	private ItemStack i;
	
	private ItemMeta meta;
	
	private String name;
	
	private List<String> lore;
	
	private List<ItemFlag> flags = new ArrayList<ItemFlag>();
	 
	private List<Enchantment> enchantments = new ArrayList<Enchantment>();
	
	private Boolean isunbreakablel;

	public Item(Player p, String value, FileConfiguration y) {
		this.p = p;
		this.i = new ItemStack(Material.valueOf(y
				.getString(value + ".Material"))
				, y.getInt(value + ".Amont")
				, (short)y.getInt(value + ".Data"));
		ItemMeta im = i.getItemMeta();
		load(value, y, im);
		i.setItemMeta(im);
	}

	public ItemStack getItem() {
		return this.i;
	}

	public ItemMeta getMeta() {
		return this.meta;
	}

	public String getDisplayName() {
		return this.name;
	}

	public List<String> getLore() {
		return this.lore;
	}

	public List<ItemFlag> getFlags() {
		return this.flags;
	}

	public List<Enchantment> getEnchantments() {
		return this.enchantments;
	}

	public boolean isUnbreakablel() {
		return this.isunbreakablel;
	}

	public Player getPlayer(){
		return this.p;
	}

	private void load(String value, FileConfiguration y, ItemMeta im) {
		this.meta = im;
		if(y.contains(value + ".Name")) {
			this.name = MenuFiles.PAPI(getPlayer(), ChatColor.translateAlternateColorCodes('&', y.getString(value + ".Name")));
			im.setDisplayName(getDisplayName());
		}
		if(y.contains(value + ".Lore")) {
			List<String> lore = new ArrayList<String>();
			for(String s : y.getStringList(value + ".Lore")) {
				lore.add(MenuFiles.PAPI(getPlayer(), ChatColor.translateAlternateColorCodes('&', s)));
			}
			this.lore = lore;
			im.setLore(lore);
		}
		if(y.contains(value + ".Flags")) {
			for(String s : y.getStringList(value + ".Flags")) {
				im.addItemFlags(ItemFlag.valueOf(s));
				this.flags.add(ItemFlag.valueOf(s));
			}
		}
		if(y.contains(value + ".Enchantments")) {
			for(String s : y.getStringList(value + ".Enchantments")) {
				List<String> id = new ArrayList<String>();
				for(String s2 : s.split(":")) {
					id.add(s2);
				}
				String a1 = id.get(1);
				int a = Integer.valueOf(a1);
				im.addEnchant(Enchantment.getByName(id.get(0)), a, true);
				this.enchantments.add(Enchantment.getByName(id.get(0)));
			}
		}
		if(y.contains(value + ".Unbreakable")) {
			if(Controller.getVersion() > 1.10) {
				im.setUnbreakable(y.getBoolean(value + ".Unbreakable"));
				this.isunbreakablel = y.getBoolean(value + ".Unbreakable");
			}
		}
	}
	
	public void setItemMeta(ItemMeta meta) {
		getItem().setItemMeta(meta);
		this.meta = meta;
	}
	
}
