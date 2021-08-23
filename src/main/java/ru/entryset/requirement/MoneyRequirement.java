package ru.entryset.requirement;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import ru.entryset.tools.VaultHook;

public class MoneyRequirement {

	public static boolean check(Player p, Integer money) {
		if(Bukkit.getPluginManager().getPlugin("Vault") == null) {
			return true;
		}
		if(VaultHook.hasEnough(p, money)) {
			return true;
		}
		return false;
	}
	
}
