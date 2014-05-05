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
public class CopyReport extends de.peregrinus.h2.GenericDB {
	
	private String periodId = "1";
	
	private String quote(String s) {
		return "'"+s+"'";
	}
	
	public CopyReport(String fileName, String user, String password) {
		super(fileName, user, password);
	}
	
	public void reportSong(SongInfo si) {
		PreparedStatement s;
		try {
			s = this.conn.prepareStatement("INSERT INTO SONGUSAGE (songbook, songcopyright, songauthor, songtitle, musicid, songno, periodid, isstorage, iscustomarrangement, isphotocopy, isrecord, isproject, isprint, bookid, wordsid, organisationid, fixationsUsed)  VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?, ?);");
			//s.setString(1, si.songBook);
			s.setString(1, "");
			s.setString(2, si.songCopyright);
			s.setString(3, si.songAuthor);
			s.setString(4, si.songTitle);
			s.setInt(5, si.musicId);
			s.setString(6, si.songNo);
			s.setInt(7, Integer.valueOf(this.periodId));
			s.setBoolean(8, si.isStorage);
			s.setBoolean(9, si.isCustomArrangement);
			s.setBoolean(10, si.isPhotoCopy);
			s.setBoolean(11, si.isRecord);
			s.setBoolean(12, si.isProject);
			s.setBoolean(13, si.isPrint);
			s.setString(14, String.valueOf(si.bookId));
			s.setInt(15, Integer.valueOf(si.wordsId));
			s.setInt(16,  si.organisationId);
			s.setNull(17, java.sql.Types.INTEGER);
			Boolean success = s.execute();
			System.out.println ("Success : " + String.valueOf(success));
			s.close();
			Statement s2 = this.conn.createStatement();
			s2.execute("COMMIT");
			s2.close();
		} catch (SQLException e) {
			e.printStackTrace();
			System.exit(0);
		}
	}
	
	public void createPeriod(String year) {
		this.periodId = this.create("INSERT INTO PERIODS (organisationid, type, startdate, enddate, complete) "
				+"VALUES (1, 1, '" + year + "-01-01', '" + year + "-12-31', false);");		
	}
		
}
