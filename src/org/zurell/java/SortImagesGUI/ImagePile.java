/*
 * ImagePile.java
 *
 * Created on 1. Dezember 2003, 16:09
 */
/* CVS-Header */
/* $Id: ImagePile.java,v 1.1 2003/12/31 15:29:16 zero_data Exp $ */


package org.zurell.java.SortImagesGUI;

import java.io.File;
import java.util.Date;
import java.util.Properties;

/**
 *
 * @author  m0554
 */

/** This class holds all the image information we need. */
public class ImagePile {
    
    /** This is the Name of the file. */    
    private File fileName;
        
    private Date ImageDate;
    
    /** This holds the new filename. This is generated from the calculated new filename
     * and the target path for the images. The new filename is composed of the data
     * information read from the EXIF-Header. The new image name has the format
     * YYYY-MM-DD_HH:MM:SS.jpg
     */    
    private File NewFilename;
    
    private Properties imageProperties = new Properties();
    
    
    
    /** Creates a new instance of ImagePile
     * @param filename The canonical filename of the file this instance holds information for.
     */
    public ImagePile(File filename) {
    	
        this.fileName = filename;
    }
    
	/**
	 * @return Returns the fileName.
	 */
	public File getFileName() {
		return fileName;
	}

	/**
	 * @param fileName The fileName to set.
	 */
	public void setFileName(File fileName) {
		this.fileName = fileName;
	}

	/**
	 * @return Returns the imageDate.
	 */
	public Date getImageDate() {
		return ImageDate;
	}

	/**
	 * @param imageDate The imageDate to set.
	 */
	public void setImageDate(Date imageDate) {
		ImageDate = imageDate;
	}

	/**
	 * @return Returns the imageProperties.
	 */
	public Properties getImageProperties() {
		return imageProperties;
	}

	/**
	 * @param imageProperties The imageProperties to set.
	 */
	public void setImageProperties(Properties imageProperties) {
		this.imageProperties = imageProperties;
	}

	/**
	 * @return Returns the newFilename.
	 */
	public File getNewFilename() {
		return NewFilename;
	}

	/**
	 * @param newFilename The newFilename to set.
	 */
	public void setNewFilename(File newFilename) {
		NewFilename = newFilename;
	}

	/**
	 * @return Returns the apertureTime.
	 */
	public String getApertureTime() {
		return imageProperties.getProperty("ApertureTime");
	}

	/**
	 * @param apertureTime The apertureTime to set.
	 */
	public void setApertureTime(String apertureTime) {
		
		imageProperties.setProperty("ApertureTime", apertureTime);
	}

	/**
	 * @return Returns the cameraMaker.
	 */
	public String getCameraMaker() {
		return imageProperties.getProperty("CameraMaker");
	}

	/**
	 * @param cameraMaker The cameraMaker to set.
	 */
	public void setCameraMaker(String cameraMaker) {
		imageProperties.setProperty("CameraMaker", cameraMaker);
	}

	/**
	 * @return Returns the cameraModel.
	 */
	public String getCameraModel() {
		return imageProperties.getProperty("CameraModel");
	}

	/**
	 * @param cameraModel The cameraModel to set.
	 */
	public void setCameraModel(String cameraModel) {
		imageProperties.setProperty("CameraModel",cameraModel);
	}

	/**
	 * @return Returns the exposureTime.
	 */
	public String getExposureTime() {
		return imageProperties.getProperty("ExposureTime");
	}

	/**
	 * @param exposureTime The exposureTime to set.
	 */
	public void setExposureTime(String exposureTime) {
		imageProperties.setProperty("ExposureTime",exposureTime);
	}

	/**
	 * @return Returns the flashUsed.
	 */
	public String getFlashUsed() {
		return imageProperties.getProperty("FlashUsed");
	}

	/**
	 * @param flashUsed The flashUsed to set.
	 */
	public void setFlashUsed(String flashUsed) {
		imageProperties.setProperty("FlashUsed",flashUsed);
	}

	/**
	 * @return Returns the focalLength.
	 */
	public String getFocalLength() {
		return imageProperties.getProperty("FocalLength");
	}

	/**
	 * @param focalLength The focalLength to set.
	 */
	public void setFocalLength(String focalLength) {
		imageProperties.setProperty("FocalLength", focalLength);
	}

	/**
	 * @return Returns the height.
	 */
	public String getHeight() {
		return imageProperties.getProperty("height");
	}

	/**
	 * @param height The height to set.
	 */
	public void setHeight(String height) {
		imageProperties.setProperty("height", height);
	}

	/**
	 * @return Returns the mD5SUM.
	 */
	public String getMD5SUM() {
		return imageProperties.getProperty("MD5SUM");
	}

	/**
	 * @param md5sum The mD5SUM to set.
	 */
	public void setMD5SUM(String md5sum) {
		imageProperties.setProperty("MD5SUM", md5sum);
	}

	/**
	 * @return Returns the width.
	 */
	public String getWidth() {
		return imageProperties.getProperty("width");
	}

	/**
	 * @param width The width to set.
	 */
	public void setWidth(String width) {
		imageProperties.setProperty("width", width);
	}

}
