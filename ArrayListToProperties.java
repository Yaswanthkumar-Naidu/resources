package reusable;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


import java.util.Properties;


public class ArrayListToProperties {
	 private static final Logger logger =LoggerFactory.getLogger(ArrayListToProperties.class.getName());
   
	
	public void createPrpertyfile(String propertyfilepath, String propertyFileName,Map<String,String> casedata) throws IOException 
	{
		File directory = new File(propertyfilepath);
		
		if (!directory.exists()) {
            if (directory.mkdirs()) {
            	
                logger.info("Directory created successfully.");
            } else {
                logger.info("Failed to create directory.");
            }
        }
		String dele = "//";
		String path=propertyfilepath+dele+propertyFileName+".properties";
		 if (fileExists(path)) 
		 	{
			 
			 clearAndWriteProperties(path,casedata);
	        } else {
	            try {
	            	createandwritePropertyFile(path,casedata);
	                logger.info("Property file created successfully.");
	            } catch (IOException e) {
	                logger.info("Error creating property file:{} ", e.getMessage());
	            }
	        }
	}
	
	public static boolean fileExists(String filePath) {
	    File file = new File(filePath);
	    boolean fileExistsAndWritable = false;
	    if (file.exists()) {
	        boolean isWritable = file.setWritable(true);
	        if (isWritable) {
	            fileExistsAndWritable = true;
	        } else {
	            logger.info("Warning: Unable to set file as writable: ");
	        }
	    }
	    return fileExistsAndWritable;
	}

	
	public static void createandwritePropertyFile(String filePath, Map<String, String> casedata) throws IOException {
	    Path path = Paths.get(filePath);
	    Files.createFile(path);

	    Properties properties = new Properties();

	    for (Map.Entry<String, String> entry : casedata.entrySet()) {
	        properties.setProperty(entry.getKey(), entry.getValue());
	    }

	    try (FileWriter writer = new FileWriter(filePath)) {
	        properties.store(writer, "NewData");
	    }
	}

	
	private static void clearAndWriteProperties(String filePath, Map<String, String> casedata) throws IOException {
	    Properties properties = new Properties();

	    for (Map.Entry<String, String> entry : casedata.entrySet()) {
	        properties.setProperty(entry.getKey(), entry.getValue());
	    }

	    try (FileWriter writer = new FileWriter(filePath, false)) {
	        properties.store(writer, "UpdatedData");
	    }
	}

}
