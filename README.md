sortimages-gui
==============

This little Java program helps you sorting your images from digital still image cameras. 
It reads the EXIF-Header of the JPEG-Files an copies the images from the source dir to a 
centralized target dir sorted by year, month and day. The files are renamed to represent 
their capture date and time. So you will have a Image repository sorted by capture date of the images.

Additionally some of the image meta informations will be stored in a database along with 
the MD5 hash of the image as primary key. SortImages check each image beforce copying if 
it's already in the database. If not, the image will sorted into the appropriate folder.

This Software makes use of the EXIF-Reader Library from Drew Noakes. See https://code.google.com/p/metadata-extractor/ 
The needed mySQL-JDBC-Drivers from MySQL AB are also included. See http://www.mysql.com/downloads/api-jdbc-stable.html


Old Project Homepage on SourceForget.net
========================================

This project was originally started in 2003 on SourceForge.net

Old homepage can be found here: http://sortimages.sourceforge.net/



Usage
=====

SortImagesGUI comes as a JAR-file which should work on most platforms which support the Java 1.4 or above JVM. It uses Swing for the GUI. Normally you just have to do a double click on the JAR file or you can start it on the command line with something like this:

java -jar SortImagesGUI-1.2.0.jar

Of course you need a graphical user interface to use SortImagesGUI.

When you start SortImagesGUI it searches for it's properties file. If the file location is not specified at the command line it is searched in the current users home directory ( ~/.sortimages). If there is no properties file at all a new one will be created in the users home directory.

The properties file stores informations about the database connection and the default target directory.

 

If you haven't specified the database connection parameters before you should select "Properties" from the "File" menu and type in the connection parameters for the database.

You must create the database and the user to access the database prior to first usage. If you don't know the database structure you can call the DB-Help from the "Help" menu. This will print you the desired SQL commands to create the database:

`mysql>CREATE DATABASE sortimages;`

`mysql>GRANT ALL PRIVILEGES ON sortimages.* TO 'username'@'localhost' IDENTIFIED BY 'password';`

`mysql>`

DROP TABLE IF EXISTS `md5sums`;
CREATE TABLE `md5sums` (
`md5` varchar(32) NOT NULL default '',
`cameramodel` varchar(255) default NULL,
`cameramaker` varchar(255) default NULL,
`capturedate` datetime NOT NULL default '0000-00-00 00:00:00',
`height` mediumint(5) NOT NULL default '0',
`width` mediumint(5) NOT NULL default '0',
`flash` varchar(50) default NULL,
`aperture_time` varchar(50) default NULL,
`focal_length` varchar(50) default NULL,
`location` varchar(255) NOT NULL default '',
`original_name` varchar(255) NOT NULL default '',
`exposure_time` varchar(50) default NULL,
PRIMARY KEY (`md5`)
) ENGINE=MyISAM DEFAULT CHARSET=latin1;

After creating the database and entering the properties close the properties window. The data will be safed into the properties file.

Now you select the target folder. In this folder the images get copied during sorting. The top most directory will contain ein directory for each year you have sorted photographs. Every year will contain a dir for month' and every month - guess what - right, will contain directories for days.

Next step is to select a source folder. This folder ist searched recursively for JPEG-Images. Only JPEG-images are supported as only these images my contain EXIF-Metadata from the camera. After you select the source folder you can start sorting by pressing the "Sort!" button in the bottom of the main window.

Now each images is examined for EXIF metadata and the MD5 sum is calculated. The "Date taken" date is used to determine the target folder for the image. Then the images is copied to the target folder and will be renamed to a filename containing the date taken. The EXIF metadata are stored in the database with the MD5 sum as the primary key. This makes it very easy to find duplicates.

If the image is already found in the database (same MD5 sum) the the image is not copied but skipped.

If there is already a file with the same name in the target directory (already sorted but not in database or taken in the same second) then a underscore is prepended to the start of the filename. This is done repeatedly until the filename is unique in the directory.