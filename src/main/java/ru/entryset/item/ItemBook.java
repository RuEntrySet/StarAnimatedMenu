package ru.entryset.item;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BookMeta;
import org.bukkit.inventory.meta.BookMeta.Generation;
import ru.entryset.tools.MenuFiles;

public class ItemBook extends Item {

	private String auther;
	
	private List<String> pages = new ArrayList<String>();
	
	private Generation generation;
	
	public ItemBook(Player p, String value, FileConfiguration y) {
		super(p, value, y);
		
		BookMeta meta = (BookMeta) getMeta();
		load(value, y, meta);
		setItemMeta(meta);
	}
	 
	private void load(String value, FileConfiguration y, BookMeta meta) {
		if(y.contains(value + ".Author")) {
			this.auther = MenuFiles.PAPI(getPlayer(), ChatColor.translateAlternateColorCodes('&', y
					.getString(value + ".Author")));
			meta.setAuthor(getAuther());
		}
		if(y.contains(value + ".Pages")) {
			for(String s : y.getStringList(value + ".Pages")) {
				getPages().add(MenuFiles.PAPI(getPlayer(), ChatColor.translateAlternateColorCodes('&', s)));
			}
			meta.setPages(getPages());
		}
		if(y.contains(value + ".Generation")) {
			this.generation = Generation.valueOf
					(y.getString(value + ".Generation"));
			meta.setGeneration(getGeneration());
		}
		
	}
	
	public String getAuther() {
		return this.auther;
	}
	
	public List<String> getPages() {
		return this.pages;
	}
	
	public Generation getGeneration() {
		return this.generation;
	}
	

}
