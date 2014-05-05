package de.peregrinus.ccli;

import java.sql.*;
import java.lang.String;

import de.peregrinus.h2.GenericDB;
import de.peregrinus.ccli.SongInfo;


/**
 * CCLI CopyReport database class
 * @author Christoph Fischer <christoph.fischer@volksmission.de>
 *
 */
public class SongRepository extends de.peregrinus.h2.GenericDB {

	public SongRepository(String fileName, String user, String password) {
		super(fileName, user, password);
	}
	
	public SongInfo findById(String ccliNumber) {
		ResultSet res = this.query("SELECT * FROM PUBLIC.TITLES WHERE songid='"+ccliNumber+"';");
		SongInfo si = null;
		try {
			if (res.next()) {
				System.out.println("Found song '" + res.getString("title") + " with ccli id #" + res.getString("songid"));
				si = new SongInfo(res.getString("songid"), res.getString("title"));
				si.setAuthor(this.getAuthorsById(ccliNumber));
				System.out.println("Authors: " + si.songAuthor);
				si.setSongBook(this.getSongBooksById(ccliNumber));
				System.out.println("Songbooks: " + si.songBook);
				si.setCopyright(this.getCopyrightsById(ccliNumber));
				System.out.println("Copyrights: (c)" + si.songCopyright);
			}
		} catch (SQLException e) {
			// not found
		}
		return si;
	}
	
	public String getAuthorsById(String ccliNumber) {
		ResultSet res = this.query("SELECT a.* FROM PUBLIC.SONGIDAUTHORIDLINK AS l LEFT JOIN AUTHORS a ON l.authorid=a.authorid WHERE l.songid='"+ccliNumber+"' ORDER BY l.orderseq;");
		String authors = "";
		try {
			while (res.next()) {
				if (authors != "") authors += ", ";
				authors += res.getString("firstname") + " " + res.getString("lastname");
			}
		} catch (SQLException e) {
			// not found
		}
		return authors;
	}

	public String getSongBooksById(String ccliNumber) {
		ResultSet res = this.query("SELECT b.* FROM PUBLIC.SONGIDBOOKIDLINK AS l LEFT JOIN BOOKS b ON l.bookid=b.bookid WHERE l.wordsid='"+ccliNumber+"';");
		String songBooks = "";
		try {
			// to avoid overflow, we're just getting the first songbook
			if (res.next()) {
				if (songBooks != "") songBooks += ", ";
				songBooks += res.getString("songbook");
			}
		} catch (SQLException e) {
			// not found
		}
		return songBooks;
	}
	
	public String getCopyrightsById(String ccliNumber) {
		ResultSet res = this.query("SELECT b.* FROM PUBLIC.SONGIDCOPYRIGHTIDLINK AS l LEFT JOIN COPYRIGHT b ON l.copyrightid=b.id WHERE l.songid='"+ccliNumber+"';");
		String copyrights = "";
		try {
			// to avoid overflow, we're just getting the first songbook
			// TODO filter by territory
			if (res.next()) {
				if (copyrights != "") copyrights += ", ";
				copyrights += res.getString("copyrightline");
			}
		} catch (SQLException e) {
			// not found
		}
		return copyrights;
	}
	
	
}
