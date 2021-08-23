package ru.entryset.tools;

import org.bukkit.Bukkit;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.plugin.RegisteredServiceProvider;

import net.milkbowl.vault.economy.Economy;

public class VaultHook {
	
	
	public static boolean hasEnough(Player p, double amt) {
		
		Economy econ = null;
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		
		if (rsp != null) {
			econ = (Economy)rsp.getProvider();
		}
		
		if(econ != null) {
			
			if(econ.has((Player)p, amt)){
				return true;
			}	
			return false;
			
		}
		return false;
	}
	
	public static void takeMoney(Player p, double amt) {
		
		Economy econ = null;
		RegisteredServiceProvider<Economy> rsp = Bukkit.getServer().getServicesManager().getRegistration(Economy.class);
		
		if (rsp != null) {
			econ = (Economy)rsp.getProvider();
		}
		
		if (econ != null)
			econ.withdrawPlayer((OfflinePlayer)p, amt); 
		
	}
}
