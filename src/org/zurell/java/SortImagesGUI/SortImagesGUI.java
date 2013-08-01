/*
 * Created on 28.12.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/* $Id: SortImagesGUI.java,v 1.15 2006/01/14 08:24:29 zero_data Exp $ */
package org.zurell.java.SortImagesGUI;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.*;
import java.util.Properties;

/**
 * @author m0554
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SortImagesGUI {
	
	private GUI myGui;
	private Properties properties;
	static final String Version = "1.2.0";
	private Properties sysprops = System.getProperties();
	private String sep;
	public static void main(String[] args) {
		SortImagesGUI myObject = new SortImagesGUI();
		
	}
	
	public SortImagesGUI() {
		
		sep = sysprops.getProperty("file.separator");
		
		checkProperties();
		myGui = new GUI(properties);
		myGui.setTitle("SortImage GUI");
		Thread t = new Thread(myGui);
		t.start();
		
		
		
	}
	
	private void checkProperties() {
		
		
		/* Load the properties file */
		properties = new Properties();
		
		
		if(sysprops.getProperty("CONFIGFILE") == null) {
			// We have to look for a config file in the
			// users home directory, otherwise create a new one.
			File configfile = new File(sysprops.getProperty("user.home") + sep + ".sortimages");
			
			
			if (configfile.canRead()) {
				//Load the default configfile
				System.err.println("Loading default properties from user home directory.");
				loadProperties(configfile.getAbsolutePath());
			}
			else {
				// try to create new configfile in default location
				System.err.println("Try to create new configfile in user home directory.");
				try {
					properties.setProperty("PROPERTIESFILE", configfile.getAbsolutePath());
					properties.setProperty("USEDB", "FALSE");
					properties.store(new BufferedOutputStream(new FileOutputStream(configfile)),"SortImagesGUI Properties File");
				} catch (FileNotFoundException e1) {
					System.err.println("Couldn't create configfile: " + configfile.getAbsolutePath());
					e1.printStackTrace();
					System.exit(1);
				} catch (IOException e1) {
					System.err.println("Couldn't create configfile: " + configfile.getAbsolutePath());
					e1.printStackTrace();
					System.exit(1);
				}
				System.err.println("Created config: " + configfile.getAbsolutePath());
			}

		}
		else {
			// Load the provided configfile
			System.out.println("Read config file from " + sysprops.getProperty("CONFIGFILE"));
			loadProperties(sysprops.getProperty("CONFIGFILE"));
		}
		
		
	}

	/**
	 * 
	 */
	private void loadProperties(String configfile) {
		//User has provided a configfile, so use it
		BufferedInputStream  is;	
		try {
			is = new BufferedInputStream(new FileInputStream(configfile));
			properties.load(is);
		} catch (FileNotFoundException e1) {
			// TODO Auto-generated catch block
			System.err.println("Couldn't find CONFIGFILE: " + e1.toString());
			System.exit(1);
		}
		catch (IOException e2) {
			// TODO Auto-generated catch block
			System.err.println("Couldn't open CONFIGFILE: " + e2.toString());
			System.exit(1);
		}
		properties.setProperty("PROPERTIESFILE", configfile);
	}
}
