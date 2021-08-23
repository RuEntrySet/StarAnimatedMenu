package ru.entryset.animation;

import java.util.List;

import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import ru.entryset.item.Controller;
import ru.entryset.main.Main;
import ru.entryset.menu.Menu;
import ru.entryset.tools.Logger;
import ru.entryset.tools.Runnable;

public class Animation {

	private YamlConfiguration menu;
	
	private AnimationType type;
	
	private String section;
	
	private Integer slot;
	
	private Inventory inv;
	
	private Player player;
	
	//Fill for keys
	public Animation(YamlConfiguration menu, Integer slot, String section, Inventory inv, Player player) {
		this.menu = menu;
		this.slot = slot;
		if(AnimationType.valueOf(getMenu().getString(section + ".animation.type")) == null) {
			Logger.error("You have set the wrong animation type to the key " + section + " in file " + menu.getName());
			this.type = AnimationType.update;
		} else {
			this.type = AnimationType.valueOf(getMenu().getString(section + ".animation.type"));
		}
		this.section = section;
		this.inv = inv;
		this.player = player;
	}
	
	private YamlConfiguration getMenu() {
		return this.menu;
	}
	
	private Player getPlayer() {
		return this.player;
	}
	
	private Integer getSlot() {
		return this.slot;
	}
	
	private AnimationType getType() {
		return this.type;
	}
	
	private Inventory getInv() {
		return this.inv;
	}
	
	private String getSection() {
		return this.section;
	}
	
	//Implementing animation through streams
	public void startAnimation() {
		Menu m = new Menu(getPlayer(), getMenu());
		if(!m.checkView(getSection())) {
			return;
		}
		switch (getType()) {
			case update:
				new Runnable() {
					@Override
					public void task() {
						if(!Main.opening.containsKey(getPlayer())){
							cancel();
							return;
						}
						if(!Main.opening.get(getPlayer()).equals(getInv())){
							cancel();
							return;
						}
						Controller i2 = new Controller(getPlayer()
								, getMenu().getString(getSection() + ".animation.default_item"));
						i2.setIngredient(getInv(), getSlot());
					}
				}.runTaskTimer(getMenu().getInt(getSection()
								+ ".animation.start_time"), getMenu().getInt(getSection()
								+ ".animation.update_time"));
				break;
			case run:
				List<String> list = getMenu().getStringList(getSection() + ".animation.run_items");
				new Runnable() {

					int time = 0;

					@Override
					public void task() {
						if(!Main.opening.containsKey(getPlayer())){
							cancel();
							return;
						}
						if(!Main.opening.get(getPlayer()).equals(getInv())){
							cancel();
							return;
						}
						Controller i3 = new Controller(getPlayer(), list.get(time));
						i3.setIngredient(getInv(), getSlot());
						if(time < (list.size() - 1)) {
							time++;
						} else {
							time = 0;
						}
					}

				}.runTaskTimer(getMenu().getInt(getSection()
								+ ".animation.start_time"), getMenu().getInt(getSection()
								+ ".animation.update_time"));
				break;
			case post:
				List<String> list2 = getMenu().getStringList(getSection() + ".animation.run_items");
				String item_default = getMenu().getString(getSection() + ".animation.default_item");

				new Runnable() {

					int iii = 0;

					@Override
					public void task() {
						if(!Main.opening.containsKey(getPlayer())){
							cancel();
							return;
						}
						if(!Main.opening.get(getPlayer()).equals(getInv())){
							cancel();
							return;
						}
						Controller i4 = new Controller(getPlayer(), list2.get(iii));
						i4.setIngredient(getInv(), getSlot());
						if(iii < (list2.size() - 1)) {
							iii++;
						} else {
							iii = 0;
						}
						new Runnable() {

							@Override
							public void task() {
								Controller i3 = new Controller(getPlayer()
										, getMenu().getString(getSection() + ".animation.default_item"));
								i3.setIngredient(getInv(), getSlot());
							}
						}.runTaskLater(getMenu().getInt(getSection()
								+ ".animation.post_time"));
					}
				}.runTaskTimer(getMenu().getInt(getSection()
								+ ".animation.start_time"), getMenu().getInt(getSection()
								+ ".animation.update_time"));
				Controller i4 = new Controller(getPlayer()
						, getMenu().getString(getSection() + ".animation.default_item"));
				i4.setIngredient(getInv(), getSlot());
				break;
			default:
				Controller i = new Controller(getPlayer()
						, getMenu().getString(getSection() + ".animation.default_item"));
				i.setIngredient(getInv(), getSlot());
				break;
		}
	}
}
