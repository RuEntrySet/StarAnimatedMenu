package ru.entryset.events;

import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;
import org.bukkit.inventory.Inventory;
import ru.entryset.main.Main;
import ru.entryset.menu.Menu;
import ru.entryset.requirement.RequirementType;
import ru.entryset.tools.Logger;
import ru.entryset.tools.MenuFiles;
import ru.entryset.tools.Utils;

import java.util.ArrayList;

public class Events implements Listener {

	//We clean the player when leaving the server
	@EventHandler
	public void onClose(InventoryCloseEvent e) {
		if(!(e.getPlayer() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getPlayer();
		if(!Main.opening.containsKey(p)) {
			return;
		}
		if(e.getInventory().equals(Main.opening.get(p))) {
			Main.opening.remove(p);
		}
		if(Main.openingwithyml.containsKey(p)) {
			Main.openingwithyml.remove(p);
		}
	}
	 
	//Implementing commands to open the menu
	 
	@EventHandler
	public void onCommand(PlayerCommandPreprocessEvent e) {
		 
		Player p = e.getPlayer();

		String str = e.getMessage().replaceFirst("/", "");
		
		if(MenuFiles.menusbycommand.containsKey(str)) {
			
			Menu m = new Menu(p, MenuFiles.menusbycommand.get(str));
			 
			m.open();
			 
			e.setCancelled(true);
		}
	}
	 
	 //Checking all types of clicks, including fill clicks
	 
	@EventHandler
	public void onClick(InventoryClickEvent e) {
		if(!(e.getWhoClicked() instanceof Player)) {
			return;
		}
		Player p = (Player) e.getWhoClicked();
	    if(!Main.opening.containsKey(p)) {
			return;
		}
		if(!Main.openingwithyml.containsKey(p)) {
			Logger.error("Error 200 tell the developer");
			return;
		}
		Inventory pi = Main.opening.get(p);
		Inventory i = e.getClickedInventory();
		Inventory ic = e.getInventory();
		YamlConfiguration y = Main.openingwithyml.get(p);
		Menu m = new Menu(p, y);
		if(i == null) {
			return;
		}
		if(i.equals(pi)) {
			e.setCancelled(true);
			if(e.getClick() == null) {
				return;
			}
			int slot = e.getSlot();
			ClickType click = e.getClick();
			if(e.getCurrentItem() == null) {
				return;
			}
			if(e.getCurrentItem().getType().equals(Material.AIR) &&  !m.isAir()){
				return;
			}
			ArrayList<Integer> slotss = new ArrayList<Integer>();//Check Fill
			if(y.contains("content")) {
				for (String section : y.getConfigurationSection("content").getKeys(false)) {
					if (y.getIntegerList("content." + section + ".slots").contains(slot + 1)) {
						for (Integer sec : y.getIntegerList("content." + section + ".slots")) {
							if (!slotss.contains(sec)) {
								slotss.add(sec);
							}
						}
						String value = "content." + section;
						checkClick(e.getClick(), value, m, p, y);
					}
				}
			}
			if(!slotss.contains(e.getSlot() + 1)) {
				if(!y.getBoolean("settings.fill.enable")) {
					return;
				}
				String value = "settings.fill";
				checkClick(e.getClick(), value, m, p, y);
			}
		}

		//cancel the click in the player's inventory
		if(ic.equals(pi)) {
			if(m.cancelClick()) {
				e.setCancelled(true);
			}
		}
	}

	private void loadClick(RequirementType type, String value, Menu m, Player p, YamlConfiguration y){
		if(m.checkClick(value, type)) {
			Utils.denyCommands(p, y, value + "." + type.name().toLowerCase() + "_commands");
		}
	}

	private void checkClick(ClickType typeclick, String value, Menu m, Player p, YamlConfiguration y){
		switch (typeclick){
			case CONTROL_DROP:
				loadClick(RequirementType.control_drop_click, value, m, p, y);
				break;
			case DROP:
				loadClick(RequirementType.drop_click, value, m, p, y);
				break;
			case LEFT:
				loadClick(RequirementType.left_click, value, m, p, y);
				break;
			case RIGHT:
				loadClick(RequirementType.right_click, value, m, p, y);
				break;
			case SHIFT_LEFT:
				loadClick(RequirementType.shift_left_click, value, m, p, y);
				break;
			case SHIFT_RIGHT:
				loadClick(RequirementType.shift_right_click, value, m, p, y);
				break;
			case DOUBLE_CLICK:
				loadClick(RequirementType.double_click_click, value, m, p, y);
				break;
			case MIDDLE:
				loadClick(RequirementType.middle_click, value, m, p, y);
				break;
			case CREATIVE:
				loadClick(RequirementType.creative_click, value, m, p, y);
				break;
			case NUMBER_KEY:
				loadClick(RequirementType.number_key_click, value, m, p, y);
				break;
			case UNKNOWN:
				loadClick(RequirementType.unknown_click, value, m, p, y);
				break;
			case WINDOW_BORDER_LEFT:
				loadClick(RequirementType.window_border_left_click, value, m, p, y);
				break;
			case WINDOW_BORDER_RIGHT:
				loadClick(RequirementType.window_border_right_click, value, m, p, y);
				break;
			default:
				break;
		}
	}
}
