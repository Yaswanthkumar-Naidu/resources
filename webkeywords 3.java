package common_utilities.common.action_keywords;

import org.openqa.selenium.*;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import report_utilities.common.ReportCommon;
import report_utilities.common.ScreenshotCommon;
import report_utilities.model.TestCaseParam;
import report_utilities.extent_model.ExtentUtilities;
import report_utilities.extent_model.PageDetails;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.function.Function;



public class webkeywords
{
	private static final String LOG_SWITCHED_TO = "Switched to: {}";

	public static enum SelectType
	{
		SELECT_BY_INDEX,
		SELECT_BY_TEXT,
		SELECT_BY_VALUE,
	}

	private static final webkeywords _instance = new webkeywords();
	private static final Logger logger =LoggerFactory.getLogger(webkeywords.class.getName());
	private static final String STATUSPASS="PASS";
	private static final String STATUSFAIL="FAIL";
	private static final String STATUSDONE="DONE";
	ExtentUtilities extentUtilities = new ExtentUtilities();
	ScreenshotCommon scm = new ScreenshotCommon();

	ReportCommon testStepDetails = new ReportCommon();
	ReportCommon exceptionDetails = new ReportCommon();
	
	public static webkeywords instance()
	{

		return _instance;

	}
	
	public void navigate(WebDriver driver, String url, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String actionNv = "Navigate -> "+url;
		String actionDescriptionNv = "";
		LocalDateTime startTime=  LocalDateTime.now();

		try
		{

			logger.info("Url = {}",url);
			if (!(url.startsWith("http://") || url.startsWith("https://")))
			
			driver.navigate().to(url);
			LocalDateTime EndTime =  LocalDateTime.now();
			testStepDetails.logTestStepDetails(driver, testCaseParam, actionNv, actionDescriptionNv,pageDetails, startTime, STATUSDONE);
			logger.info("Successfully Navigated to {} ",url);

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}",actionNv,actionDescriptionNv);
			testStepDetails.logExceptionDetails(driver, testCaseParam, actionNv, actionDescriptionNv, startTime,e);
			testStepDetails.logTestStepDetails(driver, testCaseParam, actionNv, actionDescriptionNv,pageDetails, startTime, STATUSFAIL);

			throw e;
		}


	}

	public void openUrl(WebDriver drivernew, String url, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -->"+url ;
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{

			logger.info("Url = {}" , url);
			if (!(url.startsWith("http://") || url.startsWith("https://")))
				throw new Exception("URL is invalid format and cannot open page");
			drivernew.navigate().to(url);
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}
	
	public void select(WebDriver drivernew, WebElement element, SelectType type, String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{

		String action = "Select dropdown Value:"+options;
		String actionDescription = "Select dropdown Value:-"+options;
		LocalDateTime startTime=  LocalDateTime.now();

		try
		{

			logger.info("Option = {}" ,options);

			switch (type)
			{
			case SELECT_BY_INDEX:
				try
				{


					if (checkOptions(options) ) 
					{
						break;
					}					

					else
					{

						webkeywords.instance().fluentWait(drivernew,element);
						scrollIntoViewElement(drivernew, element);
						if(element.isEnabled()) 
						{
							action = "Selected Value:-"+options;
							actionDescription = "Selected Value:-"+options;
							Select select = new Select(element);
							select.selectByIndex(Integer.parseInt(options));
							testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
						}

					}

				}
				catch (Exception e)
				{
					logger.error("Failed ==> {} {}" , action , actionDescription);
					testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

					throw e;
				}

				break;
			case SELECT_BY_TEXT:

				if (checkOptions(options) ) 
				{
					break;
				}
				else
				{
					webkeywords.instance().fluentWait(drivernew, element);
					scrollIntoViewElement(drivernew, element);
					if(element.isEnabled() && element.isDisplayed()) 
					{
						action = "Selected Value:-"+options;
						actionDescription = "Selected Value:-"+options;
						Select select1 = new Select(element);
						select1.selectByVisibleText(options);
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
						
					}


				}
				break;
			case SELECT_BY_VALUE:

				webkeywords.instance().fluentWait(drivernew, element);
				scrollIntoViewElement(drivernew, element);
				Select select2 = new Select(element);
				select2.selectByValue(options);
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

				verifyValueSelected(drivernew, element,options,testCaseParam,pageDetails);
				Thread.sleep(100);			
				break;
			default:
				throw new Exception("Get error in using Selected");
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==>{} {} ", action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			throw e;
		}
	}

	private boolean checkOptions(String options) {
		return options.equals("N//A") || options.equals("N/A") || options.equals("n//a") || options.equals("n/a");
	}

	public void trymultipleclick(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		webkeywords.instance().fluentWait(drivernew, element);
		String action="";
		action = getaction(drivernew, element, testCaseParam, pageDetails);

		String actionDescription = element.toString();
		LocalDateTime startTime=  LocalDateTime.now();


		try
		{	
			Actions actionnew = new Actions(drivernew);
			actionnew.moveToElement(element).build().perform(); 
			element.click();
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);


		}
		catch (Exception e)
		{
			try
			{
				
				Actions actionnew = new Actions(drivernew);
				actionnew.moveToElement(element).build().perform(); 
				element.click();
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);


			}

			catch(Exception ex) 
			{
				try
				{
					
					Actions actionnew = new Actions(drivernew);
					actionnew.moveToElement(element).build().perform(); 
					element.click();
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

					
				}

				catch(Exception exx) 
				{
					try
					{
						
						Actions actionnew = new Actions(drivernew);
						actionnew.moveToElement(element).build().perform(); 
						element.click();
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

						

					}

					catch(Exception exxx) 
					{
						try
						{
								Actions actionnew = new Actions(drivernew);
							actionnew.moveToElement(element).build().perform(); 
							element.click();
							testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);



						}

						catch(Exception exxxx) 
						{
							handleActionExc(drivernew, element, testCaseParam, pageDetails, action, actionDescription,
									startTime, e);

						}

					}

				}

			}
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			throw e;
		}
	}

	private void handleActionExc(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,
			PageDetails pageDetails, String action, String actionDescription, LocalDateTime startTime, Exception e)
			throws Exception {
		try
		{
			Actions actionnew = new Actions(drivernew);
			actionnew.moveToElement(element).build().perform(); 
			element.click();
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);



		}

		catch(Exception exp) 
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,exp);
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			throw e;

		}
	}

	private String getaction(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,
			PageDetails pageDetails) throws Exception {
		String action;
		try 
		{

			if(getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails)!=null)
			{ 
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
			} 
			else

			{
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
			}

		}
		catch(Exception e) 
		{
			action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
		}
		return action;
	}

	public void click(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action="";

		if(!(testData.equalsIgnoreCase("n/a"))||!(testData.equalsIgnoreCase("n\\a"))||!(testData.equalsIgnoreCase("n\\\\a")))
		{
		webkeywords.instance().fluentWait(drivernew, element);
		webkeywords.instance().waitElementforelementclickable(drivernew, element, 1000);
		scrollIntoViewElement(drivernew, element);
		String actionDescription = element.toString();
		LocalDateTime startTime=  LocalDateTime.now();
		getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
		
		try 
		{

			if(getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails)!=null)
			{ 
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
				actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
			} 
			else

			{
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
				actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);

			}
	
		}
		catch(Exception e) 
		{
			action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
			actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);

		} 

		

			try
			{		
				
				Actions actionnew = new Actions(drivernew);
				actionnew.moveToElement(element).build().perform();

				element.click();


				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);
				Thread.sleep(900);


			}
			catch (Exception e)
			{
				try 
				{
					webkeywords.instance().waitElementforelementclickable(drivernew, element, 1000);
					JavascriptExecutor executor = (JavascriptExecutor)drivernew;
					executor.executeScript("arguments[0].scrollIntoView(true);", element);
					Thread.sleep(300);
					executor.executeScript("arguments[0].click();", element);

					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

				}
				catch(Exception f) 
				{
					logger.error("Failed ==> {} {}" , action , actionDescription);
					testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

					throw e;
				}

			}
			
		}
	}


	public void clickwithaction(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails,String TestData) throws Exception
	{

		String action="";
		String actionDescription="";
		LocalDateTime startTime=  LocalDateTime.now();
		if(!(TestData.equalsIgnoreCase("n/a"))||!(TestData.equalsIgnoreCase("n\\a"))||!(TestData.equalsIgnoreCase("n\\\\a")))
		{
		try {

			try 
			{
				webkeywords.instance().fluentWait(drivernew, element);
				webkeywords.instance().waitElementforelementclickable(drivernew, element, 1000);
				scrollIntoViewElement(drivernew, element);

				if(getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails)!=null) 
				{ 
					action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
					actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
				} 
				else

				{
					action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
					actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);

				}

			}
			catch(Exception e) 
			{
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
				actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);

			} 



			try
			{		
				Actions actionnew = new Actions(drivernew);
				actionnew.moveToElement(element).build().perform();

				element.click();


				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
				Thread.sleep(900);

			}
			catch (Exception e)
			{

				logger.error("Failed ==>{} {} " ,action , actionDescription);
				testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				throw e;


			}

		}
		catch(Exception e)
		{
			throw e;
		}
		}
	}

	public void waitElementtobeVisible(WebDriver drivernew, String xpath, int timeOut)  {
		int count = 0;
		int maxTries = 5;
		while (count < maxTries) {
			try {
				WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
				boolean elementpresent = wait.until(ExpectedConditions.visibilityOfElementLocated(By.xpath(xpath)))
						.isDisplayed();
				if ((elementpresent)) {
					break;
				}
			} catch (Exception e) {
				count++;
				if (count == maxTries) {
					break;
				}
			}
		}
	} 




	public void clickWithoutwait(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails,String testData) throws Exception
	{
		String action="";
		Thread.sleep(100);
		String actionDescription = element.toString();
		LocalDateTime startTime=  LocalDateTime.now();
		if(!(testData.equalsIgnoreCase("n/a"))||!(testData.equalsIgnoreCase("n\\a"))||!(testData.equalsIgnoreCase("n\\\\a")))
		{
		try 
		{

			if(getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails)!=null)
			{ 
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
				actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
			} 
			else

			{
				action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
				actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);

			}

		}
		catch(Exception e) 
		{
			action = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);
			actionDescription = "Click --> "+getLocatorFromWebElement(element,drivernew,testCaseParam,pageDetails);

		} 




		try
		{		


			JavascriptExecutor executor = (JavascriptExecutor)drivernew;
			executor.executeScript("arguments[0].click();", element);

			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
			Thread.sleep(900);

		}
		catch (Exception e)
		{
			try 
			{
				JavascriptExecutor executor = (JavascriptExecutor)drivernew;
				executor.executeScript("arguments[0].click();", element);

				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

			}
			catch(Exception f) 
			{
				logger.error("Failed ==> {} {}" , action , actionDescription);
				testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				throw e;
			}

		}
		}
	}
	public void waitElementToBeVisibleNew(WebDriver drivernew, WebElement element, int timeOut) throws Exception {
		int count = 0;
		int maxTries = 5;
		while (count < maxTries) {
			try {
				By locatorvalue = getLocatorvalue(element, drivernew);
				WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
				wait.until(ExpectedConditions.presenceOfElementLocated(locatorvalue));
				break;
			} catch (Exception e) {
				Thread.sleep(10000);
				count++;
				if (++count == maxTries) {
					throw e;
				}
			}
		}
	}

	public void waitForElementToBeVisible(WebDriver drivernew, String elementId) throws Exception {
		int count = 0;
		int maxTries = 5;
		int timeOut=3000;
		while (count < maxTries) {
			try {
				WebElement element=drivernew.findElement(By.xpath(elementId));
				By locatorvalue = getLocatorvalue(element, drivernew);
				WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
				wait.until(ExpectedConditions.presenceOfElementLocated(locatorvalue));
				break;
			} catch (Exception e) {
				Thread.sleep(10000);
				count++;
				if (++count == maxTries) {
					throw e;
				}
			}
		}
	}


	public String getLocatorFromWebElement(WebElement element,WebDriver drivernew,TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{

		String locatoroutput= element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
		String[] splitlocator=locatoroutput.split(": ");
		splitlocator[0] =splitlocator[0].replaceAll("\\s", "");	    
		splitlocator[1] =splitlocator[1].replaceAll("\\s", "");

		getElementText(drivernew, splitlocator[0],splitlocator[1],  testCaseParam,pageDetails);

		return getElementText(drivernew, splitlocator[0],splitlocator[1],  testCaseParam,pageDetails);
	}

	public void hovermousetoelement(WebDriver drivernew,WebElement element,TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		try 
		{
			Thread.sleep(1000);
			Actions action = new Actions(drivernew);

			action.moveToElement(element).perform();
		}
		catch(Exception e) 
		{
			throw e;
		}
	}
	public void switchtoFrame(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{

			drivernew.switchTo().frame(element);
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	public void switchToWindowByTitle(WebDriver drivernew, String title, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{
			Thread.sleep(2000);
			Set<String> windows=drivernew.getWindowHandles();


			for (String handle: windows)
			{

				String myTitle = drivernew.switchTo().window(handle).getTitle();
				// now apply the condition - moving to the window with blank title
				if (myTitle.equals(title))
				{

					drivernew.switchTo().window(handle);
					logger.info("switched to Window--> {}",title);
					Thread.sleep(0);
					break;
				}
				else
				{
					switchToWindowHandle(drivernew, handle);
				}





			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	private void switchToWindowHandle(WebDriver drivernew, String handle) {
		try
		{
			drivernew.switchTo().window(handle);
			logger.info("switched to main Window");
		}
		catch (Exception e)
		{
			logger.info("Unable to switch to Main Window");
		}
	}


	public void refresh(WebDriver drivernew, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Refresh the page ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{
			drivernew.navigate().refresh();
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			throw e;
		}
	}

	public boolean findElementBool(WebDriver drivernew, By by, TestCaseParam testCaseParam, int... timeOut)
	{
		if(timeOut==null)
		{
			timeOut[0]=60;
		}

		boolean found = false;
		try
		{
			waitElementVisible(drivernew, by, timeOut[0]);
			found = true;
		}
		catch (Exception e)
		{
			found = false;
		}
		logger.info("{} found = {}", by, found);
		return found;
	}

	public void zoombackToOriginal(WebDriver drivernew, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{


			JavascriptExecutor js = (JavascriptExecutor)drivernew;

			js.executeScript("document.body.style.zoom='100%'");

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	public void jsClick(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails, int... timeOut) throws Exception
	{
		if(timeOut==null)
		{
			timeOut[0]=60;
		}

		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{

			WebDriverWait wait = new WebDriverWait(drivernew, timeOut[0]);


			JavascriptExecutor js = (JavascriptExecutor)drivernew;
			js.executeScript("arguments[0].click();", element);

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}


	public void clickRadioButton(WebDriver driver, TestCaseParam testCaseParam, WebElement element) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{

			for (int i = 0; i < 10; i++)
			{
				element.click();

				if (element.isSelected())
				{
					break;
				}


			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action, actionDescription);
			testStepDetails.logExceptionDetails(driver, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	
	public void setText(WebDriver drivernew, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Entered Text -> "+ text;
		String actionDescription = "Entered Text -> "+ text;

		LocalDateTime startTime=  LocalDateTime.now();

		try
		{

			if(!textCheck(text))
			{
				webkeywords.instance().fluentWait(drivernew, element);
				WebDriverWait wait = new WebDriverWait(drivernew, 1000);
				wait.until(ExpectedConditions.elementToBeClickable(element));				
				element.clear();
				element.sendKeys(text);
				boolean isDataFilled = false;
				while (!isDataFilled) {
					
					
					String textBoxValue=element.getAttribute("value");
					String textBoxtest=element.getText();
					 isDataFilled = validateTextBoxValue(drivernew, element, text, isDataFilled, textBoxValue,
							textBoxtest);
		        }

				LocalDateTime endTime =  LocalDateTime.now();

				logger.info("Successfully Entered Text {} to {}" ,text , element);

				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

				verifyValueEntered(drivernew, element,text,testCaseParam,pageDetails);
				Thread.sleep(0);
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			throw e;
		}

	}

	private boolean validateTextBoxValue(WebDriver drivernew, WebElement element, String text, boolean isDataFilled,
			String textBoxValue, String textBoxtest) {
		if (checktextboxExpression(textBoxValue, textBoxtest)) 
		 {
			 isDataFilled = true;
		 }
		   
		    else if (checkTestboxValue(textBoxValue, textBoxtest)) 
		    {
		    	isDataFilled = true;
		    }
		    else if (textBoxValue.matches("^[a-z A-Z]+$")||textBoxtest.matches("^[a-z A-Z]+$")) 
		    {
		    	isDataFilled = true;
		    }
		    
		    else if (checktextboxtestExp(textBoxValue, textBoxtest)) 
		    {
		    	isDataFilled = true;
		    }
		    else if (textBoxValue.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=/_-]).+$")) {
		    	isDataFilled = true;				        }
		    
		    else if (textBoxValue.matches("^(?=.*\\d)(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[0-9])(?=.*[@#$%^&+=/-_]).+$")) {
		    	isDataFilled = true;				        }
		    
		    else if (checkTextBoxRegularExp(textBoxValue, textBoxtest)) {
		    	isDataFilled = true;
		    	}
		 
		    else if (textBoxValue.matches("^(?=.*[@#$!%^&+=-]).+$")||textBoxtest.matches("^(?=.*[@#$!%^&+=-]).+$")) {
		    	isDataFilled = true;
		    	}
		   

		else 
		{
			
		   logger.info("Textbox is empty. Retrying...");
		    element.clear();

			
			JavascriptExecutor jsExecutor = (JavascriptExecutor) drivernew;
		    jsExecutor.executeScript("arguments[0].value = arguments[1]", element, text);
		}
		return isDataFilled;
	}

	private boolean checktextboxtestExp(String textBoxValue, String textBoxtest) {
		
		return textBoxValue.matches("\\d")||textBoxtest.matches("\\d");
	}

	private boolean checktextboxExpression(String textBoxValue, String textBoxtest) {
		return textBoxValue.matches("^[a-zA-Z0-9]+$")||textBoxtest.matches("^[a-zA-Z0-9]+$");
	}
	
	public void setTextMultipleRetry(WebDriver driver, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Entered Text -> "+ text;
		String actionDescription = "Entered Text -> "+ text;

		LocalDateTime startTime=  LocalDateTime.now();

		try
		{

			if(!checkIfTrue(text))
			{

				webkeywords.instance().fluentWait(driver, element);
				WebDriverWait wait = new WebDriverWait(driver, 1000);
				wait.until(ExpectedConditions.elementToBeClickable(element));				
				element.clear();
				element.sendKeys(text);
				
				boolean isDataFilled = false;
				while (!isDataFilled) {
					
					
					String textBoxValue=element.getAttribute("value");
					String textBoxtest=element.getText();
					 if (checktextboxExpression(textBoxValue, textBoxtest)) 
					 {
						 isDataFilled = true;
					 }
				    
				        else if (checkTestboxValue(textBoxValue, textBoxtest)) 
				        {
				        	isDataFilled = true;
				        }
				     
				        else if (checktextboxtestExp(textBoxValue, textBoxtest)) 
				        {
				        	isDataFilled = true;
				        }
				        else if (textBoxValue.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=/_-]).+$")) {
				        	isDataFilled = true;				        }
				      
				        else if (textBoxValue.matches("^(?=.*\\d)(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*\\d)(?=.*[@#$%^&+=/-_]).+$")) {
				        	isDataFilled = true;				        }
				      
				        else if (checkTextBoxRegularExp(textBoxValue, textBoxtest)) {
				        	isDataFilled = true;
				        	}
				       

		            else 
		            {
		            	
		               logger.info("Textbox is empty. Retrying...");
		                element.clear();

						
						JavascriptExecutor jsExecutor = (JavascriptExecutor) driver;
			            jsExecutor.executeScript("arguments[0].value = arguments[1]", element, text);
		            }
		        }

				logger.info("Successfully Entered Text {} to {}" ,text ,element);
				testStepDetails.logTestStepDetails(driver, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
				verifyValueEntered(driver, element,text,testCaseParam,pageDetails);
				Thread.sleep(0);
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action ,actionDescription);
			testStepDetails.logExceptionDetails(driver, testCaseParam, action, actionDescription, startTime,e);
			testStepDetails.logTestStepDetails(driver, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			throw e;
		}

	}

	private boolean checkIfTrue(String text) {
		return text.equalsIgnoreCase("N/A")|| text.equalsIgnoreCase("N//A");
	}

	public void setTextwithoutverification(WebDriver drivernew, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Entered Text -> "+ text;
		String actionDescription = "Entered Text -> "+ text;

		LocalDateTime startTime=  LocalDateTime.now();

		try
		{

			if(!textCheck(text))
			{

				webkeywords.instance().fluentWait(drivernew, element);
				WebDriverWait wait = new WebDriverWait(drivernew, 1000);
				wait.until(ExpectedConditions.elementToBeClickable(element));				
				element.clear();
				element.sendKeys(text);
				
				boolean isDataFilled = false;
				while (!isDataFilled) {
					
					
					String textBoxValue=element.getAttribute("value");
					String textBoxtest=element.getText();
					 if (checktextboxExpression(textBoxValue, textBoxtest)) 
					 {
						 isDataFilled = true;
					 }
				        
				        else if (checkTestboxValue(textBoxValue, textBoxtest)) 
				        {
				        	isDataFilled = true;
				        }
				        
				        else if (checktextboxtestExp(textBoxValue, textBoxtest)) 
				        {
				        	isDataFilled = true;
				        }
				        else if (textBoxValue.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[a-zA-Z])(?=.*\\d)(?=.*[@#$%^&+=/_-]).+$")) {
				        	isDataFilled = true;				        }
				       
				        else if (textBoxValue.matches("^(?=.*\\d)(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*\\d)(?=.*[@#$%^&+=/-_]).+$")) {
				        	isDataFilled = true;				        }
				       
				        else if (checkTextBoxRegularExp(textBoxValue, textBoxtest)) {
				        	isDataFilled = true;
				        	}
				        
				       

		            else 
		            {
		            	
		                logger.info("Textbox is empty. Retrying...");
		                element.clear();
						
						
						JavascriptExecutor jsExecutor = (JavascriptExecutor) drivernew;
			            jsExecutor.executeScript("arguments[0].value = arguments[1]", element, text);
		            }
		        }



				LocalDateTime endTime =  LocalDateTime.now();

				logger.info("Successfully Entered Text {} to {}" ,text ,element);

				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

				Thread.sleep(0);
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			throw e;
		}

	}

	private boolean checkTextBoxRegularExp(String textBoxValue, String textBoxtest) {
		return textBoxValue.matches("^(?=.*[a-zA-Z])(?=.*[@#$%^&+=/-_]).+$")||textBoxtest.matches("^(?=.*[a-zA-Z])(?=.*[@#$%^&+=/-_]).+$");
	}

	private boolean checkTestboxValue(String textBoxValue, String textBoxtest) {
		return textBoxValue.matches("^[a-zA-Z]+$")||textBoxtest.matches("^[a-zA-Z]+$");
	}

	private boolean textCheck(String text) {
		return text.equalsIgnoreCase("N/A")|| text.equalsIgnoreCase("N//A");
	}


	public void jsSetText(WebDriver drivernew, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();


		try
		{
			waitwebElementVisible(drivernew, element,200);

			 JavascriptExecutor js = (JavascriptExecutor)drivernew;

			 js.executeScript("arguments[0].setAttribute('value','" + text + "');", element);
		}
		catch (WebDriverException e)
		{
			throw new Exception("Element is not enable for set text" + "\r\n" + "error: " + e.getMessage());
		}

		catch (Exception e)
		{
			throw new Exception("Element is not enable for set text" + "\r\n" + "error: " + e.getMessage() + e.getStackTrace());
		}

	}

	public String jsGetText(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		String webText = "";

		try
		{
			webText = element.getText().trim();

		}
		catch (WebDriverException e)
		{
			throw new Exception("Element is not enable for get text" + "\r\n" + "error: " + e.getMessage());
		}

		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw new Exception("Element is not enable for get text" + "\r\n" + "error: " + e.getMessage() +  e.getStackTrace());
		}
		return webText;

	}

	public void submit(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{
			waitwebElementVisible(drivernew, element,200);
			element.submit();
			
			logger.info("Successfully Clicked Button ==> {}" ,element);
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}

	}

	public void setDate(WebDriver drivernew, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{

		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try


		{
			String id = element.getAttribute("ID");
			((JavascriptExecutor)drivernew).executeScript("document.getElementById('" + id + "').removeAttribute('readonly',0);");


			element.click();

			((JavascriptExecutor)drivernew).executeScript("document.getElementById('" + id + "').setAttribute('value', '" + text + "')");

			drivernew.findElement(By.xpath("//div[@id=\"fare_" + text + "\"]")).click();


		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}

	}


	public void waitElementforelementclickable(WebDriver drivernew,WebElement element, int timeOut) throws Exception
	{

		try
		{	
			By locatorvalue=getLocatorvalue(element,drivernew);

			WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(locatorvalue));

		}
		catch (WebDriverException e)
		{
			try 
			{
				Thread.sleep(1500);
				By locatorvalue=getLocatorvalue(element,drivernew);

				WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
				wait.until(ExpectedConditions.elementToBeClickable(locatorvalue));
			}
			catch(WebDriverException f) 
			{

			}


		}
	}



	public static void waitForPageLoad(WebDriver driver) {


	}
	


	public By getLocatorvalue(WebElement element,WebDriver drivernew) 
	{

		String locatoroutput= element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "");
		String[] splitlocator=locatoroutput.split(": ");
		splitlocator[0] = splitlocator[0].replaceAll("\\s", "");	    
		splitlocator[1] =splitlocator[1].replaceAll("\\s", "");


		By locatorvalue=getlocatorvalueforwait(drivernew, splitlocator[0],splitlocator[1]);
		return locatorvalue;
	}

	public By getlocatorvalueforwait(WebDriver drivernew, String locatorType,String locatorValue) 
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		
		try
		{
			By locatorvalue = null;
			locatorType =locatorType.strip().trim();
			locatorValue =locatorValue.strip().trim();
			logger.info("Finding Element having ==> LocatorType = {} => LocatorValue =  {}" ,locatorType , locatorValue);
			switch (locatorType.toLowerCase())
			{
			case " id":
				locatorvalue = By.id(locatorValue.trim());
				break;
			case " name":
				locatorvalue = By.name(locatorValue.trim());
				break;
			case " xpath":
				locatorvalue = By.xpath(locatorValue.trim());
				break;
			case " tag":
				locatorvalue = By.name(locatorValue.trim());
				break;
			case " link text":
				locatorvalue = By.linkText(locatorValue.trim());
				break;
			case " css":
				locatorvalue = By.cssSelector(locatorValue.trim());
				break;
			case " class":
				locatorvalue = By.className(locatorValue.trim());
				break;
			default:
				logger.info("Incorrect Locator Type ==> LocatorType =  {}" , locatorType);
				
			}
			return locatorvalue;
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action ,actionDescription);

			throw e;
		}

	}

	public void waitElementToBeClickable(WebDriver drivernew, By locatorValue, int timeOut)
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		try
		{
			WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(locatorValue));
		}
		catch (WebDriverException e)
		{
			logger.error("Failed ==> {} {}" , action ,actionDescription);
			throw e;
		}
	}

	public void waitforinvisibilityofelement(WebDriver drivernew, By locator) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		try 
		{
			WebDriverWait wait = new WebDriverWait(drivernew, 3000);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(locator));
		}
		catch(Exception e) 
		{
			logger.error("Failed ==> {} {}" , action ,actionDescription);
throw e;
		}

	}

	public String wpGetPageTitle(WebDriver drivernew) 
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		try {
			String wpPageTitle="";
			int len=drivernew.findElements(By.xpath("//*[@alt='Print' and @tabindex='0' and @class='vertical-align-middle cursor-hand height-20px printDoc']/.././../div/h2/span/label")).size();
			if(len>0) 
			{
				wpPageTitle=drivernew.findElement(By.xpath("//*[@alt='Print' and @tabindex='0' and @class='vertical-align-middle cursor-hand height-20px printDoc']/.././../div/h2/span/label")).getText();
			}
			else 
			{
				wpPageTitle="";
			}
			return wpPageTitle;
		}
		catch(Exception e) 
		{
			logger.error("Failed ==> {} {}" , action ,actionDescription);

			throw e;
		}
	}




	public void fluentWait(WebDriver drivernew, WebElement element) 
	{

		try
		{																		

			Wait<WebDriver> wait2 = new FluentWait<WebDriver>(drivernew)
					.ignoring(Exception.class)
					.ignoring(NoSuchElementException.class)
					.ignoring(StaleElementReferenceException.class)
					.ignoring(ElementNotVisibleException.class);

			wait2.until(new Function<WebDriver, WebElement>() 
			{
				public WebElement apply(WebDriver driver) {

					if(element.isDisplayed())
					{
						return element;

					}else
					{
						return null;
					}
				}
			});

		}
		catch (TimeoutException e)
		{

			e.printStackTrace();
		}
	}
	
	public void waitElementVisible(WebDriver drivernew, By locatorValue, int timeOut)
	{



		try
		{
			logger.info("Waiting for Element = {}" ,locatorValue);
			WebDriverWait wait1 = new WebDriverWait(drivernew, timeOut);
			wait1.until(ExpectedConditions.visibilityOfElementLocated(locatorValue));

		}
		catch (TimeoutException e)
		{
			logger.error("Get {} , {} is not visible" , e, locatorValue);
			
		}
	}

	public void waitElementClickable(WebDriver drivernew, WebElement element)
	{
    	String action = "Navigate -> ";
		String actionDescription = "";
		try
		{
			logger.info("Waiting for Element = {}" ,element);
			WebDriverWait wait = new WebDriverWait(drivernew, 5000);
			wait.until(ExpectedConditions.elementToBeClickable(element));
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action ,actionDescription);

			throw e;
		}
	}


	public void waitElementClickable(WebDriver drivernew, WebElement locatorValue, int timeOut)
	{
		
		try
		{
			logger.info("Waiting for Element = {} ", locatorValue);
			WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
			wait.until(ExpectedConditions.elementToBeClickable(locatorValue));

		}
		catch (TimeoutException e)
		{
			logger.error("Get {} , {} is not visible",e ,locatorValue);
			
		}
	}
	public void waitElementEnabled(WebDriver drivernew, WebElement element, int timeOut) throws Exception
	{
		for (int i = 0; i < timeOut; i++)
		{
			try
			{
				if (element.isEnabled())
				{
					break;
				}



			}
			catch (WebDriverException e)
			{
				throw new Exception("Get " + e.getMessage() + ", " + "Element is not visible");
			}
		}
	}

	public void switchFocusToOtherWindow(WebDriver drivernew, By pagetitlelocator)
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		try {
			
			Set<String> allWindows =  drivernew.getWindowHandles();
			for(String curWindow : allWindows){
				drivernew.switchTo().window(curWindow);
				if(!(drivernew.findElements(pagetitlelocator).isEmpty())) 
				{
					String pageTitle=drivernew.findElement(pagetitlelocator).getText();
					logger.info(LOG_SWITCHED_TO,pageTitle);
				}
				else 
				{
					String pageTitle=drivernew.getTitle();
					logger.info(LOG_SWITCHED_TO,pageTitle);

				}
			}

		}
		catch(Exception e) 
		{
			logger.error("Failed ==> {} {} " , action , actionDescription);
			throw e;
		}
	}

	public void switchFocusToMainWindow(WebDriver drivernew, By pagetitlelocator)
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		try {
		
			String pageTitle=drivernew.findElement(pagetitlelocator).getText();
			logger.info(LOG_SWITCHED_TO,pageTitle);
			Set<String> allWindows =  drivernew.getWindowHandles();

			for(String curWindow : allWindows){
				drivernew.switchTo().window(curWindow);
				if(!drivernew.findElements(pagetitlelocator).isEmpty()) 
				{
					pageTitle=drivernew.findElement(pagetitlelocator).getText();
					logger.info(LOG_SWITCHED_TO,pageTitle);
					break;
				}
				else 
				{
					pageTitle=drivernew.getTitle();
					logger.info(LOG_SWITCHED_TO,pageTitle);

				}
			}
		}
		catch(Exception e) 
		{
			logger.error("Failed ==> {} {} " , action , actionDescription);
			throw e;
		}
	}
	
	public void waitTitleContains(WebDriver drivernew, String title) throws Exception
	{
		try
		{
			int timeOut=10000;
			WebDriverWait wait = new WebDriverWait(drivernew, timeOut);
			wait.until(ExpectedConditions.titleContains(title));
		}
		catch (WebDriverException e)
		{
			throw new Exception("Get " + e.getMessage() + ", [" + title + "] is not displayed in WebPage title [" + drivernew.getTitle() + "]");
		}
	}
	
	public String getAttribute(WebDriver drivernew, WebElement element, String attribute)
	{
		return element.getAttribute(attribute);
	}

	public void verifyAttributeValue(WebDriver drivernew, WebElement element, String attribute,String ExpectedText,TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{

		String action = "Verify "+element.getAttribute(attribute)+" Attribute value-->"+ExpectedText;
		String actionDescription = "Verify "+element.getAttribute(attribute)+" Attribute value"+ExpectedText;
		LocalDateTime startTime = LocalDateTime.now();
		if(element.getAttribute(attribute).equalsIgnoreCase(ExpectedText)) 

		{
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
		}
		else 
		{
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

		}
	}
	
	public boolean getTitle(WebDriver drivernew,String expectedTitle, TestCaseParam testCaseParam) throws Exception
	{

		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{

			String pageTitle = drivernew.getTitle();
			boolean validTitle = false;
			if (expectedTitle.contains(pageTitle))
			{
				validTitle = true;
			}
			else
			{
				validTitle = false;
			}
			


			logger.info("Successfully Navigated to {}" ,pageTitle);
			return validTitle;


		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}
	
	public String getCssValue(WebDriver drivernew, WebElement element, String value)
	{
		return element.getCssValue(value);
	}
	
	public String getPageSource(WebDriver drivernew)
	{
		return drivernew.getPageSource();
	}
	
	public void waitForPageToLoad(WebDriver drivernew, int time)
	{
		
		
	}
	
	public void setAttribute(WebDriver drivernew, WebElement element, String attributeName, String value, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{

			WrapsDriver wrappedElement = (WrapsDriver) element;
			if (wrappedElement == null)
				throw new Exception("Element must wrap a web driver");

			drivernew = wrappedElement.getWrappedDriver();
			JavascriptExecutor js = (JavascriptExecutor)drivernew;
			if (js == null)
				throw new Exception("Element must wrap a web driver that supports javascript execution");
			js.executeScript("arguments[0].setAttribute(arguments[1], arguments[2])", element, attributeName, value);
		}
		catch (Exception e)
		{
			logger.error("Failed ==>{} {} " ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	public void clearText(WebDriver drivernew, WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{

			element.clear();
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}
	
	public JavascriptExecutor javaScript(WebDriver driver)
	{
		return (JavascriptExecutor)driver;
	}

	public WebElement findElement(WebDriver drivernew, String value, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{
			WebElement element = null;
			String locatorType = value.split(";")[0];
			String locatorValue = value.split(";")[1];
			logger.info("Finding Element having ==> LocatorType = {} => LocatorValue = {}",locatorType,locatorValue);
			switch (locatorType.toLowerCase())
			{
			case "id":
				element = drivernew.findElement(By.id(locatorValue));
				break;
			case "name":
				element = drivernew.findElement(By.name(locatorValue));
				break;
			case "xpath":
				element = drivernew.findElement(By.xpath(locatorValue));
				break;
			case "tag":
				element = drivernew.findElement(By.name(locatorValue));
				break;
			case "link":
				element = drivernew.findElement(By.linkText(locatorValue));
				break;
			case "css":
				element = drivernew.findElement(By.cssSelector(locatorValue));
				break;
			case "class":
				element = drivernew.findElement(By.className(locatorValue));
				break;
			default:
				logger.info("Incorrect Locator Type ==> LocatorType = {}" ,locatorType);
				
			}
			return element;
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);

			throw e;
		}

	}

	public String getElementText(WebDriver drivernew, String locatorType,String locatorValue, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Navigate -> ";
		String actionDescription = "";
		LocalDateTime startTime=  LocalDateTime.now();
		try
		{


			String text=null;
			WebElement element = null;
			locatorType =locatorType.trim();
			locatorValue =locatorValue.trim();
			logger.info("Finding Element having ==> LocatorType = {}=> LocatorValue = {} " ,locatorType ,locatorValue);
			switch (locatorType.toLowerCase())
			{
			case " id":
				element = drivernew.findElement(By.id(locatorValue));
				text=element.getAttribute("Value");
				text = checkTextNull(text, element);
				break;
			case " name":
			  case " tag":
				element = drivernew.findElement(By.name(locatorValue));
				text=element.getAttribute("Value");
				text = checkTextNull(text, element);
				break;
			case " xpath":
				element = drivernew.findElement(By.xpath(locatorValue));
				text=element.getAttribute("Value");
				text = checkTextNull(text, element);
				break;
			
			case " link text":
				element = drivernew.findElement(By.linkText(locatorValue));
				text=element.getAttribute("Value");
				text = checkTextNull(text, element);
				break;
			case " css":
				element = drivernew.findElement(By.cssSelector(locatorValue));
				text=element.getAttribute("Value");
				text = checkTextNull(text, element);
				break;
			case " class":
				element = drivernew.findElement(By.className(locatorValue));
				text=element.getAttribute("Value");
				text = checkTextNull(text, element);
				break;
			default:
				logger.info("Incorrect Locator Type ==> LocatorType = {} " ,locatorType);
				throw new Exception("Support FindElement with 'id' 'name' 'xpath' 'tag' 'link' 'css' 'class'");
			}
			return text;
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);

			throw e;
		}

	}

	private String checkTextNull(String Text, WebElement element) {
		if(Text==null) 
		{
			Text=element.getText();
		}
		return Text;
	}

	public ArrayList<WebElement> findElements(WebDriver drivernew, String value) 
	{
		List<WebElement> elements = null;
		String locatorType = value.split(";")[0];
		String locatorValue = value.split(";")[1];

		logger.info("Finding Element having ==> LocatorType = {} => LocatorValue =  {} " , locatorType , locatorValue);

		switch (locatorType.toLowerCase())
		{
		case "id":
			elements = drivernew.findElements(By.id(locatorValue));
			break;
		case "name":
			elements = drivernew.findElements(By.name(locatorValue));
			break;
		case "xpath":
			elements = drivernew.findElements(By.xpath(locatorValue));
			break;
		case "tag":
			elements = drivernew.findElements(By.name(locatorValue));
			break;
		case "link":
			elements = drivernew.findElements(By.linkText(locatorValue));
			break;
		case "css":
			elements = drivernew.findElements(By.cssSelector(locatorValue));
			break;
		case "class":
			elements = drivernew.findElements(By.className(locatorValue));
			break;
		default:
			logger.info("Incorrect Locator Type ==> LocatorType = {}" ,locatorType);
			
		}
		return (ArrayList<WebElement>) elements;
	}

	public boolean isElementVisible(WebElement element)
	{
		return element.isDisplayed();
	}

	public void waitwebElementVisible(WebDriver drivernew, WebElement locatorValue, int timeOut) 
	{
	
		try
		{
			logger.info("Waiting for Element = {}" , locatorValue);
            
			boolean visible = isElementVisible(locatorValue);
			while (true)
			{
				if(visible)
				{
					break;
				}
				else
				{

					visible = false;
				}
			}

		}
		catch (Exception e)
		{
			logger.error("Error: {}, Locator value: {} is not visible.", e, locatorValue);
			
		}
	}

	public void verifyTextDisplayed(WebDriver driver, WebElement element, String text, TestCaseParam testCaseParam,PageDetails pageDetails, int timeout) throws Exception
	{

		String value = "";
		
		waitwebElementVisible(driver, element,timeout);

		value = jsGetText(driver, element, testCaseParam,pageDetails);

		if (value.equals(text.trim()))
		{
			logger.info("The value displayed in the application is as expected: ||{}||", value);
		}
		else
		{
			logger.info("The value displayed in the application: ||{}||, Expected Value: ||{}||. This is not as expected.", value, text);
		}
	}



	public void verifyElementPresent(WebDriver driver, WebElement element,TestCaseParam testCaseParam, int timeout) throws Exception
	{
		String testStepName = "VerifyElementPresent";
		String testStepDescription = "VerifyElementPresent";
		LocalDateTime startTime = LocalDateTime.now();
		Boolean isDisplayed = false;

		try
		{
			
			waitwebElementVisible(driver, element,timeout);
			isDisplayed = isElementVisible(element);
			logger.info("Element present in application: {}" , isDisplayed);
			if (isDisplayed)
			{
				logger.info("The Element is displayed in the application");
				
			}
			else
			{
				logger.info("The Element is not displayed in the application");
				
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {}" , testStepDescription);
			exceptionDetails.logExceptionDetails(driver, testCaseParam, testStepName, testStepDescription, startTime);
			throw e;
		}
	}


	public void documentUpload(WebDriver drivernew, WebElement element,String path,String docName, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Upload Document";
		String actionDescription = "Upload Document";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			webkeywords.instance().fluentWait(drivernew, element);


			element.sendKeys(path+docName);

			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);


		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}


	//*********************************SCREEN VALIDATIONS**************************************************
	public void verifyDropdownSelection(WebDriver drivernew, WebElement element, String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Dropdown-->Actual:"+options+":Expected:-"+element.getText();
		String actionDescription = "Verify Dropdown-->Actual:"+options+":Expected:-"+element.getText();
		LocalDateTime startTime = LocalDateTime.now();
		Boolean valuefound = false;

		try
		{
			if(!(options.equals("N//A")||options==null)) 
			{

				webkeywords.instance().fluentWait(drivernew, element);
				Select select = new Select(element);
				List<WebElement> allOptions = select.getOptions();
				for(int i=0; i<allOptions.size(); i++) 
				{

					if(allOptions.get(i).getText().contains(options)) 
					{
						valuefound=true;
						action = "Verify Dropdown-->Actual:"+options+":Expected:-"+allOptions.get(i).getText();
						actionDescription = "Verify Dropdown-->Actual:"+element.getText()+":Expected:-"+allOptions.get(i).getText();
						break;
					}
				}
				if(valuefound) 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}


	public void verifyDropdownText(WebDriver drivernew, WebElement element, String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Dropdown Text-->Actual:"+options+":Expected:-"+element.getText();
		String actionDescription = "Verify Dropdown Text-->Actual:"+options+":Expected:-"+element.getText();
		LocalDateTime startTime = LocalDateTime.now();
		Boolean valuefound = false;

		try
		{
			if(!(options.equals("N//A")||options==null)) 
			{

				webkeywords.instance().fluentWait(drivernew, element);
				Select select = new Select(element);
				List<WebElement> allOptions =select.getAllSelectedOptions();			
				for(int i=0; i<allOptions.size();i++) 
				{
					if(allOptions.get(i).getText().contains(options)) 
					{
						valuefound=true;
						action = "Verify Dropdown-->Actual:"+options+":Expected:-"+allOptions.get(i).getText();
						actionDescription = "Verify Dropdown-->Actual:"+options+":Expected:-"+allOptions.get(i).getText();
						break;
					}
				}

				if(valuefound) 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	public void verifytextentered(WebDriver drivernew, WebElement element, String text, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String action = "Verify Dropdown Selection-->"+text;
		String actionDescription = "Verify Dropdown Selection"+text;
		LocalDateTime startTime = LocalDateTime.now();
		Boolean verified = false;

		try
		{
			if(!(text.equals("N//A")||text==null)) 
			{

				webkeywords.instance().fluentWait(drivernew, element);
				String enteredText = element.getText();

				if(enteredText.equals(text)) 
				{
					verified=true;
				}

				if(verified)
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
				}
			}



		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	public void verifyElementDisplayed(WebDriver drivernew, WebElement element,String testdata, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String actionv1 = "Verify Element Displayed--"+element.getAttribute("Value");
		String actionDescription = "Verify Element Displayed--"+element.getAttribute("Value");
		LocalDateTime startTime = LocalDateTime.now();

		if(!(testdata.equalsIgnoreCase("n/a"))||!(testdata.equalsIgnoreCase("n\\a"))||!(testdata.equalsIgnoreCase("n\\\\a")))
		{

		try
		{
			scrollIntoViewElement(drivernew, element);
			Actions action = new Actions(drivernew);
			action.moveToElement(element).perform();
			if(element.isDisplayed()) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, actionv1, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, actionv1, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,actionv1 , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, actionv1, actionDescription, startTime,e);
			throw e;
		}
		}
	}
	
	public void verifyElementDisplayedtextattribute(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String Action = "Verify Element Displayed--"+element.getText();
		String actionDescription = "Verify Element Displayed--"+element.getText();
		LocalDateTime startTime = LocalDateTime.now();

		if(!(testData.equalsIgnoreCase("n/a"))||!(testData.equalsIgnoreCase("n\\a"))||!(testData.equalsIgnoreCase("n\\\\a")))
		{

		try
		{
			Actions action = new Actions(drivernew);
			action.moveToElement(element).perform();
			if(element.isDisplayed()) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, Action, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, Action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , Action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, Action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public void verifyelementnotdisplayed(WebDriver drivernew, List<WebElement> element, String TestData, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element Not Displayed--"+ TestData;
		String actionDescription = "Verify Element Not Displayed--"+ TestData;
		LocalDateTime startTime = LocalDateTime.now();

		if(!(TestData.equalsIgnoreCase("n/a"))|| !(TestData.equalsIgnoreCase("n\\a"))|| !(TestData.equalsIgnoreCase("n\\\\a")))
		{


		try
		{

			if(!element.isEmpty()) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);
			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==>{} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}
	
	
	public void verifyelementnotdisplayed(WebDriver _driver, String xpath, String TestData, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String Action = "Verify Element Not Displayed--"+ TestData;
		String ActionDescription = "Verify Element Not Displayed--"+ TestData;
		LocalDateTime StartTime = LocalDateTime.now();

		if(!(TestData.equalsIgnoreCase("n/a"))||!(TestData.equalsIgnoreCase("n\\a"))||!(TestData.equalsIgnoreCase("n\\\\a")))
		{

		try
		{

			int size=_driver.findElements(By.xpath(xpath)).size();
			if(size>0) 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSFAIL);
			}
			else 
			{
				testStepDetails.logTestStepDetails(_driver, testCaseParam, Action, ActionDescription,pageDetails, StartTime, STATUSPASS);
			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , Action , ActionDescription);
			testStepDetails.logExceptionDetails(_driver, testCaseParam, Action, ActionDescription, StartTime,e);
			throw e;
		}
		}
	}




	public void verifyElementEnabled(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String actionnew = "Verify Element Enabled";
		String actionDescription = "Verify Element Enabled";
		LocalDateTime startTime = LocalDateTime.now();

				if (!testData.equalsIgnoreCase("n/a") || !testData.equalsIgnoreCase("n\\a") || !testData.equalsIgnoreCase("n\\\\a"))

			{

		try
		{
			Actions action=new Actions(drivernew);
			action.moveToElement(element);

			if(element.isEnabled())
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, actionnew, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, actionnew, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==>{} {} " , actionnew , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, actionnew, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public void verifyElementSelected(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element Selected";
		String actionDescription = "Verify Element Selected";
		LocalDateTime startTime = LocalDateTime.now();

		if(!(testData.equalsIgnoreCase("n/a"))||!(testData.equalsIgnoreCase("n\\a"))||!(testData.equalsIgnoreCase("n\\\\a")))
		{


		try
		{

			if(element.isSelected()) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public void verifyElementDisabled(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element Disabled";
		String actionDescription = "Verify Element Disabled";
		LocalDateTime startTime = LocalDateTime.now();
		if(!(testData.equalsIgnoreCase("n/a"))||!(testData.equalsIgnoreCase("n\\a"))||!(testData.equalsIgnoreCase("n\\\\a")))
		{


		try
		{


			if(!element.getAttribute("disabled").equals("true")) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public void verifyCheckBoxChecked(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify CheckBox Checked";
		String actionDescription = "Verify CheckBox Checked";
		LocalDateTime startTime = LocalDateTime.now();

		if(!(testData.equalsIgnoreCase("n/a"))||!(testData.equalsIgnoreCase("n\\a"))||!(testData.equalsIgnoreCase("n\\\\a")))
		{

		try
		{

			if(element.getAttribute("checked").equals("true")) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public void verifyElementTitle(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element Title";
		String actionDescription = "Verify Element Title";
		LocalDateTime startTime = LocalDateTime.now();

		if(!(testData.equalsIgnoreCase("n/a"))||!(testData.equalsIgnoreCase("n\\a"))||!(testData.equalsIgnoreCase("n\\\\a")))
		{


		try
		{

			if(element.getAttribute("title").equals(testData)) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public void verifyTextBoxValue(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify TextBox Value";
		String actionDescription = "Verify Element Title";
		LocalDateTime startTime = LocalDateTime.now();

		if(!(testData.equalsIgnoreCase("n/a"))||!(testData.equalsIgnoreCase("n\\a"))||!(testData.equalsIgnoreCase("n\\\\a")))
		{

		try
		{
			webkeywords.instance().fluentWait(drivernew, element);
			if(element.getAttribute("value").equals(testData)) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, "Pass");
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public boolean verifyTextDisplayed(WebDriver drivernew, WebElement element,String testdata, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "";
		String actionDescription = "";
		LocalDateTime startTime = LocalDateTime.now();

boolean isresult=false;
		
if(!(testdata.equalsIgnoreCase("n/a"))||!(testdata.equalsIgnoreCase("n\\a"))||!(testdata.equalsIgnoreCase("n\\\\a")))
{


		try
		{

			if(!(testdata.equals("N//A")||testdata==null))
			{

				scrollIntoViewElement(drivernew, element);
				zoomWebPage(drivernew,"50%",testCaseParam,pageDetails);
				action="Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testdata;
				actionDescription = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testdata;
				webkeywords.instance().fluentWait(drivernew, element);
				if(testdata.contains("\""))
				{
					testdata=testdata.replace("\"", "");
				}
				if(element.getText().toLowerCase().equalsIgnoreCase(testdata.toLowerCase()))
				{
					testStepDetails.logVerificationDetails(drivernew, testCaseParam, action, actionDescription, startTime, STATUSDONE, element.getText(), testdata);
					isresult=true;
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

					isresult=false;
				}
			}
			zoomWebPage(drivernew,"100%",testCaseParam,pageDetails);
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}
		return isresult;
	}


	
	public boolean verifyText(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "";
		String actionDescription = "";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{

			if(!(testData.equals("N//A")||testData==null)) 
			{

				scrollIntoViewElement(drivernew, element);
				zoomWebPage(drivernew,"50%",testCaseParam,pageDetails);
				action="Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
				actionDescription = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
				webkeywords.instance().fluentWait(drivernew, element);
				if(testData.contains("\"")) 
				{
					testData=testData.replace("\"", "");
				}
				if(element.getText().toLowerCase().equalsIgnoreCase(testData.toLowerCase())) 
				{
					testStepDetails.logVerificationDetails(drivernew, testCaseParam, action, actionDescription, startTime, STATUSDONE, element.getText(), testData);
					
					return true;
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
					return false;
				}
			}
			zoomWebPage(drivernew,"100%",testCaseParam,pageDetails);
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		return false;
		}

	

	public void verifyTextDisplayedValueAttribute(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
		String actionDescription = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
		LocalDateTime startTime = LocalDateTime.now();

		if(!(testData.equalsIgnoreCase("n/a"))||!(testData.equalsIgnoreCase("n\\a"))||!(testData.equalsIgnoreCase("n\\\\a")))
		{


		try
		{

			if(!(testData.equals("N//A")||testData==null)) 
			{

				webkeywords.instance().fluentWait(drivernew, element);
				if(testData.contains("\"")) 
				{
					testData=testData.replace("\"", "");
				}
				if(element.getAttribute("value").toLowerCase().equalsIgnoreCase(testData)) 
				{
					testStepDetails.logVerificationDetails(drivernew, testCaseParam, action, actionDescription, startTime, STATUSDONE, element.getText(), testData);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
	}

	public void verifyTextDisplayedTitleAttribute(WebDriver drivernew, WebElement element,String testData, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
		String actionDescription = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+testData;
		LocalDateTime startTime = LocalDateTime.now();

		if(!(testData.equalsIgnoreCase("n/a"))||!(testData.equalsIgnoreCase("n\\a"))||!(testData.equalsIgnoreCase("n\\\\a")))
		{

		try
		{
			

			if(!(testData.equals("N//A")||testData ==null)) 
			{

				webkeywords.instance().fluentWait(drivernew, element);
				if(testData.contains("\"")) 
				{
					testData=testData.replace("\"", "");
				}
				if(element.getAttribute("title").toLowerCase().equalsIgnoreCase(testData)) 
				{
					testStepDetails.logVerificationDetails(drivernew, testCaseParam, action, actionDescription, startTime, STATUSDONE, element.getText(), testData);
					
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
		}
		}



	public void verifyTextDisplayedNew(WebDriver drivernew, WebElement element,ArrayList<String> Text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+Text;
		String actionDescription = "Actual Text Displayed-->"+element.getText()+"Contains Expected Text:-"+Text;
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			if(!(Text.contains("N//A")||Text==null)) 
			
			{
				webkeywords.instance().fluentWait(drivernew, element);
				if(element.getText().contains(Text.get(0))) 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	public boolean verifyTableData(WebDriver drivernew, ArrayList<WebElement> element,String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Table Columns Text Displayed-->";
		String actionDescription = "Table Columns Text Displayed-->";
		LocalDateTime startTime = LocalDateTime.now();
		boolean verifydata=false;

		try
		{
			if(!(text.equals("N//A")||text==null))
			{


				ArrayList<WebElement> elementnew=new ArrayList<>();
				elementnew=element;
				ArrayList<String> dataList = new ArrayList<>();

				String[] dataCount = new String[element.size()];
				int l = 0;
				for (WebElement data : elementnew)
				{
					action = "Actual Text Displayed-->"+data.getText()+"Expected:-"+text;
					actionDescription = "Actual Text Displayed-->"+data.getText()+"Expected:-"+text;
					dataCount[l++] = data.getText();
					dataList.add(data.getText());

					if(data.getText().contains(text)) 
					{
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action,actionDescription,pageDetails, startTime, STATUSPASS);
						verifydata= true;
					} 
					else 
					{
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action,actionDescription,pageDetails, startTime, STATUSFAIL);
						verifydata= false;
					}

				}
			}
		

			return 	verifydata;	


		}
		catch (Exception e)
		{
			logger.error("Failed ==>{} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			return false;
		}
	}

	public void verifyTableDataifNull(WebDriver drivernew, ArrayList<WebElement> element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Table Columns Text Displayed-->";
		String actionDescription = "Table Columns Text Displayed-->";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			ArrayList<WebElement> elementnew =new ArrayList<>();
			elementnew=element; 
			ArrayList<String> dataList = new ArrayList<>();

			String[] dataCount = new String[element.size()];
			int l = 0;
			for (WebElement data : elementnew)
			{
				action = "Table Columns Text Displayed-->"+data.getText();
				actionDescription = "Table Columns Text Displayed-->"+data.getText();
				dataCount[l++] = data.getText();
				dataList.add(data.getText());
				if(data.getText()!=null) 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}





		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	public void verifyPartialTextDisplayed(WebDriver drivernew, WebElement element,String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element Partial Text";
		String actionDescription = "Verify Element Partial Text";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{

			if(element.getText().toLowerCase().contains(text.toLowerCase())) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}
	public void verifyPartialTextDisplayed(WebDriver drivernew, WebElement element,String textnew,String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verified "+text+" Partial Text from "+element.getText();
		String actionDescription = "Verified "+text+" Partial Text from "+element.getText();
		LocalDateTime startTime = LocalDateTime.now();


		try
		{

			if(element.getText().toLowerCase().contains(text.toLowerCase())) 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}



	public void verifyValueEntered(WebDriver drivernew, WebElement element,String text, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element  Text";
		String actionDescription = "Verify Element  Text";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			if(!(text.equalsIgnoreCase("N//A")||text.equalsIgnoreCase("N/A"))) 
			{


				String actualText=element.getAttribute("value");
				if(text.contains("\"")) 
				{
					text=text.replace("\"", "");
				}
				if(actualText.equals("___-__-____")) 
				{
					actualText=actualText.replace("___-__-____", "");
				}
				if(actualText.contains("_-")) 
				{
					actualText=actualText.replace("_", "");
					actualText=actualText.replace("-", "");
				}


				action = "Actual Value Displayed-->"+actualText+"<--> Expected Value-->"+text;
				actionDescription = "Actual Value Displayed-->"+actualText+"<-->Expected Value-->"+text;

				if(actualText.equalsIgnoreCase(text)) 
				{

					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
				}
			}	
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}

	public void verifyValueSelected(WebDriver drivernew, WebElement element,String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element  Text";
		String actionDescription = "Verify Element  Text";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			if(!isOptionsTrue(options)) 
							{								
					Select select = new Select(element);
					element=select.getFirstSelectedOption();

					String actualText=element.getText();


					action = "Actual Value Displayed-->"+actualText+"<--> Expected Value-->"+options;
					actionDescription = "Actual Value Displayed-->"+actualText+"<-->Expected Value-->"+options;

					if(actualText.equals(options)) 
					{

						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
					}
					else 
					{
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
					}
				}
			
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}", action, actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}



	public void verifyDisabledPropertyOfElement(WebDriver drivernew,WebElement element, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Disabled Property of Element";
		String actionDescription = "Verify Disabled Property of Element";
		LocalDateTime startTime = LocalDateTime.now();
		fluentWait(drivernew,element);
		if(pageDetails != null)
		{
		try
		{
			Boolean displayed = true;
			displayed = element.isDisplayed();
			if (displayed)
			{
				String isDisabled = "";

				isDisabled = element.getAttribute("disabled");
				isDisabled = isReadyEmpty(isDisabled);
				String isReadOnly = "";
				isReadOnly = element.getAttribute("readonly");
				isReadOnly = isReadyEmpty(isReadOnly);
				String pointereventproperty="";
				pointereventproperty =element.getAttribute("style");
				boolean pointerevent=pointereventproperty.contains("pointer-events: none");
				
				if(pointerevent) 
				{
					action = "The field is Disabled and Readonly, which is as expected";
					actionDescription = "The field is Disabled and Readonly, which is as expected";
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);                    
				}

				else 
				{
					if (isReadDisableTrue(isDisabled, isReadOnly))
					{
						action = "The field is Disabled and Readonly, which is as expected";
						actionDescription = "The field is Disabled and Readonly, which is as expected";
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);

					}
					else if (isReadblnkisDisabledtrue(isDisabled, isReadOnly))
					{
						action = "The field is Disabled, which is as expected";
						actionDescription = "The field is Disabled, which is as expected";
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);
					}
					else if (isDisableNull(isDisabled, isReadOnly))
					{
						action = "The field is Readonly, which is as expected";
						actionDescription = "The field is Readonly, which is as expected";
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSPASS);

					}

					else
					{
						action = "The field is not Disabled and Readonly, which is not as expected";
						actionDescription = "The field is not Disabled and Readonly, which is not as expected";
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

					}

				}




			}
			else
			{
				action = "The Element is not found";
				actionDescription = "The field is not Disabled and Readonly, which is not as expected";
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action, actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
		}
	}
	

	private String isReadyEmpty(String isReadOnly) {
		if(isReadOnly==null) 
		{
			isReadOnly="";
		}
		return isReadOnly;
	}

	private boolean isDisableBlank(String isDisabled) {
		return isDisabled.equals("")||isDisabled==null;
	}

	private boolean isDisableNull(String isDisabled, String isReadOnly) {
		return isReadOnly.equals("true")&&isDisableBlank(isDisabled);
	}

	private boolean isReadblnkisDisabledtrue(String isDisabled, String isReadOnly) {
		return isReadOnly.equals("") && isDisabled.equals("true");
	}

	private boolean isReadDisableTrue(String isDisabled, String isReadOnly) {
		return isDisabled.equals("true") && isReadOnly.equals("true");
	}

	public void verifypropertyofelement(WebDriver drivernew, WebElement element, String dataValue, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String action = "Verify Property of Element-->"+element.getAttribute("Value");
		String actionDescription = "Verify Property of Element-->"+element.getAttribute("Value");
		LocalDateTime startTime = LocalDateTime.now();
		if(pageDetails != null) 
		{
			
		
		try
		{
			Boolean displayed = true;
			displayed = element.isDisplayed();
			if (displayed)
			{
				String prop = "";
				String[] data = null;

				data = dataValue.split("&");
				prop=element.getAttribute(data[0]) ;

				if (prop.equals(data[1]))
				{
					action = "The " + data[0] + " Property of the element is as expected";
					actionDescription = "The " + data[0] + " Property of the element is as expected";
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

				}
				else 
				{
					action = "The " + data[0] + " Property of the element is not as expected";
					actionDescription = "The " + data[0] + " Property of the element is not as expected";
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
		}
	}

	public void verifyCheckBoxIsNotChecked(WebDriver drivernew,WebElement element,TestCaseParam testCaseParam, PageDetails pageDetails ) throws Exception
	{
		String action="";
		String actionDescription="";
		LocalDateTime startTime = LocalDateTime.now();
		try
		{

			if (!element.isSelected())
			{

				action = "The element is Not checked";
				actionDescription = "The element is Not checked";
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

			}
			else
			{
				action = "The element is  checked";
				actionDescription = "The element is  checked";
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

			}
		}
		catch (Exception e)
		{

			logger.error("Failed ==>{} {} " , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}

	}

	public void dismissAlertMessage(WebDriver drivernew, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{


		String action="";
		String actionDescription="";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			drivernew.switchTo().alert().dismiss();

		}
		catch (Exception e)
		{
			action = "Failed to Dismiss Alert Message";
			actionDescription = "Failed to Dismiss Alert Message";
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

		}


	}

	public void enterTextByFunctionKeys(WebDriver drivernew,WebElement element,Keys key,String keyValue, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{


		String action="";
		String actionDescription="";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			webkeywords.instance().fluentWait(drivernew, element);
			WebDriverWait wait = new WebDriverWait(drivernew, 1000);
			wait.until(ExpectedConditions.elementToBeClickable(element));				

			element.sendKeys(key+keyValue);

			LocalDateTime endTime =  LocalDateTime.now();

			logger.info("Successfully Entered Text {} to {}",key,element);
		}
		catch (Exception e)
		{
			action = "Failed to Dismiss Alert Message";
			actionDescription = "Failed to Dismiss Alert Message";
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

		}


	}

	public void enterTextByFunctionKeys(WebDriver drivernew,WebElement element,Keys key, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{


		String action="";
		String actionDescription="";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			webkeywords.instance().fluentWait(drivernew, element);
			WebDriverWait wait = new WebDriverWait(drivernew, 1000);
			wait.until(ExpectedConditions.elementToBeClickable(element));				

			element.sendKeys(key);

			LocalDateTime endTime =  LocalDateTime.now();

			logger.info("Successfully Entered Text {} to {}",key ,element);
		}
		catch (Exception e)
		{
			action = "Failed to Dismiss Alert Message";
			actionDescription = "Failed to Dismiss Alert Message";
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

		}


	}

	public void zoomWebPage(WebDriver drivernew,String zoomValue, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{


		String action="";
		String actionDescription="";
		LocalDateTime startTime = LocalDateTime.now();



		try
		{

			JavascriptExecutor executor = (JavascriptExecutor)drivernew;
			executor.executeScript("document.body.style.zoom = '"+zoomValue+"'");
			action = "Sucessfully Zoomed the page to -->"+zoomValue;
			actionDescription = "Sucessfully Zoomed the page to -->"+zoomValue;
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);


		}
		catch (Exception e)
		{
			action = "Failed to Zoom the page";
			actionDescription = "Failed to Zoom the page";
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

		}


	}

	public void mouseHover(WebDriver drivernew,WebElement element, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{


		String actionnew="Mouse Hover";
		String actionDescription="Mouse Hover";
		LocalDateTime startTime = LocalDateTime.now();



		try
		{
			webkeywords.instance().fluentWait(drivernew, element);
			Actions action = new Actions(drivernew);

			action.moveToElement(element).build().perform();
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, actionnew, actionDescription,pageDetails, startTime, STATUSDONE);


		}
		catch (Exception e)
		{
			actionnew = "Failed do mouse hover";
			actionDescription = "Failed do mouse hover";
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, actionnew, actionDescription,pageDetails, startTime, STATUSFAIL);

		}


	}

	public void verifyDropdownValues(WebDriver drivernew, WebElement element,String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Dropdown Values";
		String actionDescription = "Verify Dropdown Values";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			if(!isOptionsTrue(options)) 
			{


						
					ArrayList<String> actualvalues=new ArrayList<>();
					ArrayList<String> expectedvalues=new ArrayList<>();
					Select select = new Select(element);
					int count=0;

					String[] exvalues=options.split(";");
					for(int i=0; i<exvalues.length;i++) 
					{
						expectedvalues.add(exvalues[i]);
					}

					int expectedcount=expectedvalues.size();
					count = actualvaluesList(actualvalues, expectedvalues, select, count);
					countZeroValidation(drivernew, testCaseParam, pageDetails, action, actionDescription, startTime,
							count);
					if(count==expectedcount) 
					{
						action = "Verify Dropdown values<=>Expected Values="+expectedvalues+"<=>Actual Values="+actualvalues;
						actionDescription = "Verify Dropdown values<=>Expected Values="+expectedvalues+"<=>Actual Values="+actualvalues;
						testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);

					}

				
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action ,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}

	private int actualvaluesList(ArrayList<String> actualvalues, ArrayList<String> expectedvalues, Select select,
			int count) {
		List<WebElement> elements = select.getOptions();
		for (WebElement we : elements) {
			for (int i = 0; i < expectedvalues.size(); i++) 
			{
				if (we.getText().equals(expectedvalues.get(i))) 
				{
					actualvalues.add(we.getText());
					count++;
					break;
				}
			}

		}
		return count;
	}

	private void countZeroValidation(WebDriver drivernew, TestCaseParam testCaseParam, PageDetails pageDetails,
			String action, String actionDescription, LocalDateTime startTime, int count) throws Exception {
		if(count==0) 
		{
			testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

		}
	}

	private boolean isOptionsTrue(String options) {
		return options.equals("N//A") || options.equals("N/A") || options.equals("n//a") || options.equals("n/a")||options==null;
	}

	public void scrollUpPageToTheTop(WebDriver driver) 
	{

		String action = "Scroll Up Page To The Top";
		String actionDescription = "Scroll Up Page To The Top";



		try
		{

			((JavascriptExecutor) driver).executeScript("window.scrollTo(document.body.scrollHeight, 0)");


		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}",action,actionDescription);
		
		}



	}

	public void scrollIntoViewElement(WebDriver driver, WebElement element)
	{

		webkeywords.instance().fluentWait(driver, element);

		JavascriptExecutor js = (JavascriptExecutor) driver;

		js.executeScript("arguments[0].scrollIntoView(true);",element);

	}



	public void verifydropdownoptionnotavailable(WebDriver drivernew, WebElement element, String options, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String action = "Verifying Dropdown Options -->"+element.getText()+" : does not contain :-"+options;
		String actionDescription = "Verifying Dropdown Options -->"+element.getText()+" : does not contain :-"+options;
		LocalDateTime startTime = LocalDateTime.now();
		Boolean valuefound = true;

		try
		{
			if (!("N//A".equals(options) || options == null))
				{


				webkeywords.instance().fluentWait(drivernew, element);
				Select select = new Select(element);
				List<WebElement> allOptions = select.getOptions();
				for(int i=0; i<allOptions.size(); i++) 
				{

					if(allOptions.get(i).getText().contains(options)) 
					{
						valuefound=false;
						action = "Verify Dropdown-->contains "+allOptions.get(i).getText()+"which is not as Expected";
						actionDescription = "Verify Dropdown-->contains "+allOptions.get(i).getText()+"which is not as Expected";

						break;
					}
				}
				if(valuefound) 
				{
					testStepDetails.logVerificationDetails(drivernew, testCaseParam, action, actionDescription, startTime, STATUSDONE, element.getText(), options);
				}
				else 
				{
					testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);

				}
			}
		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" , action , actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}




	public void openNewTabwithURL(WebDriver drivernew, String url, TestCaseParam testCaseParam, PageDetails pageDetails) throws Exception
	{
		String action = "Open new tab with url";
		String actionDescription = "Open new tab with url";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			JavascriptExecutor javaScript = (JavascriptExecutor) drivernew;

			javaScript.executeScript("window.open(arguments[0])", url);
			webkeywords.instance().waitForPageToLoad(drivernew, 5000);
			Thread.sleep(5000);
			String currentWindow = drivernew.getWindowHandle();
			logger.info("Current Window: {} " , currentWindow);

			int windowsCount = drivernew.getWindowHandles().size();
			logger.info("Count of Windows: {}" , windowsCount);

			if (windowsCount > 1) 
			{
				logger.info("New Window is opened. Switching the control to the new window");
				ArrayList<String> tab = new ArrayList<>(drivernew.getWindowHandles());
				drivernew.switchTo().window(tab.get(1));
				webkeywords.instance().waitForPageToLoad(drivernew, 5000);
			}

		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}",action,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;
		}
	}

	public void verifyDropdownValueSelected(WebDriver drivernew, WebElement element,String options, TestCaseParam testCaseParam,PageDetails pageDetails) throws Exception
	{
		String action = "Verify Element  Text";
		String actionDescription = "Verify Element  Text";
		LocalDateTime startTime = LocalDateTime.now();


		try
		{
			String actualText=element.getAttribute("title");


			action = "Actual Value Displayed-->"+actualText+"<--> Expected Value-->"+options;
			actionDescription = "Actual Value Displayed-->"+actualText+"<-->Expected Value-->"+options;

			if(actualText.equals(options)) 
			{

				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSDONE);
			}
			else 
			{
				testStepDetails.logTestStepDetails(drivernew, testCaseParam, action, actionDescription,pageDetails, startTime, STATUSFAIL);
			}



		}
		catch (Exception e)
		{
			logger.error("Failed ==> {} {}" ,action,actionDescription);
			testStepDetails.logExceptionDetails(drivernew, testCaseParam, action, actionDescription, startTime,e);
			throw e;

		}
	}


public  void navigateToNextPage(WebDriver driver,WebElement element, TestCaseParam testCaseParam, PageDetails pageDetails ) throws Exception
{
	String action = "Capture Page Response Time";
	String actionDescription = "Capture Page Response Time";
	String sspHeader = "//div[@class='ssp-header']/..//h1";


	String getHeaderBeforeClick = "";
	String getHeaderAfterClick = "";
	
	try {
		WebDriverWait wait=new WebDriverWait(driver, 3);
		wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Spinner']")));
	    getHeaderBeforeClick = driver.findElement(By.xpath("//div[@class='ssp-menuItemDropDownHeader']/p")).getText();
	    if (getHeaderBeforeClick.equals("")){
	        getHeaderBeforeClick = driver.findElement(By.xpath(sspHeader)).getText();
	    }
	    
	} catch (Exception e) {
	  logger.info("Getting title of Current page");
	    getHeaderBeforeClick = driver.getTitle();
	}

	LocalDateTime startTime = LocalDateTime.now();
	if (!element.getText().equals("NULL") || !element.getText().equals("") || !element.getAttribute("title").isBlank()) {
	    try {
	        if (element.isDisplayed()) {
	            JavascriptExecutor executor = (JavascriptExecutor) driver;
	            executor.executeScript("arguments[0].click();", element);
	            WebDriverWait wait = new WebDriverWait(driver, 3);
	            wait.until(webDriver -> ((JavascriptExecutor) webDriver).executeScript("return document.readyState").equals("complete"));
	        }
	    } catch (Exception e) {
	        logger.info("Unable to click on element");
	   }
		long startTimePerf = System.currentTimeMillis();
		
	    try {
	        WebDriverWait wait=new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Spinner']")));
		    getHeaderAfterClick = driver.findElement(By.xpath("//div[@class='ssp-menuItemDropDownHeader']/p")).getText();
	        if (getHeaderAfterClick.equals("")) {
	            getHeaderAfterClick = driver.findElement(By.xpath(sspHeader)).getText();
	        }
	    } catch (Exception e) {
	       logger.info("Getting Next Page Title");
	        getHeaderAfterClick = driver.getTitle();
	    }
	    
	    try {
	    	WebDriverWait wait=new WebDriverWait(driver, 5);
			wait.until(ExpectedConditions.invisibilityOfElementLocated(By.xpath("//img[@alt='Spinner']")));
		   
			logger.info("Current Page title : {}",getHeaderBeforeClick);
			logger.info("Next Page title : {}",getHeaderAfterClick);
			

	        logger.info("Measuring performance metrics done");
	        
	} catch (NoSuchElementException e) {
	    logger.error("Failed ==> {} {}",action,actionDescription);
	    testStepDetails.logExceptionDetails(driver, testCaseParam, action, actionDescription, startTime, e);
	    throw e;
	}
	}


	

}
}