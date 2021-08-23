package ru.entryset.main;

import org.bukkit.Bukkit;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;
import ru.entryset.commands.PluginCommand;
import ru.entryset.events.Events;
import ru.entryset.placeholders.PlaceholderAPI;
import ru.entryset.tools.Logger;
import ru.entryset.tools.MenuFiles;
import ru.entryset.tools.Runnable;
import ru.entryset.tools.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class Main extends JavaPlugin {

    private static Main instance;

    public static List<Runnable> runnables = new ArrayList<>();

    public static HashMap<Player, Inventory> opening = new HashMap<Player, Inventory>();

    public static HashMap<Player, YamlConfiguration> openingwithyml = new HashMap<Player, YamlConfiguration>();

    @Override
    public void onEnable() {
        instance = this;
        Utils.setInstance(this);
        Utils.reloadConfig();
        MenuFiles.loadGUIMenus();
        MenuFiles.loadGUIMenusMap();
        getServer().getMessenger().registerOutgoingPluginChannel((Plugin)Main.getInstance(), "BungeeCord");
        registerEvents();
        registerCommands();
        registerTabs();
        if(Bukkit.getPluginManager().getPlugin("PlaceholderAPI") != null) {
            new PlaceholderAPI().register();
        }
        Logger.enable("&fPlugin enabled &asuccessfully&f!");
    }

    @Override
    public void onDisable() {
        for(Runnable runnable : runnables){
            runnable.cancel();
        }
        Main.Close();
    }

    private void registerEvents() {
        Bukkit.getPluginManager().registerEvents(new Events(), this);
    }

    private void registerCommands() {
        getCommand("staranimatedmenu").setExecutor(new PluginCommand());
    }

    private void registerTabs() {
        getCommand("staranimatedmenu").setTabCompleter(new PluginCommand());
    }

    public static void Close() {
        for(Player p : Bukkit.getOnlinePlayers()) {
            if(!Main.opening.containsKey(p)) {
                return;
            }
            Main.opening.remove(p);
            p.closeInventory();
            if(Main.openingwithyml.containsKey(p)) {
                Main.openingwithyml.remove(p);
            }
        }
    }

    public static boolean bungeecheck() {
        if(Bukkit.getServer().getMessenger().isOutgoingChannelRegistered((Plugin)Main.getInstance(), "BungeeCord")) {
            return true;
        }
        return false;
    }

    public static Main getInstance() {
        return Main.instance;
    }

}
