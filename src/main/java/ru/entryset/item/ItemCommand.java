package ru.entryset.item;

import org.bukkit.ChatColor;
import org.bukkit.block.CommandBlock;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.BlockStateMeta;
import ru.entryset.tools.MenuFiles;

public class ItemCommand extends Item {
	
	private String command;
	
	private String commandname;

	public ItemCommand(Player p, String value, FileConfiguration y) {
		super(p, value, y);
		
		BlockStateMeta meta = (BlockStateMeta) getMeta();
		load(value, y, meta);
		setItemMeta(meta);
	}
	 
	private void load(String value, FileConfiguration y, BlockStateMeta meta) {
		CommandBlock state = (CommandBlock) meta.getBlockState();
		if(y.contains(value + ".Command")) {
			this.command = MenuFiles.PAPI(getPlayer(), y.getString(value + ".Command"));
			state.setCommand(getCommand());
		}
		if(y.contains(value + ".CName")) {
			this.commandname = MenuFiles.PAPI(getPlayer(), ChatColor.translateAlternateColorCodes('&'
					, y.getString(value + ".CName")));
			state.setName(getCommandName());
		}
		meta.setBlockState(state);
	}
	
	public String getCommand() {
		return this.command;
	}
	
	public String getCommandName() {
		return this.commandname;
	}
	
	

}
