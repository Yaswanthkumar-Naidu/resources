package reusable;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;


public class TimeTravelDate {
	 private static final Logger logger =LoggerFactory.getLogger(TimeTravelDate.class.getName());
	private  WebDriver driver;
	public static final String DATEFORMAT= "MM/dd/yyyy";
	public TimeTravelDate(WebDriver drivernew)
    {
        driver = drivernew;
    }
	
	
	public String getTimeTravelDate() {
	    try {
	        Thread.sleep(5000);
	        LocalDate date = LocalDate.now();
	        String datenew = date.format(DateTimeFormatter.ofPattern(DATEFORMAT));
	        LocalDate tTDate = LocalDate.parse(datenew, DateTimeFormatter.ofPattern(DATEFORMAT));
	        String applicationDate = "";
	        if (!driver.findElements(By.xpath("//*[@class='TimeTravelDateDisplay subHeadingStyle']")).isEmpty()) {
	            applicationDate = driver.findElement(By.xpath("//*[@class='TimeTravelDateDisplay subHeadingStyle']")).getText().replace("Time Travel Date ", "");
	        } else {
	            applicationDate = tTDate.format(DateTimeFormatter.ofPattern(DATEFORMAT));
	        }

	        return applicationDate;
	    } catch (InterruptedException e) {
	        // Re-interrupt the current thread
	        Thread.currentThread().interrupt();
	        logger.info("Error creating property file:{} ", e.getMessage());
	    } catch (Exception e) {
	        logger.info("Error creating property file:{} ", e.getMessage());
	    }
	    return null;
	}

	
	
}
