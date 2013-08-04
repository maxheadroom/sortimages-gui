package org.zurell.java.SortImagesGUI;
import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
/* import com.drew.metadata.Directory; */
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;


public class ImageTester {

	public static void main(String args) {
		// TODO Auto-generated method stub
		
		File jpegFile = new File(args);
		
		System.out.println(checkfile(jpegFile));
		

	}
	
	public static String checkfile(File file) {
		
		System.out.println("Filename: " + file.getName());
		Metadata metadata = null;
		String message = "";
		try {
			metadata = ImageMetadataReader.readMetadata(file);
		} catch (ImageProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Directory directory : metadata.getDirectories()) {
		    for (Tag tag : directory.getTags()) {
		        message = message + "\n["+tag.getDirectoryName() + "] " + tag.getTagName() + " = " + tag.getDescription();
		    }
		}
		
		return message;
	}

}
