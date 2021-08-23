package ru.entryset.mysql;

import ru.entryset.tools.Logger;
import ru.entryset.tools.Utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class MySQLConnect {

	private String connect;
	
	private String host;
	private String port;
	private String database;
	private String username;
	private String password;
	
	private Connection connection;
	
	public MySQLConnect(String connect) {
		this.connect = connect;
		this.host = Utils.getQuerys().getString("Connects." + connect + ".host");
		this.port = Utils.getQuerys().getString("Connects." + connect + ".port");
		this.database = Utils.getQuerys().getString("Connects." + connect + ".database");
		this.username = Utils.getQuerys().getString("Connects." + connect + ".user");
		this.password = Utils.getQuerys().getString("Connects." + connect + ".password");
	}
	
	public boolean isConnected() {
		return (connection == null ? false : true);
	}
	
	public void connect() throws ClassNotFoundException {
		if(!isConnected()) {
			try {
				this.connection = DriverManager.getConnection("jdbc:mysql://" +
						this.host + ":" + this.port + "/" + this.database + "?useSSL=false",
					     this.username, this.password);
			} catch (SQLException e) {
				Logger.error("Failed to connect to database in connection " + this.connect + ", in file querys.yml");
			}
		}
	}
	
	public Connection getConnection() {
		return connection;
	}
	
}
