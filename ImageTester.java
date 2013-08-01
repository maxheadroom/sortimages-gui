import java.io.File;
import java.io.IOException;

import com.drew.imaging.ImageMetadataReader;
import com.drew.imaging.ImageProcessingException;
import com.drew.metadata.Directory;
/* import com.drew.metadata.Directory; */
import com.drew.metadata.Metadata;
import com.drew.metadata.Tag;


public class ImageTester {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		
		File jpegFile = new File(args[0]);
		Metadata metadata = null;
		try {
			metadata = ImageMetadataReader.readMetadata(jpegFile);
		} catch (ImageProcessingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		for (Directory directory : metadata.getDirectories()) {
		    for (Tag tag : directory.getTags()) {
		        System.out.println("["+tag.getDirectoryName() + "] " + tag.getTagName() + " = " + tag.getDescription());
		    }
		}

	}

}
