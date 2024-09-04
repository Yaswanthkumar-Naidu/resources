package reusable;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CustomDate {
	private static final Logger logger =LoggerFactory.getLogger(CustomDate.class.getName());
	
	public static final String DATEFORMAT="MM/dd/yyyy";
	   
	
	public String customDate(String trDate,int days,int months,int years) 
    {
          try 
          {        
        	    LocalDate tradeDate= LocalDate.parse(trDate, DateTimeFormatter.ofPattern(DATEFORMAT));
        	  
                  tradeDate= tradeDate.plusMonths(months);
                 
                  
                 tradeDate=tradeDate.plusDays(days);
                 tradeDate=tradeDate.plusYears(years);
                 
                  String customDate=tradeDate.format(DateTimeFormatter.ofPattern(DATEFORMAT));
          logger.info("customDate= {}",customDate);
                       
                       return customDate;
          }
          
          catch(Exception e) 
          {
        	  logger.info("Error creating property file:{} ", e.getMessage());
          }
		return trDate;
          
          

    }
}
