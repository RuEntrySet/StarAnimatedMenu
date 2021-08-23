package ru.entryset.menu;

import java.util.ArrayList;
import java.util.List;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryType;
import org.bukkit.inventory.Inventory;
import ru.entryset.animation.Animation;
import ru.entryset.main.Main;
import ru.entryset.requirement.Requirement;
import ru.entryset.requirement.RequirementType;
import ru.entryset.tools.Logger;
import ru.entryset.tools.Utils;

public class Menu {

	private Player player;
	
	private YamlConfiguration yml;
	
	private InventoryType type;
	
	private Integer slots;
	
	private String title;
	
	private Boolean cancel_click;
	
	private Boolean isair;
	
	private Inventory i;
	
	//Create Menu
	public Menu(Player player, YamlConfiguration menu) {
		this.player = player; 
		this.yml = menu;
		loadSettings();
	}
	
	public Player getPlayer() {
		return this.player;
	}
	
	public YamlConfiguration getMenu() {
		return this.yml;
	}
	
	public InventoryType getType() {
		return this.type;
	}
	
	public Integer getSlotSize() {
		return this.slots;
	}
	
	public String getTitle() {
		return this.title;
	}
	
	public Inventory getInv() {
		return this.i;
	}
	
	public Boolean cancelClick() {
		return this.cancel_click;
	}
	
	public Boolean isAir() {
		return this.isair;
	}

	//seters

	//loadSettigns
	private void loadSettings(){

		if(yml.contains("settings.menu_type")) {
			if(yml.getString("settings.menu_type").equalsIgnoreCase("CHEST")){
				this.type = InventoryType.CHEST;
				if(yml.contains("settings.slot_size")) {
					this.slots = yml.getInt("settings.slot_size");
				} else {
					this.slots = 27;
				}
			} else if(InventoryType.valueOf(yml.getString("settings.menu_type")) != null){
				this.type = InventoryType.valueOf(yml.getString("settings.menu_type"));
			} else {
				Logger.error("Invalid value in one of your menus \"settings.menu_type\"");
			}
		}
		if(yml.contains("settings.menu_title")) {
			this.title = yml.getString(("settings.menu_title"));
		} else {
			this.title = Utils.format("You don`t set title");
		}

		if(yml.contains("settings.click_on_air_slot")) {
			this.isair = yml.getBoolean("settings.click_on_air_slot");
		} else {
			this.isair = false;
		}
		if(yml.contains("settings.cancelled_click_in_inventory_player")) {
			this.cancel_click = yml.getBoolean("settings.cancel_click_in_inventory_player");
		} else {
			this.cancel_click = false;
		}

	}

	//open Menu
	public void open(){
		if(!checkOpen())
			return;
		if(getType().equals(InventoryType.CHEST)) {
			this.i = Bukkit.createInventory(getPlayer(), getSlotSize(), Utils.format(getTitle()));
		} else {
			this.i = Bukkit.createInventory(getPlayer(), getType(), Utils.format(getTitle()));
		}

		if(Main.opening.containsKey(getPlayer())) {
			Main.opening.remove(getPlayer());
		}
		if(Main.openingwithyml.containsKey(getPlayer())) {
			Main.openingwithyml.remove(getPlayer());
		}
		Main.openingwithyml.put(getPlayer(), getMenu());
		Main.opening.put(getPlayer(), getInv());

		getPlayer().openInventory(getInv());

		List<Integer> slot_active = new ArrayList<Integer>();//for fill
		if(getMenu().contains("content")){
			for(String key : getMenu().getConfigurationSection("content").getKeys(false)) {
				if(!getMenu().contains("content." + key + ".slots")) {
					Logger.error("Set slot in the key " + key + " in file " + getMenu().getName());
					return;
				}
				for(Integer dd : getMenu().getIntegerList("content." + key + ".slots")){
					if(!slot_active.contains(dd-1)) {
						slot_active.add(dd-1);
					}
					loadMenu(dd-1, "content." + key);
				}
			}
		}
		if(getMenu().contains("settings.fill.enable")) {
			if(getMenu().getBoolean("settings.fill.enable")) {
				if(getMenu().contains("settings.fill.type")) {
					String type = getMenu().getString("settings.fill.type");
					if(type.equalsIgnoreCase("FULL")) {
						for(int ii = 0; ii < getSlotSize(); ii++) {
							if(!slot_active.contains(ii)) {
								loadMenu(ii, "settings.fill");
							}
						}
					}
					if(type.equalsIgnoreCase("SIDE")) {
						if(getType().equals(InventoryType.CHEST)) {
							loadSideChest(slot_active);
						}
					}
				}
			}
		}

	}

	private void loadSideInBlock(int i, int i2, List<Integer> slot_active){
		if(!slot_active.contains(i)) {
			loadMenu(i, "settings.fill");
		}
		if(!slot_active.contains(i2)) {
			loadMenu(i2, "settings.fill");
		}
	}

	private void loadSideInner(int i, List<Integer> slot_active){
		int inner1 = 1;
		int inner2 = 9;
		int inner3 = 0;
		for(inner1 = 1; inner1 <= 2; inner1++) {
			for(int ii = inner3; ii < inner2; ii++) {
				if(!slot_active.contains(ii)) {
					loadMenu(ii, "settings.fill");
				}
			}
			inner2 = i;
			inner3 = inner2 - 9;
		}
	}

	private void loadSideChest(List<Integer> slot_active){
		switch (getSlotSize()) {
			case 18 :
				loadSideInner(18, slot_active);
				break;
			case 27 :
				loadSideInner(27, slot_active);
				loadSideInBlock(9, 17, slot_active);
				break;
			case 36 :
				loadSideInner(36, slot_active);
				loadSideInBlock(9, 17, slot_active);
				loadSideInBlock(18, 26, slot_active);
				break;
			case 45 :
				loadSideInner(45, slot_active);
				loadSideInBlock(9, 17, slot_active);
				loadSideInBlock(18, 26, slot_active);
				loadSideInBlock(27, 35, slot_active);
				break;
			case 54 :
				loadSideInner(54, slot_active);
				loadSideInBlock(9, 17, slot_active);
				loadSideInBlock(18, 26, slot_active);
				loadSideInBlock(27, 35, slot_active);
				loadSideInBlock(36, 44, slot_active);
				break;
			default:
				for(int ii = 0; ii < 9; ii++) {
					if(!slot_active.contains(ii)) {
						loadMenu(ii, "settings.fill");
					}
				}
				break;
		}
	}
	//set items
	private void loadMenu(Integer slot, String value){
		if(!getMenu().contains(value + ".animation")) {
			Logger.error("You have not set the animation to the key " + value + " in file " + getMenu().getName());
			return;
		}
		if(!getMenu().contains(value + ".animation.type")) {
			Logger.error("You haven't set the animation type to the key " + value + " in file " + getMenu().getName());
			return;
		}
		Animation a = new Animation(getMenu(), slot, value, getInv(), getPlayer());
		a.startAnimation();
	}


	public Boolean checkOpen() {
		if(getMenu().contains("settings.open_requirements")) {
			Requirement r = new Requirement(getPlayer(), getMenu(), RequirementType.open, "settings");
			if(r.Check()) {
				return true;
			}
			return false;
		}
		return true;
	}

	public Boolean checkView(String section) {
		if(getMenu().contains(section + ".view_requirements")) {
			Requirement r = new Requirement(getPlayer(), getMenu(), RequirementType.view, section);
			if(r.Check()) {
				return true;
			}
			return false;
		}
		return true;

	}

	public Boolean checkClick(String section, RequirementType type) {
		if(getMenu().contains(section + "." + type.name() + "_requirements")) {
			Requirement r = new Requirement(getPlayer(), getMenu(), type, section);
			if(r.Check()) {
				return true;
			}
			return false;
		}
		return true;
	}
	
}
