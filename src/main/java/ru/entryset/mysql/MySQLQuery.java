package ru.entryset.mysql;

import ru.entryset.tools.Utils;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class MySQLQuery {

	MySQLConnect connection;

	private String connect;
	
	private String query;
	
	private String column;
	
	public MySQLQuery(String Query) {
		this.connect = Utils.getQuerys().getString("Querys." + Query + ".connect");
		this.query = Utils.getQuerys().getString("Querys." + Query + ".query");
		this.column = Utils.getQuerys().getString("Querys." + Query + ".column");
		this.connection = new MySQLConnect(this.connect);
		try {
			connection.connect();
		} catch (ClassNotFoundException e) {
		}
	}
	
	public MySQLConnect getConnection() {
		return this.connection;
	}
	
	private String getQuery() {
		return this.query;
	}
	
	private String getColumn() {
		return this.column;
	}
	
	public String get() {
		String string = "no_connect";
		try {
			PreparedStatement ps = getConnection().getConnection().prepareStatement(getQuery());
			ResultSet results = ps.executeQuery();
			if(results.next()) {
				string = results.getString(getColumn());
				return string;
			}
			return string;
		
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return string;
	}
	
}
