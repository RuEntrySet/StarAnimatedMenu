package ru.entryset.tools;

import org.bukkit.Bukkit;

public class Logger {

	public static void info(String text) {
		Bukkit.getConsoleSender().sendMessage(Utils.format(Utils.color("&a(" + Utils.getInstance().getName() + "/INFO) " + text)));
	}

	public static void warn(String text) {
		Bukkit.getConsoleSender().sendMessage(Utils.format(Utils.color("&6(" + Utils.getInstance().getName() + "/WARN) " + text)));
	}

	public static void error(String text) {
		Bukkit.getConsoleSender().sendMessage(Utils.format(Utils.color("&c(" + Utils.getInstance().getName() + "/ERROR) " + text)));
	}

	public  static  void enable(String text) {
		Bukkit.getConsoleSender().sendMessage(Utils.format(Utils.color("&a[&f" + Utils.getInstance().getName() + "&a] " + text + "&r")));
	}

}
