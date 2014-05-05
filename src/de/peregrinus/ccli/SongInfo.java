package de.peregrinus.ccli;

import java.lang.String;

public class SongInfo {
	public String id;
	public int organisationId = 1;
	public String wordsId;
	public int bookId = -1;
	public int musicId = -1;
	public boolean isStorage = true;
	public boolean isCustomArrangement = false;
	public boolean isPhotoCopy = false;
	public boolean isRecord = false;
	public boolean isProject = false;
	public boolean isPrint = false;
	public String songNo = ""; 
	public int periodId = 1;
	public String songTitle;
	public String songAuthor; 
	public String songBook; 
	public String songCopyright; 
	public int fixationsUsed = 0;
	

	public SongInfo(String id, String title) {
		this.id = id;
		this.wordsId = id;
		this.songTitle = title;
	}
	
	public void setAuthor (String author) {
		this.songAuthor = author;
	}
	
	public void setSongBook (String book) {
		this.songBook = book;
	}
	
	public void setCopyright (String copyright) {
		this.songCopyright = copyright;
	}
	
	public String getReportQuery() {
		return "";
	}
	
}
