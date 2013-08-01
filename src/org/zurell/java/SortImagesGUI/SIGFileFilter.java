/*
 * Created on 28.12.2003
 *
 * To change the template for this generated file go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */


/* $Id: SIGFileFilter.java,v 1.2 2003/12/31 15:44:05 zero_data Exp $ */
package org.zurell.java.SortImagesGUI;

import java.io.File;
import java.util.ArrayList;
import java.util.Iterator;

import javax.swing.filechooser.FileFilter;

/**
 * @author m0554
 *
 * To change the template for this generated type comment go to
 * Window&gt;Preferences&gt;Java&gt;Code Generation&gt;Code and Comments
 */
public class SIGFileFilter extends FileFilter {
	private String Version = SortImagesGUI.Version;
	protected ArrayList exts = new ArrayList(  );

	public void addType(String s) {
		exts.add(s);
	}
	
	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#accept(java.io.File)
	 */
	public boolean accept(File f) {
		// TODO Auto-generated method stub
		if (f.isDirectory()) {
			return true;
		}else if (f.isFile(  )) {
			Iterator it = exts.iterator(  );
			while (it.hasNext(  )) {
				if (f.getName().endsWith((String)it.next(  )))
					return true;
			}
		}
		return false;
	}

	/* (non-Javadoc)
	 * @see javax.swing.filechooser.FileFilter#getDescription()
	 */
	public String getDescription() {
		// TODO Auto-generated method stub
		return "Directories only";
	}

}
