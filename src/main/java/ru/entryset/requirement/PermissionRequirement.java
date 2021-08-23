package ru.entryset.requirement;

import org.bukkit.entity.Player;

import ru.entryset.tools.Utils;

public class PermissionRequirement {

	public static boolean check(Player p, String permission) {
		if(Utils.hasPermission(p, permission)) {
			return true;
		}
		return false;
	}
	
}
