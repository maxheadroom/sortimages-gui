/*
 * Created on 28.12.2003
 * 
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */

/* $Id: GUI.java,v 1.12 2005/11/17 07:18:56 zero_data Exp $ */

package org.zurell.java.SortImagesGUI;

import java.awt.BorderLayout;
import java.awt.Component;
import java.awt.Container;
import java.awt.FlowLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileFilter;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;
import java.util.regex.Pattern;

import javax.swing.JDialog;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JProgressBar;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;

/**
 * @author m0554
 * 
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class GUI
	extends JFrame
	implements Runnable, ActionListener, ChangeListener {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	/**
	 * Comment for <code>properties - holds global system properties. This is passed from the main class to the constructor of this gui class.</code>
	 */
	// Variable Declaration
	private Properties properties;
	private String Version = SortImagesGUI.Version;
	private Container cp;
	private JButton sourceBrowseButton,
		startSortButton,
		exitButton,
		targetBrowseButton;
	private JScrollPane scrollPane;
	private JMenuItem exitAppMenuItem,
		browseTargetMenuItem,
		browseSourceMenuItem,
		setPropertiesMenuItem,
		dbHelpMenuItem,
		usageHelpMenuItem,
		aboutHelpMenuItem;
	private JMenu fileMenu, helpMenu;
	private JTextArea outputTextArea;
	private JTextField sourceTextField, targetTextField;
	private JLabel sourceLabel, targetLabel, fileLabel, allLabel;
	private JPanel headerPanel, middlePanel, footerPanel;
	private JFileChooser sourceFileChooser;
	private JProgressBar fileProgress, allProgress;
	private HashMap files;
	private ImageSorter imagesorter;
	private JDialog jd;
	/**
	 * This Constructor takes a properties object which should hold the global
	 * configuration as fetched from CONFIGFILE by the main class.
	 * 
	 * @param props -
	 *                    Properties from the main class.
	 */
	// Finish declaration;

	public GUI(Properties props) {
		// Constructor

		this.properties = props;
		this.setBounds(100,100,400,600);

	}

	/**
	 * This method initiates all the Swing GUI components
	 *  
	 */
	private void initComponents() {

		cp = this.getContentPane();

		fileProgress = new JProgressBar(JProgressBar.HORIZONTAL, 0, 4);
		allProgress = new JProgressBar(JProgressBar.HORIZONTAL);
		fileProgress.setStringPainted(true);
		allProgress.setStringPainted(true);
		fileProgress.addChangeListener(this);
		allProgress.addChangeListener(this);
		fileLabel = new JLabel("File: ");
		allLabel = new JLabel("Number of Files: ");
		sourceFileChooser = new JFileChooser();
		sourceFileChooser.setAcceptAllFileFilterUsed(false);
		sourceFileChooser.setFileFilter(new SIGFileFilter());
		sourceFileChooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
		sourceFileChooser.setDialogTitle("Choose Source Directory");

		targetBrowseButton = new JButton("Target Dir");
		targetBrowseButton.setToolTipText("Set new target directory");
		targetBrowseButton.addActionListener(this);

		sourceBrowseButton = new JButton("Source Dir");
		sourceBrowseButton.setToolTipText("Browse for source directory");
		sourceBrowseButton.addActionListener(this);

		startSortButton = new JButton("Sort!");
		startSortButton.setToolTipText("Start sorting");
		startSortButton.addActionListener(this);

		exitButton = new JButton("Exit");
		exitButton.setToolTipText("Exit the Application");
		exitButton.addActionListener(this);

		sourceLabel =
			new JLabel("<html><font color=\"red\">Source: </font></html>");
		targetLabel = new JLabel("Target: ");
		sourceTextField = new JTextField(20);
		targetTextField = new JTextField(20);
		targetTextField.setText(properties.getProperty("PHOTODIR"));
		targetTextField.setEditable(false);
		sourceTextField.setEditable(false);

		setJMenuBar(initMenu());

		headerPanel = new JPanel(new BorderLayout());

		JPanel headerNorthPanel = new JPanel(new FlowLayout());
		JPanel headerSouthPanel = new JPanel(new FlowLayout());

		headerNorthPanel.add(targetLabel);
		headerNorthPanel.add(targetTextField);
		headerNorthPanel.add(targetBrowseButton);

		headerSouthPanel.add(sourceLabel);
		headerSouthPanel.add(sourceTextField);
		headerSouthPanel.add(sourceBrowseButton);

		headerPanel.add(headerNorthPanel, BorderLayout.NORTH);
		headerPanel.add(headerSouthPanel, BorderLayout.SOUTH);

		footerPanel = new JPanel(new FlowLayout());
		footerPanel.add(startSortButton);
		footerPanel.add(exitButton);

		middlePanel = new JPanel(new BorderLayout());

		outputTextArea = new JTextArea(22, 20);
		outputTextArea.setAutoscrolls(true);
		outputTextArea.setEditable(false);
		outputTextArea.setLineWrap(true);
		scrollPane = new JScrollPane(outputTextArea);
		JPanel middleNorthPanel = new JPanel(new BorderLayout());
		JPanel middleSouthPanel = new JPanel(new BorderLayout());
		
		
		middleNorthPanel.add(fileLabel, BorderLayout.NORTH);
		middleNorthPanel.add(fileProgress, BorderLayout.SOUTH);

		middleSouthPanel.add(allLabel, BorderLayout.NORTH);
		middleSouthPanel.add(allProgress, BorderLayout.SOUTH);
		
		middlePanel.add(middleNorthPanel, BorderLayout.NORTH);
		middlePanel.add(scrollPane, BorderLayout.CENTER);
		middlePanel.add(middleSouthPanel, BorderLayout.SOUTH);

		cp.setLayout(new BorderLayout());
		cp.add(headerPanel, BorderLayout.NORTH);
		cp.add(footerPanel, BorderLayout.SOUTH);
		cp.add(middlePanel, BorderLayout.CENTER);

	}

	/**
	 * If the button for the target directory is pressed or the corresponding
	 * menu item is selected then this method is called. It calls a browse
	 * dialog and resets the textfield displaying the choosen target directory.
	 * The property containing the actual target directory is overwritten (but
	 * not saved permanently).
	 * 
	 * @param e -
	 *                    the ActionEvent from the action handling method. Is not
	 *                    currently used.
	 */
	protected void targetBrowseButtonActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		targetTextField.setText(browseSource(this));
		properties.setProperty("PHOTODIR", targetTextField.getText());
	}

	/**
	 * This method calls the FileChooser Dialog
	 * 
	 * @param parent
	 * @return the canonical path of the choosen directory.
	 */
	protected String browseSource(Component parent) {

		int returnVal = sourceFileChooser.showDialog(parent, "Select");
		if (returnVal == JFileChooser.APPROVE_OPTION) {
			try {
				return sourceFileChooser.getSelectedFile().getCanonicalPath();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				return "Error";
			}
		} else {
			return "No file choosed";
		}
	}

	/**
	 * Initialises the Menubar
	 * 
	 * @return a JMenuBar object for inserting into a JFrame
	 */
	protected JMenuBar initMenu() {
		JMenuBar ret = new JMenuBar();

		fileMenu = new JMenu("File");
		fileMenu.setMnemonic('F');

		setPropertiesMenuItem = new JMenuItem("Properties");
		setPropertiesMenuItem.addActionListener(this);

		browseTargetMenuItem = new JMenuItem("Target Dir");
		browseTargetMenuItem.addActionListener(this);

		browseSourceMenuItem = new JMenuItem("Source Dir");
		browseSourceMenuItem.addActionListener(this);

		exitAppMenuItem = new JMenuItem("Exit");
		exitAppMenuItem.addActionListener(this);

		fileMenu.add(browseTargetMenuItem);
		fileMenu.add(browseSourceMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(setPropertiesMenuItem);
		fileMenu.addSeparator();
		fileMenu.add(exitAppMenuItem);

		ret.add(fileMenu);

		helpMenu = new JMenu("Help");

		dbHelpMenuItem = new JMenuItem("Help on Database");
		dbHelpMenuItem.addActionListener(this);

		usageHelpMenuItem = new JMenuItem("Help on Usage");
		usageHelpMenuItem.addActionListener(this);

		aboutHelpMenuItem = new JMenuItem("About");
		aboutHelpMenuItem.addActionListener(this);

		helpMenu.add(dbHelpMenuItem);
		helpMenu.add(usageHelpMenuItem);
		helpMenu.add(aboutHelpMenuItem);

		ret.add(helpMenu);
		return ret;

	}

	/**
	 * If the properties entry from the File menu is choosed this method is
	 * called and will show a Dialog for entering the Database properties.
	 * Additionally the default target directory can be set. This dialog will
	 * save the properties to the global properties file. After finishing the
	 * dialog the TextField displaying the current target directory is
	 * actualized with the value from the properties file.
	 * 
	 * @param e
	 */
	protected void setPropertiesMenuItemActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		SetPropertiesGUI propsgui = new SetPropertiesGUI(this, properties);
		propsgui.setTitle("Database Properties");
		propsgui.setVisible(true);
				
		loadProperties(properties.getProperty("PROPERTIESFILE"));
		targetTextField.setText(properties.getProperty("PHOTODIR"));
		
		
		

	}

	/**
	 * This method displays public information about this Application
	 * 
	 * @param e
	 */
	protected void aboutHelpMenuItemActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		String helpmessage = "Public Information\n";
		helpmessage = helpmessage + "\n";
		helpmessage = helpmessage + "SortImagesGUI by Falko Zurell\n";
		helpmessage = helpmessage + "\n";
		helpmessage = helpmessage + "Version: " + this.Version + "\n";
		helpmessage = helpmessage + "License: GNU Public License\n";
		helpmessage =
			helpmessage + "Homepage: http://sortimages.sourceforge.net\n";

		JOptionPane.showConfirmDialog(
			this,
			helpmessage,
			"common help",
			JOptionPane.CLOSED_OPTION,
			JOptionPane.INFORMATION_MESSAGE);

	}

	/**
	 * This message gives help on usage of this application.
	 * 
	 * @param e
	 */
	protected void usageHelpMenuItemActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		String helpmessage = "Usage Information\n";
		helpmessage = helpmessage + "\n";
		helpmessage =
			helpmessage
				+ "If not already done choose Properties from the File menu\n";
		helpmessage = helpmessage + "and fill in your database informations.\n";
		helpmessage = helpmessage + "\n";
		helpmessage =
			helpmessage
				+ "Then select a source directory to search for images\n";
		helpmessage = helpmessage + "and press the Sort! button.\n";
		helpmessage = helpmessage + "\n";
		helpmessage = helpmessage + "That's all.\n";
		JOptionPane.showConfirmDialog(
			this,
			helpmessage,
			"Usage help",
			JOptionPane.CLOSED_OPTION,
			JOptionPane.INFORMATION_MESSAGE);

	}

	/**
	 * This message displays information on how to set up the database
	 * 
	 * @param e
	 */
	protected void dbHelpMenuItemActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		jd = new JDialog(this, "Database help");
		JPanel panel = new JPanel(new BorderLayout());
		JButton exitButton = new JButton("Ok");
		exitButton.addActionListener(this);
		JTextArea output = new JTextArea(20, 30);
		output.setEditable(false);
		JScrollPane helpscrollpane = new JScrollPane(output);

		output.append(
			"In order to use SortImagesGUI you will need a mySQL database set up.\n");
		output.append("Use this database scheme to create your database:\n\n");
		output.append("CREATE DATABASE /*!32312 IF NOT EXISTS*/ sortimages;\n");
		output.append("USE sortimages;\n");
		output.append("\n");
		output.append("CREATE TABLE md5sums (\n");
		output.append("\tmd5 varchar(32) NOT NULL default '',\n");
		output.append("\tcameramodel varchar(255) default NULL,\n");
		output.append("\tcameramaker varchar(255) default NULL,\n");
		output.append(
			"\tcapturedate datetime NOT NULL default '0000-00-00 00:00:00',\n");
		output.append("\theight mediumint(5) NOT NULL default '0',\n");
		output.append("\twidth mediumint(5) NOT NULL default '0',\n");
		output.append("\tflash varchar(50) default NULL,\n");
		output.append("\taperture_time varchar(50) default NULL,\n");
		output.append("\tfocal_length varchar(50) default NULL,\n");
		output.append("\tlocation varchar(255) NOT NULL default '',\n");
		output.append("\toriginal_name varchar(255) NOT NULL default '',\n");
		output.append("\texposure_time varchar(50) default NULL,\n");
		output.append("\tPRIMARY KEY  (md5)\n");
		output.append(") TYPE=MyISAM;\n");

		panel.add(helpscrollpane, BorderLayout.CENTER);
		panel.add(exitButton, BorderLayout.SOUTH);
		jd.getContentPane().add(panel);
		jd.pack();
		jd.setVisible(true);

	}

	/**
	 * If an Exitbutton is pressed or the corresponding menu item is choosed
	 * this method will end the application.
	 * 
	 * @param e
	 */
	protected void exitButtonActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		System.exit(0);
	}

	/**
	 * If the Start Button is pressed then a new ImageSorter instance is
	 * created and the thread will be started.
	 * 
	 * @param e
	 */
	protected void startSortButtonActionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub
		outputTextArea.setText(null);
		imagesorter = new ImageSorter(this, properties, files);
		imagesorter.start();

	}

	/**
	 * THis method will find all the JPEG files in the source directory. It
	 * creates a hashmap containing all the found images and actualize the
	 * ProgressBar for the overall progress with the size of the HashMap as
	 * MaxValue.
	 * 
	 * @param dirname -
	 *                    the source directory as displayed in the sourceTextField
	 * @return true if no error has occured during scan and at least one file
	 *               was found.
	 */
	private boolean findFiles(String dirname) {
		
		File[] dir;
		File source = new File(dirname);

		if (!source.exists()) {
			JOptionPane.showMessageDialog(
				this,
				"Source directory",
				"The source directory does not exist.",
				JOptionPane.ERROR_MESSAGE,
				null);
			return false;
		}

		if (source.isFile()) {
			JOptionPane.showMessageDialog(
				this,
				"Source directory",
				"The source is a file. Please provide a directory!",
				JOptionPane.ERROR_MESSAGE);
			return false;
		}

		dir = source.listFiles(new FileFilter() {
			public boolean accept(File pathname) {
				if (pathname.isFile()) {
					if (!Pattern.matches("^\\..*", pathname.getName()) && Pattern
						.matches(
							"^.*(\\.JPG|\\.jpg|\\.jpeg)$",
							pathname.getName())) {
						return true;
					}
				} else if (pathname.isDirectory()) {
					return true;
				}
				return false;
			}

		});

		for (int i = 0; i < dir.length; i++) {

			if (dir[i].isFile()) {
				// Insert File in global list
				files.put(dir[i].getAbsolutePath(), dir[i]);

			} else if (dir[i].isDirectory()) {
				// If isDir then recurse into it
				findFiles(dir[i].toString());
			} else {
				message(
					"<HTML><i>I don't know what to do with: </i></html>"
						+ dir[i].getName());
			}
		}
			return true;
	}

	/* Traverse trough the filelist */

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	public void run() {
		// TODO Auto-generated method stub
		initComponents();
		this.pack();
		this.setVisible(true);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
	 */
	public void actionPerformed(ActionEvent e) {
		// TODO Auto-generated method stub

		if (e.getActionCommand() == "Exit") {
			this.exitButtonActionPerformed(e);
		} else if (e.getActionCommand() == "Sort!") {
			startSortButtonActionPerformed(e);
		} else if (e.getActionCommand() == "Source Dir") {
			sourceDirUpdate();
		} else if (e.getActionCommand() == "Target Dir") {
			targetBrowseButtonActionPerformed(e);

		} else if (e.getActionCommand() == "Properties") {
			this.setPropertiesMenuItemActionPerformed(e);
		} else if (e.getActionCommand() == "Help on Usage") {
			this.usageHelpMenuItemActionPerformed(e);
		} else if (e.getActionCommand() == "About") {
			this.aboutHelpMenuItemActionPerformed(e);
		} else if (e.getActionCommand() == "Help on Database") {
			this.dbHelpMenuItemActionPerformed(e);
		} else if (e.getActionCommand() == "Ok") {
			jd.setVisible(false);
			jd = null;
		}

	}

	private void sourceDirUpdate() {

		fileLabel.setText("File:");
		fileProgress.setValue(0);
		files = new HashMap();
		sourceTextField.setText(browseSource(this));
		findFiles(sourceTextField.getText());
		outputTextArea.setText(null);
		allProgress.setValue(0);
		allProgress.setMaximum(files.size());
		allLabel.setText("Number of Files: " + files.size());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see javax.swing.event.ChangeListener#stateChanged(javax.swing.event.ChangeEvent)
	 */
	public void stateChanged(ChangeEvent e) {
		// TODO Auto-generated method stub
		repaint();
	}

	public void message(String message) {
		outputTextArea.append(message + "\n");
	}

	public void setfileLabel(String Label) {
		fileLabel.setText(Label);
	}
	public void setfileProgress(int value) {
		fileProgress.setValue(value);
	}
	public void setallProgress(int value) {
		allProgress.setValue(value);
	}
	public void setallProgressMAX(int value) {
		allProgress.setMaximum(value);
	}
	private void loadProperties(String configfile) {
		
		System.err.println("Reloading Properties: " + configfile);
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
