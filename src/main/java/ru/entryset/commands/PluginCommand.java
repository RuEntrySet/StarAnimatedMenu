package ru.entryset.commands;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;
import ru.entryset.main.Main;
import ru.entryset.menu.Menu;
import ru.entryset.tools.MenuFiles;
import ru.entryset.tools.Utils;

public class PluginCommand implements CommandExecutor, TabCompleter {

	//Execute command /sam
	@Override
	public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
		if (!Utils.hasPermission(sender, Utils.getString("permissions.default"))) {
			return false;
		}
		if(args.length == 0){
			if(sender instanceof Player) {
				Utils.sendMessage(sender, Utils.getMessage("player_usage"), true);
				return false;
			}
			Utils.sendMessage(sender, Utils.getMessage("console_usage"), true);
			return false;
		}
		if(args[0].equalsIgnoreCase("reload") || args[0].equalsIgnoreCase("rl")){
			if(!Utils.hasPermission(sender, Utils.getString("permissions.reload"))) {
				return false;
			}
			Main.Close();
			Main.opening.clear();
			Main.openingwithyml.clear();
			MenuFiles.menus.clear();
			MenuFiles.menusbycommand.clear();
			MenuFiles.menuslist.clear();
			Utils.reloadConfig();
			MenuFiles.loadGUIMenus();
			MenuFiles.loadGUIMenusMap();
			Utils.sendMessage(sender, Utils.getMessage("reload"), true);
			return true;
		}
		if(args[0].equalsIgnoreCase("list")) {
			if(!Utils.hasPermission(sender, Utils.getString("permissions.list"))) {
				return false;
			}
			for(String s : MenuFiles.menuslist) {
				Utils.sendMessage(sender, s, false);
			}
			return true;
		}
		if(args[0].equalsIgnoreCase("close")) {
			if(!Utils.hasPermission(sender, Utils.getString("permissions.close"))) {
				return false;
			}
			if(args.length >= 2){
				if(Bukkit.getPlayerExact(args[1]) == null) {
					Utils.sendMessage(sender, Utils.getMessage("player_offline"), true);
					return false;
				}
				Player target = Bukkit.getPlayerExact(args[1]);
				if(!Main.opening.containsKey(target)) {
					Utils.sendMessage(sender
							, Utils.getMessage("no_open").replace("<target>", target.getName()), true);
					return false;
				}
				target.closeInventory();
				Main.openingwithyml.remove(target);
				Main.opening.remove(target);
				Utils.sendMessage(sender
						, Utils.getMessage("close_other").replace("<target>", target.getName()), true);
				return true;
			}
			if(sender instanceof Player) {
				Utils.sendMessage(sender, Utils.getMessage("player_usage"), true);
				return false;
			}
			Utils.sendMessage(sender, Utils.getMessage("console_usage"), true);
			return false;
		}
		if(args[0].equalsIgnoreCase("open")) {
			if(!(sender instanceof Player)) {
				if(args.length >= 3 && Bukkit.getPlayerExact(args[1]) == null) {
					Utils.sendMessage(sender, Utils.getMessage("player_offline"), true);
					return false;
				}
				if(args.length >= 3) {
					Player target = Bukkit.getPlayerExact(args[1]);
					if(!MenuFiles.menuslist.contains(args[2])) {
						Utils.sendMessage(sender, Utils.getMessage("menu_not_exist"), true);
						return false;
					}
					Menu m = new Menu(target, Utils.getMenu(args[2]));
					if(Main.opening.containsKey(target)) {
						target.closeInventory();
						Main.openingwithyml.remove(target);
						Main.opening.remove(target);
					}
					m.open();
					Utils.sendMessage(sender, Utils.getMessage("open_others").replace("<menu>", args[2])
							.replace("<player>", target.getName()), true);
					return true;
				}
				Utils.sendMessage(sender, Utils.getMessage("console_usage"), true);
				return false;
			}
			Player p = (Player) sender;
			if(!Utils.hasPermission(p, Utils.getString("permissions.open"))) {
				return true;
			}
			if(args.length >= 2) {
				if(!MenuFiles.menuslist.contains(args[1])) {
					Utils.sendMessage(p, Utils.getMessage("menu_not_exist"), true);
					return false;
				}
				Menu m = new Menu(p, Utils.getMenu(args[1]));
				m.open();
				Utils.sendMessage(p, Utils.getMessage("open").replace("<menu>", args[1])
						.replace("<player>", p.getName()), true);
				return true;
			}
			Utils.sendMessage(sender, Utils.getMessage("player_usage"), true);
			return false;
		}
		if(sender instanceof Player) {
			Utils.sendMessage(sender, Utils.getMessage("player_usage"), true);
			return false;
		}
		Utils.sendMessage(sender, Utils.getMessage("console_usage"), true);
		return false;
	}

	//Сommand handler in tab
	@Override
	public List<String> onTabComplete(CommandSender sender, Command cmd, String label, String[] args) {
		if(Utils.hasPermission(sender, Utils.getString("permissions.default"))) {
			if(args.length == 1) {
				return filter(Arrays.asList("reload", "list", "open", "close"), args);
			}
			if(args.length == 2 && args[0].equalsIgnoreCase("open")) {
				if(sender instanceof Player) {
					String str = args[1];
					if(str.equalsIgnoreCase("") || str.equalsIgnoreCase(" ")){
						List<String> s = MenuFiles.menuslist;
						return s;
					}
					List<String> list = new ArrayList<String>();
					for(String s : MenuFiles.menuslist){
						if(s.startsWith(str)){
							list.add(s);
						}
					}
					return list;
				}
				ArrayList<String> s = new ArrayList<>();
				for(Player l : Bukkit.getOnlinePlayers()) {
					s.add(l.getName());
				}
				return s;
			}
			if(args.length == 3 && args[0].equalsIgnoreCase("open")) {
				if(!(sender instanceof Player)) {
					String str = args[2];
					if(str.equalsIgnoreCase("") || str.equalsIgnoreCase(" ")){
						List<String> s = MenuFiles.menuslist;
						return s;
					}
					List<String> list = new ArrayList<String>();
					for(String s : MenuFiles.menuslist){
						if(s.startsWith(str)){
							list.add(s);
						}
					}
					return list;
				}
				ArrayList<String> s = new ArrayList<>();
				for(Player l : Bukkit.getOnlinePlayers()) {
					s.add(l.getName());
				}
				return s;
			}
		}
		return null;
	}
	
	private List<String> filter(List<String> text, String[] args) {
		String last = args[args.length - 1].toLowerCase();
		List<String> result = new ArrayList<>();

		for (String s : text)
			if (s.startsWith(last))
				result.add(s);
		return result;
	}

}
