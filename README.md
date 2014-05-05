AutoCopyReport
==============

If you're using OpenLP (http://www.openlp.org) to project lyrics licensed from CCLI (http://www.ccli.de in our case), you know the problem: 
Although the CCLI numbers are stored in your OpenLP database, you have to go through a rather tedious manual reporting process at 
the end of each year with the CCLI CopyReport tool.

AutoCopyReport takes care of most of this process by importing the correct songs into your CopyReport.

Prerequisites
-------------
* OpenLP 2.x
* CopyReport (This has been tested with v4.5.9.864)
* Java (This has been tested with JRE 1.7.0_55)

Usage
-----
* Open CopyReport and make sure it is configured correctly (inital setup done).
* Simply copy the .jar file somewhere on your computer and start it with the correct parameters:

    ```
    java -jar AutoCopyReport.jar --openlp-path /path/to/openlp/data --copyreport-path /path/to/copyreport/app --copyreport-data-path /path/to/copyreport/data --password thesecretpassword
    ```

* Edit and submit your report as usual through the CopyReport interface.

Password
--------

The internal CopyReport database is protected by a password, which I won't publish here for copyright reasons. 
You can ask CCLI nicely for the password or you can figure it out yourself from the ```copyreport.jar``` file,
e.g. by using tools like jd-gui (http://jd.benow.ca/) and taking a look at the ```public static Connection D()``` method in the 
```A.A.A.A.C.D.A``` class.

Known problems
--------------

* At the moment, AutoCopyReport always reports songs for the first license period in your CopyReport database. A workaround is to delete
  all userdata.* files in your CopyReport data folder. You will need to go through the initial setup steps, though.
* The current German CopyReport version (v.4.5.9.864) does not show all of your OpenLP songs under "Gemeldete Lieder". 
  You can see that the selection works when you filter for a song in "Liedtitel", though, and the final report also
  correctly contains all the songs.

Licensing
---------
AutoCopyReport is licensed under the terms of the GPL v2 license. The following libraries have been used and may have different licensing terms and conditions:
* Apache Commons CLI 1.2 (Apache License 2.0), http://commons.apache.org/proper/commons-cli/
* H2 Database 1.3.171 (H2 license, http://www.h2database.com/html/license.html) http://www.h2database.com/
* sqlite-jdbc 3.7.2 (Apache License 2.0), https://bitbucket.org/xerial/sqlite-jdbc

Credits
-------
AutoCopyReport was written by Christoph Fischer (chris@toph.de) for Volksmission Freudenstadt (http://open.vmfds.de).
