package de.peregrinus.h2;

import java.sql.*;
import java.lang.String;

public class GenericDB {

	protected Connection conn;
	
	/**
	 * Connect to a h2 database
	 *  
	 * @param fileName Path to the .h2.db file
	 * @param user User name 
	 * @param password Password 
	 * @return void
	 */
	public GenericDB(String fileName, String user, String password) {
		try {
			//driver for H2 db get from http://www.h2database.com
			Class.forName("org.h2.Driver");
			//create database from file
			this.conn = DriverManager.getConnection("jdbc:h2:file:" + fileName, user, password);
			this.conn.setAutoCommit(true);
		} catch (Exception e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Close the db connection
	 * @param void
	 * @return void
	 */
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}		
	}
	
	/**
	 * Execute a query and return the ResultSet
	 * @param sql Query String
	 * @return ResultSet
	 */
	public ResultSet query(String sql) {
		Statement stmt = null;
		ResultSet res = null;
		try {
			stmt = this.conn.createStatement();
			res = stmt.executeQuery(sql);
		} catch (SQLException e) {
			System.out.println("Query: " + sql);
			e.printStackTrace();
			System.exit(0);
		}
		return res;
	}

	/**
	 * Execute a query without any result
	 * @param sql Query string
	 */
	public void execute(String sql) {
		ResultSet res = this.query(sql);
	}
	

	/**
	 * Execute an INSERT query and return the insert id
	 * @param sql Query string
	 * @return Id of the inserted row
	 */
	public String create(String sql) {
		Statement stmt = null;
		ResultSet res = null;
		String Id = "";
		try {
			stmt = this.conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			res = stmt.getGeneratedKeys();
			if (res.next() && res != null) {
				Id = String.valueOf(res.getInt(1));
			}
		} catch (SQLException e) {
			System.out.println("Query: " + sql);
			e.printStackTrace();
			System.exit(0);
		}
		return Id;
	}
	
}
