package ru.entryset.requirement;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class CalculationsRequirement {
	
	public static boolean string(Player p, String placeholder, String input){
		
		String Placeholder = PlaceholderAPI.setPlaceholders((Player)p, placeholder);
		if(Placeholder.equalsIgnoreCase(input)) {
			return true;
		}
		
		return false;
	}
	
	public static boolean eqlse(Player p, String placeholder, Integer input){
		
		double Placeholder = Double.parseDouble((PlaceholderAPI.setPlaceholders((Player)p, placeholder)));
		if(Placeholder == input) {
			return true;
		}
		
		return false;
	}
	
	public static boolean moreeqlse(Player p, String placeholder, Integer input){
		
		double Placeholder = Double.parseDouble((PlaceholderAPI.setPlaceholders((Player)p, placeholder)));
		if(Placeholder >= input) {
			return true;
		}
		
		return false;
	}
	
	public static boolean unmoreeqlse(Player p, String placeholder, Integer input){
		
		double Placeholder = Double.parseDouble((PlaceholderAPI.setPlaceholders((Player)p, placeholder)));
		if(Placeholder <= input) {
			return true;
		}
		
		return false;
	}
	
	public static boolean unmore(Player p, String placeholder, Integer input){
		
		double Placeholder = Double.parseDouble((PlaceholderAPI.setPlaceholders((Player)p, placeholder)));
		if(Placeholder < input) {
			return true;
		}
		
		return false;
	}
	public static boolean more(Player p, String placeholder, Integer input){
		
		double Placeholder = Double.parseDouble((PlaceholderAPI.setPlaceholders((Player)p, placeholder)));
		if(Placeholder > input) {
			return true;
		}
		
		return false;
	}
	
}
