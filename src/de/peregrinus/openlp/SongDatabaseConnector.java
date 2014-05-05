package de.peregrinus.openlp;

import java.sql.*;
import java.lang.String;
import java.util.ArrayList;
import java.util.List;
import de.peregrinus.openlp.Song;

/**
 * OpenLP songs database class 
 * @author Christoph Fischer <christoph.fischer@volksmission.de>
 * 
 */
public class SongDatabaseConnector {

	private Connection conn;
	
	/**
	 * Connect to an OpenLP songs database
	 * @param fileName Path to the database
	 */
	public SongDatabaseConnector(String fileName) {
		try {
			Class.forName("org.sqlite.JDBC");
			this.conn = DriverManager.getConnection("jdbc:sqlite:" + fileName);
		} catch ( Exception e ) {
			System.err.println( e.getClass().getName() + ": " + e.getMessage() );
			System.exit(0);
		}		
	}
	
	public List<Song> findLicensedSongs() {
		Statement stmt;
		List<Song> songs = new ArrayList<Song>();
		
		try {
			stmt = this.conn.createStatement();
			ResultSet res = stmt.executeQuery("SELECT * FROM songs WHERE ccli_number != \"\";");
			
			while (res.next()) {
				songs.add(new Song(res.getString("title"), res.getString("ccli_number")));
			}
			res.close();
			stmt.close();
			
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
		return songs;
	}

	/**
	 * Close the database connection
	 */
	public void close() {
		try {
			this.conn.close();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
}
