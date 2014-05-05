package de.peregrinus.autocopyreport;

import de.peregrinus.ccli.*;
import de.peregrinus.openlp.*;
import java.util.List;
import org.apache.commons.cli.*;


public class AutoCopyReport {
	
	public static void main(String[] args) {
		SongInfo si;
		
		String openlpPath = "";
		String crPath = "";
		String crDataPath = "";
		String crPassword = "";
		
		System.out.println("AutoCopyReport v.1.00.00");
		System.out.println("(c) 2014 Volksmission Freudenstadt");
		System.out.println("Author: Christoph Fischer <christoph.fischer@volksmission.de>");
		//System.out.println("Available under the General Public License (GPL) v2");
		System.out.println();
		
		
		// get options
		Options options = new Options();
		options.addOption( "h", "help", false, "display a list of available options" );
		options.addOption( OptionBuilder.withLongOpt( "openlp-path" )
                .withDescription( "path to OpenLP data directory" )
                .hasArg()
                .withArgName("PATH")
                .create() );
		options.addOption( OptionBuilder.withLongOpt( "copyreport-path" )
                .withDescription( "path to the CopyReport program directory" )
                .hasArg()
                .withArgName("PATH")
                .create() );
		options.addOption( OptionBuilder.withLongOpt( "copyreport-data-path" )
                .withDescription( "path to the CopyReport data directory" )
                .hasArg()
                .withArgName("PATH")
                .create() );
		options.addOption( OptionBuilder.withLongOpt( "password" )
                .withDescription( "internal password for the CopyReport database" )
                .hasArg()
                .withArgName("PASSWORD")
                .create() );
		
			
		// parse command line
		try {
		    // parse the command line arguments
			CommandLineParser parser = new GnuParser();
			CommandLine line = parser.parse( options, args );

		    // validate that block-size has been set
		    if( line.hasOption( "help" ) ) {
		    	// automatically generate the help statement
		    	HelpFormatter formatter = new HelpFormatter();
		    	formatter.printHelp( "AutoCopyReport", options );
		    	System.exit(0);
		    } else {
		    	if (line.hasOption ("openlp-path")) {
		    		openlpPath = line.getOptionValue("openlp-path") + "/songs/songs.sqlite";
		    		System.out.println("OpenLP data path: " + openlpPath);
		    	} else {
		    		System.out.println("ERROR: Please supply the path to your OpenLP data folder by using the --openlp-path parameter.");
		    		System.exit(0);
		    	}
		    	if (line.hasOption ("copyreport-path")) {
		    		crPath = line.getOptionValue("copyreport-path");
		    		System.out.println("Copyreport application database: " + crPath + "/ccldata.h2.db");
		    	} else {
		    		System.out.println("ERROR: Please supply the path to your CopyReport program folder by using the --copyreport-path parameter.");
		    		System.exit(0);
		    	}
		    	if (line.hasOption ("copyreport-data-path")) {
		    		crDataPath = line.getOptionValue("copyreport-data-path");
		    		System.out.println("Copyreport user database: " + crDataPath + "/userdata.h2.db");
		    	} else {
		    		System.out.println("ERROR: Please supply the path to your CopyReport data folder by using the --copyreport-data-path parameter.");
		    		System.exit(0);
		    	}
		    	if (line.hasOption ("password")) {
		    		crPassword = line.getOptionValue("password");
		    	} else {
		    		System.out.println("ERROR: Please supply the internal password for the CopyReport database by using the --password parameter.");
		    		System.exit(0);
		    	}
		    }
		}
		catch( ParseException exp ) {
		    System.out.println( "Unexpected exception:" + exp.getMessage() );
		    System.exit(0);
		}
		
		
		SongDatabaseConnector sdb = new SongDatabaseConnector(openlpPath);
		SongRepository cdb = new SongRepository(crPath + "/ccldata", "sa", crPassword);

		CopyReport rpt = new CopyReport(crDataPath + "/userdata", "sa", crPassword);
		//rpt.createPeriod("2014");
		
		List<Song> songs = sdb.findLicensedSongs();
		for (Song song : songs) {
			System.out.println(song.title + ": " + song.ccliNumber);
			if (song.ccliNumber.matches("-?\\d+(\\.\\d+)?")) {
				si = cdb.findById(song.ccliNumber);
				if (si != null) rpt.reportSong(si);
			} else {
				System.out.println("Format error: '" + song.ccliNumber + "' is not numeric.");
			}
		}
		
		// wrap up: close the databases
		cdb.close();
		rpt.close();
		sdb.close();
	
	
	}

}
