package ru.entryset.requirement;

import net.pretronic.dkcoins.api.DKCoins;
import net.pretronic.dkcoins.api.account.AccountCredit;
import net.pretronic.dkcoins.api.user.DKCoinsUser;
import net.pretronic.dkcoins.api.user.DKCoinsUserManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

public class DKCoinsRequirement {

	public static boolean check(Player p, Integer amont) {
		if(Bukkit.getPluginManager().getPlugin("DKCoins") == null) {
			return true;
		}
		DKCoinsUserManager manager = DKCoins.getInstance().getUserManager();
		DKCoinsUser user = manager.getUser(p.getName());
		AccountCredit coins = user.getDefaultAccount().getCredit(user.getDefaultAccount().getId());
		if(coins.getAmount() >= amont) {
			return true;
		}
		return false;
	}
	
}
