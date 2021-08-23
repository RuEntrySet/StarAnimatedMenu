package ru.entryset.placeholders;

import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

import org.bukkit.entity.Player;

import me.clip.placeholderapi.expansion.PlaceholderExpansion;
import ru.entryset.mysql.MySQLQuery;
import ru.entryset.tools.Utils;

public class PlaceholderAPI extends PlaceholderExpansion {
	
	public String onPlaceholderRequest(Player player, String params) {
		if(Utils.getQuerys().contains("Querys." + params)) {
			MySQLQuery q = new MySQLQuery(params);
			if(q.getConnection().isConnected()){
				return q.get();
			}
			return "";
		}
		return "";
	  }
	  
	  public String getIdentifier() {
	    return "mysql";
	  }
	  
	  public String getAuthor() {
	    return "_VELSEN_";
	  }
	  
	  public String getVersion() {
	    return "1.0";
	  }
	  
	  public <T, E> List<T> getKeysByValue(Map<T, E> map, E value) {
	    return (List<T>)map.entrySet()
	      .stream()
	      .filter(entry -> Objects.equals(entry.getValue(), value))
	      .map(Map.Entry::getKey)
	      .collect(Collectors.toList());
	  }

}
