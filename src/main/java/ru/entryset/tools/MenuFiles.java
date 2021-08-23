package ru.entryset.tools;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Set;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;

import me.clip.placeholderapi.PlaceholderAPI;

public class MenuFiles {
	
	public static HashMap<String, YamlConfiguration> menus = new HashMap<String, YamlConfiguration>();
	
	public static HashMap<String, YamlConfiguration> menusbycommand = new HashMap<String, YamlConfiguration>();
	
	
	//for execute command /sam list
	public static ArrayList<String> menuslist = new ArrayList<String>();
	
	//loadfiles
	public static int loadGUIMenus() {
		FileConfiguration c = Utils.getConfig();
		if (!c.contains("menus"))
			return 0;
		if (!c.isConfigurationSection("menus"))
			return 0; 
		Set<String> keys = c.getConfigurationSection("menus").getKeys(false);
		if (keys == null || keys.isEmpty())
			return 0; 
		for (String key : keys) {
			if (c.contains("menus." + key + ".file")) {
				String fileName = c.getString("menus." + key + ".file");
				if (!fileName.endsWith(".yml")) {
					Logger.warn("Filename specified for GUI menu: " + key + " is not a .yml file!");
					Logger.info("Make sure that the file name to load this GUI menu from is specified as a .yml file!");
					Logger.info("Skipping loading of GUI menu: " + key);
					continue;
				} 
				File dir = new File(Utils.getInstance().getDataFolder() + File.separator + "menus");
				try {
					if (!dir.exists()) {
						dir.mkdirs();
						Logger.warn("Directory did not exist for loading gui menus from individual files!");
						Logger.info("Creating directory: plugins" + File.separator + "StarAnimatedMenu" + File.separator + "menus");
					} 
				} catch (SecurityException e) {
					Logger.error("Could not create directory: plugins" + File.separator + "StarAnimatedMenu" + File.separator + "menus");
					continue;
				} 
				File f = new File(Utils.getInstance().getDataFolder() + File.separator + "menus", fileName);
				if (!f.exists()) {
					Logger.warn(String.valueOf(f.getName()) + " does not exist!");
					try {
						f.createNewFile();
						Logger.info(String.valueOf(f.getName()) + " created! Add your GUI entries to this file and use /sam reload to load it!");
					} catch (IOException e) {
						Logger.error("Could not create gui menu file: plugins" + File.separator + "StarAnimatedMenu" + File.separator + "menus" + File.separator + fileName);
					} 
				} 
				if (!checkConfig(f)) {
					Logger.warn("Menu: " + key + " in file: " + fileName + " not loaded.");
					continue;
				}
				YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
				if (yamlConfiguration.getKeys(false) == null || yamlConfiguration.getKeys(false).isEmpty()) {
					Logger.info("GUI config file: " + f.getName() + " is empty! Creating default GUI config example...");
					createDefaultGUIMenu(f, (FileConfiguration)yamlConfiguration, key);
				}
				continue;
			}
		} 
		return 0;
	}

	//load HashMap MenuFiles
	public static int loadGUIMenusMap() {
		FileConfiguration c = Utils.getConfig();
		if (!c.contains("menus"))
			return 0; 
		if (!c.isConfigurationSection("menus"))
			return 0; 
		Set<String> keys = c.getConfigurationSection("menus").getKeys(false);
		if (keys == null || keys.isEmpty())
			return 0; 
		for (String key : keys) {
			if (c.contains("menus." + key + ".file")) {
				String fileName = c.getString("menus." + key + ".file");
				if (!fileName.endsWith(".yml")) {
					Logger.warn("Filename specified for GUI menu: " + key + " is not a .yml file!");
					Logger.info("Make sure that the file name to load this GUI menu from is specified as a .yml file!");
					Logger.info("Skipping loading of GUI menu: " + key);
					continue;
				} 
				File f = new File(Utils.getInstance().getDataFolder() + File.separator + "menus", fileName);
				YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(f);
				MenuFiles.menus.put(key, yamlConfiguration);
				MenuFiles.menuslist.add(key);
				if (yamlConfiguration.contains("settings.menu_commands")) {
					List<String> cmd = yamlConfiguration.getStringList("settings.menu_commands");
					for(String cm : cmd) {
						if(cm != null) {
							if(!MenuFiles.menusbycommand.containsKey(cm)) {
								MenuFiles.menusbycommand.put(cm, yamlConfiguration);
							}
						}
					}
				}
			}
		} 
		return 0;
	}
	

	public static boolean checkConfig(File f) {
		YamlConfiguration yamlConfiguration = new YamlConfiguration();
		try {
			yamlConfiguration.load(f);
			return true;
		} catch (IOException e) {
			return false;
		} catch (InvalidConfigurationException e) {
			Logger.warn("Detected invalid configuration in file: " + f.getName());
			e.printStackTrace();
			return false;
		} 
	}
	
	private static void createDefaultGUIMenu(File f, FileConfiguration c, String name) {
		c.options().header("-----------------------------------------------------------------------------------#" + 
			"\n                                                                                   #" + 
	        "\n                              StarAnimatedMenu                                     #" + 
	        "\n  Plugin developer: _VELSEN_                                                       #" + 
	        "\n  Technical support: https://discord.gg/UrbdeuMHjW                                 #" + 
	        "\n  Tested versions: Spigot (1.8-1.16.5)                                             #" +
	        "\n                                                                                   #" + 
	        "\n-----------------------------------------------------------------------------------#" + 
	        "\nDownload menu examples: https://drive.google.com/file/d/1EnICZeJm2EHjVea2C7Rl1_xIT5Xe0EvG/view?usp=sharing");
		c.set("settings.menu_type", "CHEST");
		c.set("settings.slot_size", Integer.valueOf(54));
		c.set("settings.menu_commands", Arrays.asList(new String[] { String.valueOf(name) + "gui" }));
		c.set("settings.menu_title", "&d" + name + " &aGUI menu");
		c.set("settings.open_requirements.permission.type", "has permission");
		c.set("settings.open_requirements.permission.permission", "menu.open." + name);
		c.set("settings.open_requirements.permission.deny_commands", 
				Arrays.asList(new String[] { "[message] &cYou do not have permission to use!"
						, "[console] mute %player% 10s" 
						, "[player] heal" }));
		c.set("settings.open_requirements.anythinghere.type", "string equals ignorecase");
		c.set("settings.open_requirements.anythinghere.input", "%player_world%");
		c.set("settings.open_requirements.anythinghere.output", "world");
		c.set("settings.fill.enable", false);
		c.set("settings.fill.type", "FULL");
		c.set("settings.fill.animation.type", "run");
		c.set("settings.fill.animation.run_items", Arrays.asList(new String[] { "Helmet"
				, "Book"}));
		c.set("settings.fill.animation.update_time", Integer.valueOf(20));
		c.set("settings.fill.animation.start_time", Integer.valueOf(0));
		c.set("settings.cancel_click_in_inventory_player", true);
		c.set("settings.click_on_air_slot", false);
		c.set("content.1.view_requirements.permission.type", "has permission");
		c.set("content.1.view_requirements.permission.permission", "menu.test.1");
		c.set("content.1.left_click_requirements.Money.type", "has money");
		c.set("content.1.left_click_requirements.Money.amont", Double.valueOf(800.0));
		c.set("content.1.left_click_commands",Arrays.asList(new String[] { "[sound] BLOCK_ANVIL_FALL"
				, "[player] home"}));
		c.set("content.1.right_click_commands",Arrays.asList(new String[] { "[player] home"}));
		c.set("content.1.slots", Arrays.asList(new Integer[] {2,4,6}));
		
		c.set("content.1.animation.type", "run");
		c.set("content.1.animation.run_items", Arrays.asList(new String[] { "Item"
				, "Banner"}));
		c.set("content.1.animation.update_time", Integer.valueOf(20));
		c.set("content.1.animation.start_time", Integer.valueOf(0));

	    try {
	    	c.save(f);
	    	Logger.info("Default GUI menu configuration created for file: " + f.getName() + ". Modify this file and load this GUI menu with /sam reload");
	    } catch (IOException e) {
	    	Logger.error("Could not save default configuration to file: " + f.getName());
	    } 
	  }
	
	public static String PAPI(Player p, String s) {
		String sd = Utils.format(s);
		if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
			sd = PlaceholderAPI.setPlaceholders((Player)p, s);
		}
		return sd;
	}
	
}
