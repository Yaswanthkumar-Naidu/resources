package report_utilities.ExtentReport;


import report_utilities.Model.InterfaceTCDetails;
import report_utilities.Model.InterfaceTestRun;
import report_utilities.Model.TestCaseParam;
import report_utilities.Model.ExtentModel.TestCaseDetails;
import report_utilities.Model.ExtentModel.TestStepDetails;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.CodeLanguage;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.model.ExceptionInfo;
import com.aventstack.extentreports.reporter.ExtentHtmlReporter;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.UUID;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ExtentReport
{

	public static ExtentHtmlReporter htmlReporter;
	public static ExtentReports extent;

	public static HashMap<Integer, ExtentTest> dictExtentTestScenario = new HashMap<>();
	public static HashMap<Integer, String> dictExtentTestCase = new HashMap<>();
	public static HashMap<Integer, ArrayList<ExtentTestSteps>> dictExtentTestSteps = new HashMap<>();
	final Logger logger =LoggerFactory.getLogger(ExtentReport.class.getName()); 

//added for live report
	public static HashMap<String, ExtentTest> tcExtentMapping = new HashMap<>();	
	public void startReport(String reportPath, String hostName, String environment, String userName)
	{
		try
		{
			reportPath = reportPath + "/ExtentReport_RunTime.html";
	
	
			
			htmlReporter = new ExtentHtmlReporter(reportPath);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("Host Name", hostName);
			extent.setSystemInfo("Environment", environment);
			extent.setSystemInfo("Username", userName);
		}
		catch(Exception e)
		{
			logger.info("Unable to initialize the Extent Report");
			e.printStackTrace();
			logger.info(e.getMessage());
			
			
			
		}
		
		
		}
	
	public void startReport(String reportPath, String environment)
	{
		try
		{
			reportPath = reportPath + "/ExtentReport_RunTime.html";
	
	
			
			htmlReporter = new ExtentHtmlReporter(reportPath);
			extent = new ExtentReports();
			extent.attachReporter(htmlReporter);
			extent.setSystemInfo("Environment", environment);
		}
		catch(Exception e)
		{
			logger.info("Unable to initialize the Extent Report");
			e.printStackTrace();
			logger.info(e.getMessage());
			
			
			
		}
		
		
		}
	public void startTest(String testCase,String moduleName, String browser, String testcaseDescription)
	{
		try
		{
		
		ExtentTest tc = extent.createTest(testCase, testcaseDescription);
		ExtentTest test =tc.createNode("Iteration==>1");
		test.assignCategory(browser);
		test.assignCategory(moduleName);
		Thread.sleep(0);
		tcExtentMapping.put(testCase+"_"+ moduleName+ "_" + browser, test);

		}
	catch(Exception e)
	{
		logger.info("Unable to initialize the Extent Test Case ==> {} " , testCase );
		logger.info(Arrays.toString(e.getStackTrace()));
		logger.info(e.getMessage());
		
		
		
	}

	
	}


	
	public void startTest(String testCase,String moduleName, String browser, int iteration,String testcaseDescription)
	{
		
		try
		{
		ExtentTest test;
		
		if(iteration>1)
		{
			ExtentTest tc =tcExtentMapping.get(testCase+"_"+ moduleName+ "_" + browser);
		
            test =tc.createNode("Iteration==>"+iteration);
            tcExtentMapping.put(testCase+"_"+ moduleName+ "_" + browser, test);
		}
		else
		{
			ExtentTest tc = extent.createTest(testCase, testcaseDescription);
		    test =tc.createNode("Iteration==>1");	
		
			test.assignCategory(browser);
			test.assignCategory(moduleName);
	
		}
		
		tcExtentMapping.put(testCase+"_"+ moduleName+ "_" + browser, test);
		}
		catch(Exception e)
		{
			logger.info("Unable to initialize the Extent Test Case ==> {} ", testCase );
			logger.info(Arrays.toString(e.getStackTrace()));
			logger.info(e.getMessage());
			
			
			
		}

	}
	
	public void addTestStepsLogs(TestCaseParam testCaseParam, TestStepDetails tsDetails)
	{

		String businessArea="";
		String screenName="";
		try
		{
		ExtentTest test=tcExtentMapping.get(testCaseParam.TestCaseName  +"_"+ testCaseParam.ModuleName + "_" + testCaseParam.Browser);
		

		
		if((tsDetails.testStepType==TestStepDetails.TestStepType.TestStep)||
				(tsDetails.testStepType==TestStepDetails.TestStepType.Verification)||
				(tsDetails.testStepType==TestStepDetails.TestStepType.Exception))
		{	
			if (tsDetails.ScreenShotData.equals(""))
			{
				test.log(tsDetails.ExtentStatus, tsDetails.StepName);
				
			}
			else
			{
				test.log(tsDetails.ExtentStatus,writeImageToReport(tsDetails.ScreenShotData, tsDetails.StepName));
			}
		}
		
		if(tsDetails.testStepType==TestStepDetails.TestStepType.Module)
		{
				test.log(Status.INFO, "=================Module: " + tsDetails.ModuleName + "==============");
		}
		
		if(tsDetails.testStepType==TestStepDetails.TestStepType.Screen)
		{
				test.log(Status.INFO, "=================Screen: " + tsDetails.ScreenName + "==============");
		}
		
		if(tsDetails.testStepType==TestStepDetails.TestStepType.Module_Screen)
		{
				test.log(Status.INFO, "=================Module: " + tsDetails.ModuleName + "==============");
				test.log(Status.INFO, "=================Screen: " + tsDetails.ScreenName + "==============");
		}
		
		

		}
		catch(Exception e)
		{
			logger.info("Unable to update the Extent Test Step ==> {} ", tsDetails.StepName );
			logger.info(Arrays.toString(e.getStackTrace()));
			logger.info(e.getMessage());
			
			
			
		}



	}

	//live report end


	public void startReport(String reportPath, String hostName, String environment, String userName, int... node)
	{
		if(node==null)
			node[0]=0;
		reportPath = reportPath + "../TestReports/ExtentReport.html";
		htmlReporter = new ExtentHtmlReporter(reportPath);
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", hostName);
		extent.setSystemInfo("Environment", environment);
		extent.setSystemInfo("Username", userName);
	}


	public void startTest(String testScenarioName, String testScenarioDescription, int... node)
	{
		if(node==null)
			node[0]=0;
		ExtentTest test = extent.createTest(testScenarioName, testScenarioDescription);
	}

	public void endTest(String testcaseName, String testcaseDesc, String moduleName, int... node)
	{
		if(node==null)
			node[0]=0;
		int failCounter = 0;

		if (dictExtentTestScenario.containsKey(node))
		{
			ExtentTest testScenario = dictExtentTestScenario.get(node[0]);
			ExtentTest  extentNode = testScenario.createNode(testcaseName, testcaseDesc);

			ArrayList<ExtentTestSteps> arrayListTestSteps = dictExtentTestSteps.get(node[0]);

			for (ExtentTestSteps ETS : arrayListTestSteps)
			{
				if (ETS.testStepStatus == ExtentConstants.TestStepStatus.PASS)
				{
					extentNode.createNode(ETS.testStepName, ETS.testStepDesc).pass(ETS.testStepName + "--Passed");
				}

				else if (ETS.testStepStatus == ExtentConstants.TestStepStatus.FAIL)
				{
					extentNode.createNode(ETS.testStepName, ETS.testStepDesc).fail(ETS.testStepName + "--Failed");
					failCounter++;
				}

				else
				{
					extentNode.createNode(ETS.testStepName, ETS.testStepDesc).skip(ETS.testStepName + "--Skipped");
				}
			}

			if (failCounter > 0)
			{
				extentNode.fail(testcaseName + "--Fail");
			}
			else
			{
				extentNode.pass(testcaseName + "--Pass");
			}


			extentNode.assignCategory(moduleName);

			if (node[0] > 0)
			{
				extentNode.assignDevice("Node -- " + node[0]);
			}

		}
	}
	
	
	
	public void endReport()

	{

		//End Report

		extent.flush();

	}

	public void addTestStepsLogs(String testStepName, String testStepDesc, ExtentConstants.TestStepStatus testStepStatus, int... node)
	{
		if(node==null)
			node[0]=0;

		
		ExtentTestSteps extentTestSteps = new ExtentTestSteps();
		if (dictExtentTestSteps.containsKey(node))
		{
			ArrayList<ExtentTestSteps> arrayListTestSteps = dictExtentTestSteps.get(node);
			extentTestSteps.testStepName = testStepName;
			extentTestSteps.testStepDesc = testStepDesc;
			extentTestSteps.testStepStatus = testStepStatus;

			arrayListTestSteps.add(extentTestSteps);
			dictExtentTestSteps.putIfAbsent( node[0] , arrayListTestSteps);
		}
		else
		{
			ArrayList<ExtentTestSteps> arrayListTestSteps = new ArrayList<>();
			extentTestSteps.testStepName = testStepName;
			extentTestSteps.testStepDesc = testStepDesc;
			extentTestSteps.testStepStatus = testStepStatus;

			arrayListTestSteps.add(extentTestSteps);
			dictExtentTestSteps.putIfAbsent(node[0], arrayListTestSteps);
		}

	}

	public void createExtentReportFromModel(String reportPath, InterfaceTestRun model,
		String testName,String environment,String executedBy, String requestPath,
		String responsePath,boolean shareAttachmentForPassedTestCases)
	{

		reportPath = reportPath + "/ExtentReport_Interface.html";
		ExtentHtmlReporter htmlReporteICI = new ExtentHtmlReporter(reportPath);
		ExtentReports extentlCl = new ExtentReports();
		extentlCl.attachReporter(htmlReporteICI);
		extentlCl.setSystemInfo("Host Name", testName);
		extentlCl.setSystemInfo("Environment", environment);
		extentlCl.setSystemInfo("Username", executedBy);

		if (model != null)
		{
			for (InterfaceTCDetails node : model.interfaceTCDetails )
			{
				String testcaseName = "";
				String requestFileName = "";
				String responseFileName = "";
				if (node.iteration > 1)
				{
					testcaseName = node.module + "==>" + node.interfaceName + "==> Iteration: " + node.iteration;
					requestFileName="Request_"+ node.module + "_" + node.interfaceName + "_" + node.iteration;
					responseFileName = "Response_" + node.module + "_" + node.interfaceName + "_" + node.iteration;
				}
				else
				{
					testcaseName = node.module + "==>" + node.interfaceName;
					requestFileName = "Request_" + node.module + "_" + node.interfaceName;
					responseFileName = "Response_" + node.module + "_" + node.interfaceName;
				}

				ExtentTest test = extentlCl.createTest(node.module+"==>"+ node.interfaceName);

				test.assignCategory(node.module);
				if (node.testCaseStatus == ExtentConstants.TestStepStatus.FAIL)
				{
					test.log(Status.FAIL, testcaseName);
					test.log(Status.INFO, writeDataToTextFile(requestPath, requestFileName,node.requestData,node.fileFormat));
					test.log(Status.INFO, writeDataToTextFile(responsePath, responseFileName, node.responseData, node.fileFormat));
					test.log(Status.ERROR, node.errorMessage);
				}
				else if (node.testCaseStatus == ExtentConstants.TestStepStatus.PASS)
				{
					test.log(Status.PASS, testcaseName);
					if (shareAttachmentForPassedTestCases)
					{
						test.log(Status.INFO, writeDataToTextFile(requestPath, requestFileName, node.requestData, node.fileFormat));
						test.log(Status.INFO, writeDataToTextFile(responsePath, responseFileName, node.responseData, node.fileFormat));
						test.log(Status.PASS, node.errorMessage);
					}
				}
				else 
				{
					test.log(Status.SKIP, testcaseName);

				}
			}
		}
		extentlCl.flush();
	}



	public void createExtentReportCategory(String reportPath, ArrayList<HashMap<UUID, TestCaseDetails>> testcaseRepository,
	String testName, String environment, String executedBy) throws Exception
	{
		
		try
		{

			reportPath = reportPath + "/TestResults_Category.html";
			

		ExtentHtmlReporter htmlReporterICI = new ExtentHtmlReporter(reportPath);
		ExtentReports extentICI = new ExtentReports();
		try
		{
			extentICI.attachReporter(htmlReporterICI);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			logger.info(Arrays.toString(e.getStackTrace()));
			throw e;	
		}
		extentICI.setSystemInfo("Host Name", testName);
		extentICI.setSystemInfo("Environment", environment);
		extentICI.setSystemInfo("Username", executedBy);

		if (testcaseRepository != null)
		{

			for (HashMap<UUID,TestCaseDetails> DictTC : testcaseRepository)
			{
				TestCaseDetails testCaseDetails = DictTC.values().stream().findFirst().get();
				String testcaseName = testCaseDetails.TestCaseName;
				String testcaseDescription = testCaseDetails.TestCaseDescription;
				String moduleName = testCaseDetails.Module;
				String browser = testCaseDetails.Browser;

				ExtentTest test = extentICI.createTest(testcaseName);

				test.assignCategory(moduleName);
				test.assignCategory(browser);

				ArrayList<TestStepDetails> testStepDetails = testCaseDetails.stepDetails;
				ExtentTest multiTCNode = null;
				ExtentTest moduleNode = null;
				ExtentTest ScreenNode = null;
				ExtentTest parentNode=null;

				String businessArea="";
				String screenName="";

				for (TestStepDetails TS : testStepDetails)
				{
					
					if(TS.testStepType==TestStepDetails.TestStepType.MultiTC)
					{
						
						multiTCNode=test.createNode(TS.StepName);	
						parentNode=multiTCNode;
					}
					
					if(TS.testStepType==TestStepDetails.TestStepType.Module)
					{
						if(multiTCNode==null)
						{
							if(!businessArea.equals(TS.ModuleName))
							{
								moduleNode=test.createNode(TS.ModuleName);
								businessArea=TS.ModuleName;		
							}
						}
						else
						{
							if(!businessArea.equals(TS.ModuleName))
							{
							
							moduleNode=multiTCNode.createNode(TS.ModuleName);	
							businessArea=TS.ModuleName;
							}
						}
						
						parentNode=moduleNode;
					}
					else if(TS.testStepType==TestStepDetails.TestStepType.Screen)
					{
						if(moduleNode==null)
						{
							if(!screenName.equals(TS.ScreenName))
							{

								ScreenNode=test.createNode(TS.ScreenName);	
								screenName=TS.ScreenName;

							}
						}
						else
						{
							if(!screenName.equals(TS.ScreenName))
							{
							ScreenNode=moduleNode.createNode(TS.ScreenName);
							screenName=TS.ScreenName;
							}
						}
						
						parentNode=ScreenNode;
					}
					else if(TS.testStepType==TestStepDetails.TestStepType.Module_Screen)
					{
						if(multiTCNode==null)
						{
						
							if(!businessArea.equals(TS.ModuleName))
							{
								moduleNode=test.createNode(TS.ModuleName);	
								businessArea=TS.ModuleName;
							}
							
							if(!screenName.equals(TS.ScreenName))
							{
								ScreenNode=moduleNode.createNode(TS.ScreenName);						
								screenName=TS.ScreenName;
							}

	

						}
						else
						{
							if(!businessArea.equals(TS.ModuleName))
							{
						moduleNode=multiTCNode.createNode(TS.ModuleName);
						businessArea=TS.ModuleName;
							}
							
							if(!screenName.equals(TS.ScreenName))
							{
								ScreenNode=multiTCNode.createNode(TS.ScreenName);
								screenName=TS.ScreenName;
							}
						}
						
						parentNode=ScreenNode;
					}
					
					if(multiTCNode==null &&moduleNode==null && ScreenNode==null )
					{

					parentNode=test;

					}
					
					if(TS.testStepType==TestStepDetails.TestStepType.TestStep)
					{
						if(TS.testStepFormat==TestStepDetails.TestStepFormat.Plain)
						{
	
							if (TS.ScreenShotData.equals(""))
							{
								parentNode.log(TS.ExtentStatus, TS.StepName);
							}
							else
							{
								parentNode.log(TS.ExtentStatus,writeImageToReport(TS.ScreenShotData, TS.StepName));
							}		
						}
						else if(TS.testStepFormat==TestStepDetails.TestStepFormat.HTML)
						{
							
							parentNode.log(TS.ExtentStatus ,MarkupHelper.createCodeBlock(TS.StepDescription));
						}
						
						else if(TS.testStepFormat==TestStepDetails.TestStepFormat.XML)
						{
							
							parentNode.log(TS.ExtentStatus ,MarkupHelper.createCodeBlock(TS.StepDescription, CodeLanguage.XML));
						}
						else if(TS.testStepFormat==TestStepDetails.TestStepFormat.JSON)
						{
							
							parentNode.log(TS.ExtentStatus ,MarkupHelper.createCodeBlock(TS.StepDescription, CodeLanguage.JSON));
						}
						else if(TS.testStepFormat==TestStepDetails.TestStepFormat.Code)
						{
							
							parentNode.log(TS.ExtentStatus ,MarkupHelper.createCodeBlock(TS.StepDescription));
						}
						
					}
					else if(TS.testStepType==TestStepDetails.TestStepType.Verification)
					{
						
						
						if(TS.testStepFormat==TestStepDetails.TestStepFormat.XML)
						{

							parentNode.log(TS.ExtentStatus ,TS.StepName);
							ExtentTest xmlChild = parentNode.createNode(TS.StepName);
							xmlChild.info(MarkupHelper.createCodeBlock(TS.ExpectedResponse, CodeLanguage.XML));
							xmlChild.log(TS.ExtentStatus, MarkupHelper.createCodeBlock(TS.ActualResponse, CodeLanguage.XML));
						}
						else if(TS.testStepFormat==TestStepDetails.TestStepFormat.JSON)
						{
							
							parentNode.log(TS.ExtentStatus ,TS.StepName);
							ExtentTest xmlChild = parentNode.createNode(TS.StepName);
							xmlChild.info(MarkupHelper.createCodeBlock(TS.ExpectedResponse, CodeLanguage.JSON));
							xmlChild.log(TS.ExtentStatus, MarkupHelper.createCodeBlock(TS.ActualResponse, CodeLanguage.JSON));
						}

						else
						{
							if(TS.ExtentStatus== Status.PASS)
							{
								parentNode.pass(MarkupHelper.createLabel( " Expected : " + TS.ExpectedResponse+ "\n   ==> Actual : " + TS.ActualResponse , ExtentColor.GREEN));
							}
							else
							{
								parentNode.fail(MarkupHelper.createLabel( " Expected : " + TS.ExpectedResponse+ "   \n ==> Actual : " + TS.ActualResponse , ExtentColor.RED));
							}
						}
					}
					else if(TS.testStepType==TestStepDetails.TestStepType.Exception)
					{
						ExceptionInfo ei = new ExceptionInfo();
						ei.setExceptionName(TS.ErrorMessage);
						ei.setStackTrace(TS.ErrorDetails);
						parentNode.log(TS.ExtentStatus, ei);
						parentNode.log(TS.ExtentStatus,writeImageToReport(TS.ScreenShotData, TS.StepName));
					}
					
				}


			}
		}
		extentICI.flush();


		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			logger.info(Arrays.toString(e.getStackTrace()));
			throw e;	
		}
	}

	
	
	public void createExtentReportModel(String reportPath, ArrayList<HashMap<UUID, TestCaseDetails>> testCaseRepository,
	String testName, String environment, String executedBy) throws Exception
	{
		
		try
		{

			reportPath = reportPath + "/TestResults.html";
			

		ExtentHtmlReporter htmlReporterlcl = new ExtentHtmlReporter(reportPath);
		ExtentReports extentlcl = new ExtentReports();
		try
		{
			extentlcl.attachReporter(htmlReporterlcl);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			logger.info(Arrays.toString(e.getStackTrace()));
			throw e;	
		}
		extentlcl.setSystemInfo("Host Name", testName);
		extentlcl.setSystemInfo("Environment", environment);
		extentlcl.setSystemInfo("Username", executedBy);

		if (testCaseRepository != null)
		{

			for (HashMap<UUID,TestCaseDetails> DictTC : testCaseRepository)
			{
				TestCaseDetails testCaseDetails = DictTC.values().stream().findFirst().get();
				String testcaseName = testCaseDetails.TestCaseName;
				String testcaseDescription = testCaseDetails.TestCaseDescription;
				String moduleName = testCaseDetails.Module;
				String browser = testCaseDetails.Browser;

				ExtentTest test = extentlcl.createTest(testcaseName);

				test.assignCategory(moduleName);
				test.assignCategory(browser);

				ArrayList<TestStepDetails> testStepDetails = testCaseDetails.stepDetails;

				String businessArea="";
				String screenName="";
				for (TestStepDetails TS : testStepDetails)
				{
					if((TS.testStepType==TestStepDetails.TestStepType.TestStep)||
							(TS.testStepType==TestStepDetails.TestStepType.Verification)||
							(TS.testStepType==TestStepDetails.TestStepType.Exception))
					{	
						if (TS.ScreenShotData.equals(""))
						{
							test.log(TS.ExtentStatus, TS.StepName);
							
						}
						else
						{
							test.log(TS.ExtentStatus,writeImageToReport(TS.ScreenShotData, TS.StepName));
						}
					}
					
					if(TS.testStepType==TestStepDetails.TestStepType.Module)
					{
						if(!businessArea.equals(TS.ModuleName))
						{
							businessArea=TS.ModuleName;
							test.log(Status.INFO, "=================Module: " + businessArea + "==============");
						}
					}
					
					if(TS.testStepType==TestStepDetails.TestStepType.Screen)
					{
						if(!screenName.equals(TS.ScreenName))
						{
							screenName=TS.ScreenName;
							test.log(Status.INFO, "=================Screen: " + screenName + "==============");
						}
					}
					
					if(TS.testStepType==TestStepDetails.TestStepType.Module_Screen)
					{
						if(!businessArea.equals(TS.ModuleName))
						{
							businessArea=TS.ModuleName;
							test.log(Status.INFO, "=================Module: " + businessArea + "==============");
						}
						
						if(!screenName.equals(TS.ScreenName))
						{
							screenName=TS.ScreenName;
							test.log(Status.INFO, "=================Screen: " + screenName + "==============");
						}
					}
					
					
					
				}


			}
		}
		extentlcl.flush();
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			logger.info(Arrays.toString(e.getStackTrace()));
			throw e;	
		}
	}


	public void addScreenShotDetails(TestStepDetails ts,ExtentTest node )
	{
		if (ts.ScreenShotData.equals(""))
		{
			node.log(ts.ExtentStatus, ts.StepName);
		}
		else
		{
			node.log(ts.ExtentStatus,writeImageToReport(ts.ScreenShotData, ts.StepName));
		}		
	}

	public String writeDataToTextFile(String filePath, String fileName,String fileContent,String fileFormat)
	{
		filePath = filePath + File.separator + fileName + fileFormat;
		try(BufferedWriter writer = new BufferedWriter(new FileWriter(filePath)))
		{
		    writer.write(fileContent);
			    
			writer.close();

			filePath= filePath.replace("\\","/");

			filePath = "<a href = 'file:///"+ filePath + "'>"+ fileName + "</a>";
			return filePath;
		}
		
		catch (Exception e)
		{
			return filePath;
		}
	}

	public String writeImageToReport(String filePath, String fileName)
	{
	
		try
		{
			filePath = filePath.replace("\\", "/");
			filePath = "<a href = 'file:///" + filePath + "'>" + fileName + "</a>";
			return filePath;
		}

		catch (Exception e)
		{
			return filePath;
		}
	}

}
