package ru.entryset.requirement;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;
import ru.entryset.tools.Logger;

public class JavaScriptRequirement {

	public static boolean check(Player p, String expression) {
		
		ScriptEngine engine = null;
	    if (engine == null) {
	        engine = (new ScriptEngineManager()).getEngineByName("javascript");
	        engine.put("BukkitServer", Bukkit.getServer());
	    } 
		
		String exp = PlaceholderAPI.setPlaceholders((Player)p, expression);
		try {
			engine.put("BukkitPlayer", p);
			Object result = engine.eval(exp);
			if (!(result instanceof Boolean)) {
				Logger.error("Requirement javascript <" + expression + "> is invalid and does not return a boolean!");
				return false;
			}
			return ((Boolean)result).booleanValue();
		} catch (ScriptException|NullPointerException e) {
			Logger.error("Error in requirement javascript syntax - " + expression);
			return false;
		}
	}
	
}
