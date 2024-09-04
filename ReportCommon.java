  package report_utilities.common;

  import org.openqa.selenium.WebDriver;

  import org.slf4j.Logger;
  import org.slf4j.LoggerFactory;
  import report_utilities.Constants.ReportContants;
import report_utilities.Model.TestCaseParam;
import report_utilities.Model.TestStepModel;
import report_utilities.Model.ExtentModel.ExtentUtilities;
import report_utilities.Model.ExtentModel.PageDetails;
import report_utilities.Model.ExtentModel.TestCaseDetails;
import report_utilities.Model.ExtentModel.TestStepDetails;
import report_utilities.TestResultModel.BrowserResult;
import report_utilities.TestResultModel.ModuleResult;
import report_utilities.TestResultModel.TestCaseResult;

import java.time.Duration;
  import java.time.LocalDateTime;

  import java.time.format.DateTimeFormatter;
  import java.time.temporal.ChronoUnit;
  import java.util.*;
  import java.util.stream.Collectors;

public class ReportCommon
{
	static final Logger logger = LoggerFactory.getLogger(ReportCommon.class.getName());

	ScreenshotCommon screenshotCommon = new ScreenshotCommon();
	ExtentUtilities extentUtilities = new ExtentUtilities();
	
	public void logTestStepDetails(WebDriver drrc, TestCaseParam testCaseParam, String testStepName, String testStepDescription,PageDetails pageDetails, LocalDateTime startTime,String status) throws Exception
	{
	LocalDateTime  endTime = LocalDateTime.now();
		
		TestStepModel testStepModel = new TestStepModel();
		
        String screenShotData="";
		
		if(status.equalsIgnoreCase("PASS"))
		{
			screenShotData=screenshotCommon.captureScreenShot(drrc, testCaseParam.TestCaseName);
		}
		else if(status.equalsIgnoreCase("DONE"))
		{
			if(ReportContants.isScreenShotforAllPass)
			{
			screenShotData=screenshotCommon.captureScreenShot(drrc, testCaseParam.TestCaseName);
			}
		}
		
		else if(status.equalsIgnoreCase("FAIL"))
		{
			screenShotData=screenshotCommon.captureScreenShot(drrc, testCaseParam.TestCaseName);
		}
		testStepName=getActionName( pageDetails.PageActionName,testStepName);
		testStepDescription=getActionDesc(pageDetails.PageActionDescription,testStepDescription );
	
			
			testStepModel=testStepModel.AddTestStepDetails(testStepName, testStepDescription, startTime, endTime,
					status,screenShotData);

			TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
			extentUtilities.Log(temptestCaseParam, testStepModel);

	}

	public void logExceptionDetails(WebDriver drex, TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime) throws Exception
	{
		TestStepModel testStepModel = new TestStepModel();

		testStepModel=testStepModel.AddTestStepDetails(testStepName, testStepDescription, startTime, LocalDateTime.now(),
			"Fail", screenshotCommon.captureScreenShot(drex, testCaseParam.TestCaseName));
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);

	}
	
	public void logExceptionDetails(WebDriver drex, TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime,Exception ex) throws Exception
	{
		TestStepModel testStepModel = new TestStepModel();
		
		StringBuilder stack= new StringBuilder();
		for(StackTraceElement s: ex.getStackTrace()) {
			stack = (stack.append(s)).append(";");
		}
		testStepModel=testStepModel.AddTestStepErrorDetails(testStepName, testStepDescription, startTime, LocalDateTime.now(),
			"Fail", screenshotCommon.captureScreenShot(drex, testCaseParam.TestCaseName),ex.getMessage(),stack.toString());
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);

	}
	
    public void logverificationdetailsLabel(TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime, String status, String expectedResponse , String actualResponse)
    {
          TestStepModel testStepModel = new TestStepModel();
    testStepModel=testStepModel.AddVerificationStep(testStepName, testStepDescription, startTime, LocalDateTime.now(),
                       status, expectedResponse, actualResponse);
          TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
          extentUtilities.Log(temptestCaseParam, testStepModel);
    }

	
	public void logExceptionDetails(TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime,Exception ex)
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddTestStepErrorDetails(testStepName, testStepDescription, startTime, LocalDateTime.now(),
			"Fail","",ex.getMessage(),Arrays.toString(ex.getStackTrace()));
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);

	}
	
	public void logDBExceptionDetails(TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTimes,Exception ex)
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddTestStepErrorDetails(testStepName, testStepDescription, startTimes, LocalDateTime.now(),
			"Fail", "",ex.getMessage(),Arrays.toString(ex.getStackTrace()));
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);

	}
	

	public void logVerificationDetails(WebDriver driverVD, TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime,String status, String expectedResponse , String actualResponse) throws Exception
{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddVerificationStep(testStepName, testStepDescription, startTime, LocalDateTime.now(),
				status, screenshotCommon.captureScreenShot(driverVD, testCaseParam.TestCaseName),expectedResponse,actualResponse);
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);


	}
	
	public void logDBVerificationDetails(TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime,String status, String expectedResponse , String actualResponse)
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddVerificationStep(testStepName, testStepDescription, startTime, LocalDateTime.now(),
				status, "",expectedResponse,actualResponse);
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);


	}
	
	
	public void logAPIModule( TestCaseParam testCaseParam, String apidetails)
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddModuleData(apidetails);
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);


	}

		public void logAPIDetails(TestCaseParam testCaseParam, String testStepName, String testStepDescription,PageDetails pageDetails, LocalDateTime startTime,String status, String url, String request, String response) throws Exception
	{
	LocalDateTime  endTime = LocalDateTime.now();
	
	ArrayList<String > fileData = new ArrayList<>();
	fileData.add("********************************************************************************************************");
	fileData.add("*********************************************************URL********************************************");
	fileData.add("********************************************************************************************************");
	fileData.add(url);
	fileData.add("********************************************************************************************************");
	fileData.add("*********************************************************Request********************************************");
	fileData.add("********************************************************************************************************");

	fileData.add(request);

	fileData.add("********************************************************************************************************");
	fileData.add("*********************************************************Response********************************************");
	fileData.add("********************************************************************************************************");

	fileData.add(response);
		
		TestStepModel testStepModel = new TestStepModel();
		
        String screenShotData="";
		
		if(status.equalsIgnoreCase("PASS"))
		{
			 screenShotData=screenshotCommon.createTextFileArrayList(fileData, testStepName);
		}
		else if(status.equalsIgnoreCase("DONE"))
		{
			if(ReportContants.isScreenShotforAllPass)
			{
				  screenShotData=screenshotCommon.createTextFileArrayList(fileData, testStepName);
			}
		}
		
		else if(status.equalsIgnoreCase("FAIL"))
		{
			 screenShotData=screenshotCommon.createTextFileArrayList(fileData, testStepName);
		}
		testStepName=getActionName( pageDetails.PageActionName,testStepName);
		testStepDescription=getActionDesc(pageDetails.PageActionDescription,testStepDescription );
	
			
			testStepModel=testStepModel.AddTestStepDetails(testStepName, testStepDescription, startTime, endTime,
					status,screenShotData);

			TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
			extentUtilities.Log(temptestCaseParam, testStepModel);

	}


	
    public void logAPITestStep(TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime,String status, String expectedResponse , String actualResponse)
    {
    	

		TestStepModel testStepModel = new TestStepModel();
          
		testStepModel=testStepModel.addAPITestStep(testStepName, testStepDescription, startTime,LocalDateTime.now(),status,
                       expectedResponse,actualResponse);
          

          TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
  		extentUtilities.Log(temptestCaseParam, testStepModel);
    }


	
	public void logDBVerificationDetails(TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime,String status, String expectedResponse , String actualResponse, String query)
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddVerificationStep(testStepName, testStepDescription, startTime, LocalDateTime.now(),
				status, "",expectedResponse,actualResponse,query);
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);


	}
	
	public void logVerificationDetails(WebDriver driver, TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime,String status,String expectedResponse , String actualResponse,String format) throws Exception {
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddVerificationStep(testStepName, testStepDescription, startTime, LocalDateTime.now(),
				status, screenshotCommon.captureScreenShot(driver, testCaseParam.TestCaseName),expectedResponse,actualResponse,format);
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);

	}
	
	public void verifyTableData(WebDriver driver, TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime, String status, String[][]expectedTableData , String[][] actualTableData) throws Exception
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.VerifyTableData(testStepName, testStepDescription, startTime, LocalDateTime.now(),
				status, screenshotCommon.captureScreenShot(driver, testCaseParam.TestCaseName),expectedTableData,actualTableData);
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);

	}

	
	public void logTableData(WebDriver driver, TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime,String status,String[][]stepTableData) throws Exception
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddTableData(testStepName, testStepDescription, startTime, LocalDateTime.now(),
				status, screenshotCommon.captureScreenShot(driver, testCaseParam.TestCaseName),stepTableData);
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);

	}


	
	public void logScreenDetails(TestCaseParam testCaseParam, String screenName)
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddScreenData(screenName);
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);
	}

	public void logModuleDetails( TestCaseParam testCaseParam, String moduleName)
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddModuleData(moduleName);
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);


	}
	
	
	
	public void logPrereqDetails( TestCaseParam testCaseParam, String tcPrereq)
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddMultiTCData(tcPrereq);
		extentUtilities.Log(testCaseParam, testStepModel);

	}

	
	public void logModuleAndScreenDetails( TestCaseParam testCaseParam, String moduleName, String screenname)
	{
		TestStepModel testStepModel = new TestStepModel();
		testStepModel=testStepModel.AddModuleScreenData(moduleName,screenname);
		TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
		extentUtilities.Log(temptestCaseParam, testStepModel);

	}
	
	public void logPDFDetails(TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime,String status, String results)
	{
			TestStepModel testStepModel = new TestStepModel();
			testStepModel=testStepModel.AddVerificationStepPDF(testStepName, testStepDescription, startTime, LocalDateTime.now(),
					status,results);
			TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
			extentUtilities.Logpdf(temptestCaseParam, testStepModel);


		}
	public void logPDFDetailsPass(TestCaseParam testCaseParam, String testStepName, String testStepDescription, LocalDateTime startTime,String status)
	{
			TestStepModel testStepModel = new TestStepModel();
			testStepModel=testStepModel.AddVerificationStepPDFPass(testStepName, testStepDescription, startTime, LocalDateTime.now(),
					status);
			TestCaseParam temptestCaseParam=getTempTestCaseParam(testCaseParam);
			extentUtilities.Logpdf(temptestCaseParam, testStepModel);


		}

	
	
	
	public TestCaseParam getTempTestCaseParam(TestCaseParam testCaseParam)
	{
		TestCaseParam temptestCaseParam= new TestCaseParam();
		temptestCaseParam.TestCaseDescription=testCaseParam.TestCaseDescription;
		temptestCaseParam.TestCaseName=testCaseParam.TestCaseName;
		temptestCaseParam.Browser=testCaseParam.Browser;
		temptestCaseParam.Iteration=testCaseParam.Iteration;
		temptestCaseParam.ModuleName=testCaseParam.ModuleName;
		temptestCaseParam.testCaseType=testCaseParam.testCaseType;
		
		
			if(temptestCaseParam.testCaseType==TestCaseParam.TestCaseType.Prereq) 
			{
				temptestCaseParam.TestCaseName=testCaseParam.TestCaseName.replace("_Prereq", "");
			}
			
			return temptestCaseParam;
	}

	public void calculateTestCaseResults(ArrayList<HashMap<UUID, TestCaseDetails>> testCaseRepository) throws Exception
	{
		try
		{
		if (testCaseRepository != null)
		{

			for (HashMap<UUID,TestCaseDetails> DictTC : testCaseRepository)
			{
				TestCaseResult testCaseResult= new TestCaseResult();
				
				
				TestCaseDetails testCaseDetails = DictTC.values().stream().findFirst().get();
				testCaseResult.TestCaseName = testCaseDetails.TestCaseName;
				testCaseResult.TestCaseDescription = testCaseDetails.TestCaseDescription;
				testCaseResult.Module = testCaseDetails.Module;
				testCaseResult.Browser = testCaseDetails.Browser;
				testCaseResult.TestCaseCategory=testCaseDetails.TestCaseCategory;
				testCaseResult.CaseNumber=testCaseDetails.CaseNumber;
				testCaseResult.ApplicationNumber=testCaseDetails.ApplicationNumber;

				ArrayList<TestStepDetails> testStepDetails = testCaseDetails.stepDetails;
				


				boolean tcfailed=testStepDetails.stream().anyMatch(x->x.ExtentStatus.name().toUpperCase().contains("FAIL"));
				

				if(tcfailed)
				{
					testCaseResult.TestCaseStatus="Fail";
				}
				else
				{
					testCaseResult.TestCaseStatus="Pass";					
				}

				testCaseResult.StartTime =testStepDetails.get(0).StartTime;
				testCaseResult.EndTime  =testStepDetails.get(testStepDetails.size()-1).EndTime;
				

				long totalSeconds = ChronoUnit.SECONDS.between(testCaseResult.StartTime.toLocalTime(), testCaseResult.EndTime.toLocalTime());
				int totalSecs=(int)totalSeconds;
				
				
				int hours = totalSecs / 3600;
				int minutes = (totalSecs % 3600) / 60;
				int seconds = totalSecs % 60;
				
				String strhours = String.valueOf(hours);
				String strminutes = String.valueOf(minutes);
				String strseconds = String.valueOf(seconds);

				if(strhours.length()==1)
				{
					strhours="0"+strhours;
				}

				if(strminutes.length()==1)
				{
					strminutes="0"+strminutes;
				}

				if(strseconds.length()==1)
				{
					strseconds="0"+strseconds;
				}


				testCaseResult.Duration=strhours +":" + strminutes+ ":" + strseconds;
				ReportContants.testcaseResults.add(testCaseResult);
			}
		}
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			logger.info(Arrays.toString(e.getStackTrace()));
			throw e;	
		}
	
	
	}
	
	
	public void calculateModuleResults()
	{
		ModuleResult moduleResult= new ModuleResult();
		Map<String, List<TestCaseResult>> moduleStatus =
				ReportContants.testcaseResults.stream().
				collect(Collectors.groupingBy(TestCaseResult::getModule));

for (Map.Entry<String, List<TestCaseResult>> entry : moduleStatus.entrySet()) {
int total = entry.getValue().size();
long passCount = entry.getValue().stream().filter(e -> e.TestCaseStatus.equalsIgnoreCase("Pass")).count();
long failCount = entry.getValue().stream().filter(e -> e.TestCaseStatus.equalsIgnoreCase("Fail")).count();
moduleResult.Module=entry.getKey();
moduleResult.TCTotalCount= total;
moduleResult.TCPassCount=(int) passCount;
moduleResult.TCFailCount=(int) failCount;
moduleResult.TCSkippedCount=(int) (total-(passCount+failCount));
ReportContants.moduleResults.add(moduleResult);

}
}

	
	public void calculateBrowserResults()
	{
		BrowserResult browserResult= new BrowserResult();
		Map<String, List<TestCaseResult>> browserStatus = 
				ReportContants.testcaseResults.stream().
				collect(Collectors.groupingBy(TestCaseResult::getBrowser));

for (Map.Entry<String, List<TestCaseResult>> entry : browserStatus.entrySet()) {
int total = entry.getValue().size();
long passCount = entry.getValue().stream().filter(e -> e.TestCaseStatus.equalsIgnoreCase("Pass")).count();
long failCount = entry.getValue().stream().filter(e -> e.TestCaseStatus.equalsIgnoreCase("Fail")).count();
browserResult.Browser=entry.getKey();
browserResult.TCTotalCount= total;
browserResult.TCPassCount=(int) passCount;
browserResult.TCFailCount=(int) failCount;
browserResult.TCSkippedCount=(int) (total-(passCount+failCount));
ReportContants.browserResults.add(browserResult);

}
}

public String getActionName(String pageStepName, String actionName)
{
	
	if(pageStepName.equals(""))
	{
		return actionName;
	}
	else
	{
		return pageStepName + "  ====>  "+ actionName;
	}
}


public String getActionDesc(String pageStepDesc, String actionDesc)
{
	
	if(pageStepDesc.equals(""))
	{
		return actionDesc;
	}
	else
	{
		return pageStepDesc + "  ====>  "+ actionDesc;
	}
}

public String getDuration(LocalDateTime startTime, LocalDateTime endTime)
{
	int totalSecs = endTime.getSecond() - startTime.getSecond();
	
	
	int hours = totalSecs / 3600;
	int minutes = (totalSecs % 3600) / 60;
	int seconds = totalSecs % 60;
	
	String strhours = String.valueOf(hours);
	String strminutes = String.valueOf(minutes);
	String strseconds = String.valueOf(seconds);

	if(strhours.length()==1)
	{
		strhours="0"+strhours;
	}

	if(strminutes.length()==1)
	{
		strminutes="0"+strminutes;
	}

	if(strseconds.length()==1)
	{
		strseconds="0"+strseconds;
	}


	return strhours +":" + strminutes+ ":" + strseconds;

}

public  String convertLocalDateTimetoSQLDateTime(LocalDateTime localdateTime)
{
	DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

	return localdateTime.format(formatter);
	

}

public String getTimeDifference(LocalDateTime startTime, LocalDateTime endTime)



{

Duration duration = Duration.between(startTime, endTime);

long seconds = duration.getSeconds();



	return String.format("%02d:%02d:%02d", seconds / 3600, (seconds % 3600) / 60, seconds % 60);



}

}

