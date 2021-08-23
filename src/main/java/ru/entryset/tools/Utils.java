package ru.entryset.tools;

import java.io.ByteArrayOutputStream;
import java.io.DataOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import me.clip.placeholderapi.PlaceholderAPI;
import ru.entryset.main.Main;
import ru.entryset.menu.Menu;

public class Utils {
	
	private static JavaPlugin instance;

	public static JavaPlugin getInstance() {
		return instance;
	}
	
	public static void setInstance(JavaPlugin instance) {
		Utils.instance = instance;
	}
	
	
	//Files utils start
	private static YamlConfiguration config;
	
	private static YamlConfiguration items;
	
	private static YamlConfiguration querys;

	public static YamlConfiguration getConfig() {
		
		if(config != null) {
			return Utils.getFile("config.yml");
		}
		return config = Utils.getFile("config.yml");
		
	}

	public static YamlConfiguration getItemConfig() {
		
		if(items != null) {
			return Utils.getFile("items.yml");
		}
		return items = Utils.getFile("items.yml");
		
	}
	
	public static YamlConfiguration getQuerys() {
		
		if(querys != null) {
			return Utils.getFile("querys.yml");
		}
		return querys = Utils.getFile("querys.yml");
		
	}
	
	public static void reloadConfig() {
		
		config = Utils.getFile("config.yml");
		items = Utils.getFile("items.yml");
		querys = Utils.getFile("querys.yml");
		
	}
	
	public static YamlConfiguration getMenu(String menu) {
		return Utils.getFile("menus" + File.separator + menu + ".yml");
	}

//	public static void reloadMenu(YamlConfiguration y, String menu) {
//		y = Utils.getFile(menu + ".yml");
//	}
	
	public static YamlConfiguration getFile(String fileName) {
		
		File file = new File(Utils.getInstance().getDataFolder(), fileName);

		if (!file.exists()) {
			Utils.getInstance().saveResource(fileName, false);
		}
		return YamlConfiguration.loadConfiguration(file);
		
	}
	
	public static String getMessage(String path) {
		return Utils.format(getConfig().getString("messages." + path));
	}
	
	public static String getString(String path) {
		return Utils.format(getConfig().getString(path));
	}
	
	public static Integer getInt(String path) {
		return getConfig().getInt(path);
	}
	
	public static Double getDouble(String path) {
		return getConfig().getDouble(path);
	}
	
	public static List<String> getStringList(String path) {
		List<String> s = getConfig().getStringList(path);
		return s;
	}
	
	public static ConfigurationSection getSection(String path) {
		return getConfig().getConfigurationSection(path);
	}
	//Files utils stop
	
	//Hex colors
	public static String format(String message) {
		
        if(Bukkit.getVersion().contains("1.16")) {
        	
            Pattern pattern = Pattern.compile("#[a-fA-F0-9]{6}");
            Matcher matcher = pattern.matcher(message);
            
            while (matcher.find()) {
                String color = message.substring(matcher.start(), matcher.end());
                message = message.replace(color, net.md_5.bungee.api.ChatColor.valueOf(color) + "");
                matcher = pattern.matcher(message);
            }
            
        }
        return ChatColor.translateAlternateColorCodes('&', message);
        
    }

	public static String color(String text) {
		return net.md_5.bungee.api.ChatColor.translateAlternateColorCodes('&', text);
	}
	
	//Auxiliary tools
	public static String print(String[] arr) {
        return Arrays.toString(arr);
    }
	
	public static boolean hasPermission(CommandSender p, String permission) {
		
		if(p.hasPermission(permission)) {
			return true;
		}
		
		Utils.sendMessage(p, Utils.getConfig().getString("messages.no_permission").replace("<permission>", permission)
				, true);
		return false;
		
	}
	
	public static void sendMessage(CommandSender player, String text, Boolean whis_prefix) {
		
		for (String line : text.split(";")) {

			line = format(line.replace("%player%", player.getName()));

			if(player instanceof Player && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

				line = PlaceholderAPI.setPlaceholders((Player) player, line);

			}

			if (line.startsWith("title:")) {

				if (player instanceof Player)
					//Title NMS
					Title.sendTitle((Player) player, line.split("title:")[1]);

			} else if (line.startsWith("actionbar:")) {

				if (player instanceof Player) {
					//ActionBar NMS
					ActionBar.sendActionBar((Player) player, line.split("actionbar:")[1]);
				}

			} else {
				if(whis_prefix){
					player.sendMessage(color(getMessage("prefix") + line));
					return;
				}
				player.sendMessage(color(line));
			}
		}
	}

	public static void sendMessage(CommandSender player, String text, Boolean with_prefix, Player target) {

		for (String line : text.split(";")) {

			line = format(line.replace("%player%", player.getName()));

			if(player instanceof Player && Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {

				line = PlaceholderAPI.setPlaceholders(target, line);

			}

			if (line.startsWith("title:")) {

				if (player instanceof Player)
					//Title NMS
					Title.sendTitle((Player) player, line.split("title:")[1]);

			} else if (line.startsWith("actionbar:")) {

				if (player instanceof Player) {
					//ActionBar NMS
					ActionBar.sendActionBar((Player) player, line.split("actionbar:")[1]);
				}

			} else {
				if(with_prefix){
					player.sendMessage(color(getMessage("prefix") + line));
					return;
				}
				player.sendMessage(color(line));
			}
		}
	}
	
	public static void denyCommands(Player p, YamlConfiguration y, String value) {
		
		if(y.contains(value)) {
			
			List<String> l = y.getStringList(value);

			for(String s : l) {

				int i = 0;

				if(s.startsWith("[console]")) {
					i = 1;
				} else if(s.startsWith("[message]")) {
					i = 2;
				} else if(s.startsWith("[player]")) {
					i = 3;
				} else if(s.startsWith("[close]")) {
					i = 4;
				} else if(s.startsWith("[sound]")) {
					i = 5;
				} else if(s.startsWith("[open]")) {
					i = 6;
				} else if(s.startsWith("[server]")) {
					i = 7;
				}

				switch (i){
					case (1):
						p.closeInventory();
						Bukkit.dispatchCommand(Bukkit.getConsoleSender(), ChatColor.translateAlternateColorCodes('&', s
								.replace("[console] ", "")
								.replace("[console]", "")
								.replace("%player%", p.getName())));
						break;
					case (2):
						Utils.sendMessage(p, s
								.replace("[message] ", "")
								.replace("[message]", "")
								.replace("%player%", p.getName()), false);
						break;
					case (3):
						p.closeInventory();
						Bukkit.dispatchCommand(p, ChatColor.translateAlternateColorCodes('&', s
								.replace("[player] ", "")
							  	.replace("[player]", "")
								.replace("%player%", p.getName())));
						break;
					case (4):
						p.closeInventory();
						break;
					case (5):
						String so = s.replace("[sound] ", "")
								.replace("[sound]", "");
						if(Sound.valueOf(so) != null){
							p.playSound(p.getLocation(), Sound.valueOf(so), 100, 100);
						}
						break;
					case (6):
						p.closeInventory();
						String op = s.replace("[open] ", "")
								.replace("[open]", "");
						File f = new File(Utils.getInstance().getDataFolder() + File.separator + "menus", op + ".yml");

						if(f != null) {
							YamlConfiguration ya = YamlConfiguration.loadConfiguration(f);
							if (ya != null) {
								Menu m = new Menu(p, ya);
								m.open();
							}
						}
						break;
					case (7):
						p.closeInventory();
						String serv = s.replace("[server] ", "")
								.replace("[server]", "");
						if(Main.bungeecheck()){
							sendServer(p, serv);
						} else {
							Logger.error("To send a player, you need to connect to BungeeCord");
						}
						break;
					default:
						break;
				}
			}
		}
	}
	//Moving the player across servers
	public static void sendServer(Player player, String server) {
		
		ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
		DataOutputStream datagramOutputStream = new DataOutputStream(byteArrayOutputStream);
		
		try {
			datagramOutputStream.writeUTF("Connect");
			datagramOutputStream.writeUTF(server);
			
		} catch (IOException e) {
			Logger.error("Error send player to server!");
		}
		
		player.sendPluginMessage(Main.getInstance(), "BungeeCord", byteArrayOutputStream.toByteArray());
		
	}
	

}
