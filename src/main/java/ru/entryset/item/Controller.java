package ru.entryset.item;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import ru.entryset.tools.Logger;
import ru.entryset.tools.Utils;

import java.util.Objects;

public class Controller {

	private ItemStack item;

	public Controller(Player p, String item) {
		if(item.equalsIgnoreCase("AIR")) {
			this.item = new ItemStack(Material.AIR);
			return;
		}
		if(getItem(p, "items." + item, Utils.getItemConfig()) == null){
			this.item = null;
			return;
		}
		this.item = getItem(p, "items." + item, Utils.getItemConfig());
	}

	public ItemStack getItemFix() {
		return this.item;
	}

	public void setIngredient(Inventory i, int slot) {
		if(getItemFix() == null) {
			Logger.error("NULL");
			return;
		}
		i.setItem(slot, getItemFix());
	}


	public static ItemStack getItem(Player p, String value, FileConfiguration y) {

		ItemStack item = null;

		String s = y.getString(value + ".Type");

		switch (Objects.requireNonNull(s)){
			case ("Leather_Equipment"):
				ItemEquipment i1 = new ItemEquipment(p, value, y);
				item = i1.getItem();
				break;
			case ("Color"):
				if(getVersion() > 10){
					ItemColor i2 = new ItemColor(p, value, y);
					item = i2.getItem();
				}
				break;
			case ("Banner"):
				ItemBanner i3 = new ItemBanner(p, value, y);
				item = i3.getItem();
				break;
			case ("Enchanted_Book"):
				ItemEnchantedBook i4 = new ItemEnchantedBook(p, value, y);
				item = i4.getItem();
				break;
			case ("Book"):
				if(getVersion() > 9){
					ItemBook i5 = new ItemBook(p, value, y);
					item = i5.getItem();
				}
				break;
			case ("Command"):
				ItemCommand i6 = new ItemCommand(p, value, y);
				item = i6.getItem();
				break;
			case ("Spawn_Egg"):
				if(getVersion() > 10){
					ItemEgg i5 = new ItemEgg(p, value, y);
					item = i5.getItem();
				}
				break;
			case ("Skull"):
				ItemSkull i8 = new ItemSkull(p, value, y);
				item = i8.getItem();
				break;
			case ("Firework"):
				ItemFirework i9 = new ItemFirework(p, value, y);
				item = i9.getItem();
				break;
			default:
				Item i = new Item(p, value, y);
				item = i.getItem();
				break;
		}
		return item;
	}
	
	public static Integer getVersion() {

		int result = 7;

		for(int inner = 8; inner <= 20; inner++){
			if(Bukkit.getVersion().contains("1." + String.valueOf(inner))){
				result = inner;
				break;
			}
		}

		return result;
	}

}
