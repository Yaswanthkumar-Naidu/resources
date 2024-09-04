package report_utilities.common;

import report_utilities.Constants.ReportContants;

import org.apache.commons.io.FileUtils;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.interactions.Actions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pom.sd.InitialScreening;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Date;

public class ScreenshotCommon
{
	
	private static final Logger logger =LoggerFactory.getLogger(ScreenshotCommon.class.getName());

	public void getEntireScreenshot(String screenshotPath, WebDriver driver) throws IOException
	{

		try {
			JavascriptExecutor jsExec = (JavascriptExecutor)driver;

			//Returns a Long, Representing the Height of the window’s content area.
			Long windowHeight = (Long) jsExec.executeScript("return window.innerHeight;");

			//Returns a Long, Representing the Height of the complete WebPage a.k.a. HTML document.
			Long webpageHeight = (Long) jsExec.executeScript("return document.body.scrollHeight;"); 

			//Marker to keep track of the current position of the scroll point
			//Using java's boxing feature to create a Long object from native long value.

			Long currentWindowScroll = 0L;

			do{
				jsExec.executeScript("window.scrollTo(0, " + currentWindowScroll + ");");

				Actions act = new Actions(driver);
				act.pause(5000).perform();

				Date timestamp = new Date();

				File tempScreenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);

				// Read the screenshot image
		        BufferedImage image = ImageIO.read(tempScreenshot);

		        // Create a graphics context on the image
		        Graphics2D graphics = image.createGraphics();

		        // Set the timestamp on the image
		        graphics.drawString(timestamp.toString(), 10, 20); // Adjust coordinates as needed

				//Unique File Name For Each Screenshot
				File destination = new File(screenshotPath);
				ImageIO.write(image, "png", tempScreenshot);

				Files.copy( tempScreenshot.toPath(), destination.toPath());

				currentWindowScroll = currentWindowScroll + windowHeight;

			}while(currentWindowScroll <= webpageHeight);

		} catch (IOException e) {
			e.printStackTrace();
			throw e;
		}
	}


	/// <summary>
	/// This method captures the screenshot of the Current Page
	/// </summary>
	/// Author: Jigesh Shah
	/// <param name="driver">WebDriver Instance</param>
	/// <param name="path">Location to capture Screenshot</param>
	/// <returns></returns>
	public void getScreenShot(String screenshotPath, WebDriver driver) throws Exception
	{
		try
		{
			TakesScreenshot scrst = ((TakesScreenshot)driver);

			Date timestamp = new Date();
			// Call getScreenshotAs method to create an image file
			File srcFile = scrst.getScreenshotAs(OutputType.FILE);

			// Read the screenshot image
	        BufferedImage image = ImageIO.read(srcFile);

	        // Create a graphics context on the image
	        Graphics2D graphics = image.createGraphics();

	        // Set the timestamp on the image
	        Font font = new Font("Arial", Font.BOLD, 24); // Adjust the font name and size as needed
	        graphics.setFont(font);
	        graphics.setColor(Color.RED);
	        graphics.drawString(timestamp.toString(), 10, 20); // Adjust coordinates as needed

			// Move image file to the new destination
			File destFile = new File(screenshotPath);

			ImageIO.write(image, "png", srcFile);
			// Copy file at the destination
			FileUtils.copyFile(srcFile, destFile);
		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;
		}
	}

	public static String getCurrentDate() throws Exception
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
			e.printStackTrace();
			throw e;
		}
	}


	public static String getCurrentTime() throws Exception
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
			e.printStackTrace();
			throw e;
		}

	}

	public String createDirectory(String directoryPath, String directoryName) throws Exception
	{
		try
		{
			String directoryFulPath = directoryPath + File.separator + directoryName;
			File dir = new File(directoryFulPath);
			if (!dir.exists()) dir.mkdirs();

			return directoryFulPath;

		}
		catch (Exception e)
		{
			e.printStackTrace();
			throw e;

		}
	}

	public String captureScreenShot(WebDriver driver, String testCaseName) throws Exception
	{

		String screenshotPath = createDirectory(ReportContants.ResultsFolder, "Screenshot");
		String screenShotFileName=testCaseName + "_" + getCurrentTime() + ".jpeg";
		screenShotFileName=screenShotFileName.replace(" ", "_");
		screenshotPath = screenshotPath + File.separator +screenShotFileName;
		if (ReportContants.isFullPageScreenShot)
		{
			getEntireScreenshot(screenshotPath, driver);
		}
		else
		{
			getScreenShot(screenshotPath, driver);
		}

		return screenshotPath;
	}

		public String createTextFileArrayList(ArrayList<String> arraylist, String apiname) throws Exception
	{
	
		String screenshotPath = createDirectory(ReportContants.ResultsFolder, "Screenshot");
		String screenShotFileName=apiname + "_" + getCurrentTime() + ".txt";
		screenShotFileName=screenShotFileName.replace(" ", "_");
		screenshotPath = screenshotPath + File.separator +screenShotFileName;
		
		
			
		
		File file = new File(screenshotPath);
		logger.info("FilePath : {}",screenshotPath);
		FileWriter fileWriter = new FileWriter(file);
		try(BufferedWriter buffer = new BufferedWriter(fileWriter)){
		for(String s : arraylist) {
			buffer.write(s);
			buffer.newLine();
		}

		}
		catch(Exception e) 
		{
			e.printStackTrace();
			throw e;
		}
		return screenshotPath;
		
		}
}
