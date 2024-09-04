package reusable;

import java.io.File;
import java.time.LocalDateTime;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.io.IOException;

import testsettings.TestRunSettings;

public class RenameDownloadedFile {
	private RenameDownloadedFile(){
		
	}

	private static final Logger logger =LoggerFactory.getLogger(RenameDownloadedFile.class.getName());

	public static String getCurrentDate()
    {
        try
        {
        	LocalDateTime today = LocalDateTime.now();

            String date = today.toLocalDate().toString();
            date = date.replace(":", "_");
            date = date.replace(" ", "_");
            date = date.replace(".", "_");
            date = date.replace("-", "_");
            return date;
        }
        catch (Exception e)
        {
        	 logger.info("Error while current date:{} ", e.getMessage());
        }
		return null;
    }


    public static String getCurrentTime()
    {
        try
        {
        	LocalDateTime today = LocalDateTime.now();

            String result = today.toLocalTime().toString();

            result = result.replace(":", "_");
            result = result.replace(" ", "_");
            result = result.replace(".", "_");
            return result;

        }
        catch (Exception e)
        {
        	 logger.info("Error while current time:{} ", e.getMessage());
        }
		return null;

    }

    public static String createDirectory(String directoryPath, String directoryName)
    {
        try
        {
        	String dele = "/";
        	String directoryFulPath = directoryPath + dele + directoryName;
        	File dir = new File(directoryFulPath);
            if (!dir.exists()) dir.mkdirs();
            
            return directoryFulPath;

        }
        catch (Exception e)
        {
        	 logger.info("Error creating property file:{} ", e.getMessage());

        }
		return directoryName;
    }
    
    public static boolean renameAndPlaceFile(String downloadedFile, String renameTo, String folder) {
        try {
            File directory;
            String home = System.getProperty("user.home");
            String filePath = TestRunSettings.artifactsPath+"//PDF//TargetPDFfile//"+folder;
            directory = new File(filePath);
            if (directory.exists()) {
                for (File file : directory.listFiles()) {
                    Path path = Paths.get(file.getAbsolutePath());
                    extracted(path);
                }
            }
            if (!directory.exists()) {
                directory.mkdirs();
            }
            Thread.sleep(10);
            String renamedFileName = renameTo + ".PDF";
            String dele = "/";
            filePath = filePath + dele + renamedFileName;

            File originalFile = new File(home + "/Downloads/" + downloadedFile);
            File newFile = new File(filePath);
            Thread.sleep(0);
            boolean flag = originalFile.renameTo(newFile);
            if (flag) {
                logger.info("File Successfully Renamed and saved to destination folder");
            } else {
                logger.info("Operation Failed");
            }

            return originalFile.renameTo(newFile);
        } catch (InterruptedException e) {
            // Re-interrupt the current thread
            Thread.currentThread().interrupt();
            logger.info("Error :{} ", e.getMessage());
        } catch (Exception e) {
            logger.info("Error :{} ", e.getMessage());
        }
        return false;
    }


	private static void extracted(Path path) {
		try {
		    Files.delete(path);
		} catch (IOException e) {
		    logger.info("Error in rename and place file:{} ", e.getMessage());
		}
	}

    
    public static boolean renameAndPlaceFile(String downloadedFile, String renameTo)
    {
    	try {
    		String home = System.getProperty("user.home");
			String filePath = TestRunSettings.artifactsPath+"//PDF//TargetPDFfile";
			String renamedFileName=renameTo+".PDF";
			String dele = "/";
			filePath = filePath + dele +renamedFileName;
			File originalFile = new File(home + "/Downloads/" + downloadedFile);			
	    	File newFile = new File(filePath);
	        boolean flag = originalFile.renameTo(newFile);	 
	        if (flag) {
	            logger.info("File Successfully Renamed and saved to destination folder");
	        }

	        else {
	            logger.info("Operation Failed");
	        }
	        
	    
		return originalFile.renameTo(newFile);
    	}
    	catch(Exception e) 
    	{
    		logger.info("Error while rename and place file:{} ", e.getMessage());
    	}
		return false;
    }
}
