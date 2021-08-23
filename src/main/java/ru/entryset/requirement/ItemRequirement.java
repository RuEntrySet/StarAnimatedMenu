package ru.entryset.requirement;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import ru.entryset.item.Controller;

public class ItemRequirement {
	
	public static boolean check(String key, Player p, Integer amont) {
		Controller id = new Controller(p, key);
		ItemStack i = id.getItemFix();
		if(p.getInventory().containsAtLeast(i, i.getAmount()*amont)) {
			return true;
		}
		return false;
	}

}
