package report_utilities.extent_model;

import com.fasterxml.jackson.core.exc.StreamWriteException;
import com.fasterxml.jackson.databind.DatabindException;

import report_utilities.constants.ReportContants;
import report_utilities.extent_report.ExtentReport;
import report_utilities.model.TestCaseParam;
import report_utilities.model.TestStepModel;
import report_utilities.html_model.TestManager;
import report_utilities.html_model.TestStep;
import report_utilities.html_report.HTMLReports;

import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public class ExtentUtilities
{
	public TestCaseDetails initializeNewTestCase(String[] testCaseDetailsArray, String module,String testCaseCategory,String caseNumber, String applicationNumber, String browser, int... iteration)
	{
		// Unpack the test case name and description from the array
		String testcasename = testCaseDetailsArray[0];
		String testcasedescription = testCaseDetailsArray[1];

		Map<UUID, TestCaseDetails> tcDetails;


		LocalDateTime starttime =LocalDateTime.now();
		TestCaseDetails testCaseDetails = new TestCaseDetails();


		if(iteration.length>0)
		{
			tcDetails = testCaseDetails.addNewTestCase(testcasename,testcasename + "_" + module + "_" + browser, testcasedescription,
			module, browser,testCaseCategory,caseNumber,applicationNumber, starttime, iteration[0]);

		}
		else
		{

			tcDetails = testCaseDetails.addNewTestCase(testcasename,testcasename + "_" + module + "_" + browser, testcasedescription,
			module, browser,testCaseCategory,caseNumber,applicationNumber, starttime);
			ExtentReport extentReport = new ExtentReport();
			extentReport.startTest(testcasename, module, browser, testcasedescription);

		}
		
		
		
		
		HTMLReports htmlReports = new HTMLReports();
		String tcLiveFilePath=htmlReports.createTCHTML(testcasename, module, browser);
		
	  
		htmlReports.createTCHTMLLive(testcasename, module, browser,ReportContants.getStatusInProgress(), tcLiveFilePath);
		htmlReports.createDashboardHTMLLiveSummary();

	
		TestManager.addTestCase(testcasename, module, browser);
		
		TestRunDetails.testCaseRepository.add(tcDetails);
		HashMap<String, UUID> tcMapping = new HashMap<>();
	
		tcMapping.put(tcDetails.get(tcDetails.entrySet().stream().findFirst().get().getKey()).testCaseFullName, tcDetails.entrySet().stream().findFirst().get().getKey());
		TestRunDetails.testCaseMapping.add(tcMapping);
		return testCaseDetails;
	}


	public UUID getTestCaseID(String testCaseName, String module, String browser)
	{

		for (HashMap<String, UUID> DictKey : TestRunDetails.testCaseMapping)
		{
			if (DictKey.containsKey(testCaseName + "_" + module + "_" + browser))
			{
				return DictKey.get(testCaseName + "_" + module + "_" + browser);
			}
		}

		return null;
	}

	public void endTestCase(String testCaseName, String module, String browser)
	{
		HTMLReports htmlReports = new HTMLReports();
		htmlReports.updateTCHTMLLive(testCaseName, module, browser, ReportContants.getStatusCompleted());
	}

	public void log(TestCaseParam testCaseParam, TestStepModel testStepModel) {
		String browser = testCaseParam.getBrowser();
		UUID tcId = getTestCaseID(testCaseParam.getTestCaseName(), testCaseParam.getModuleName(), browser);

		TestStepDetails tsDetail = new TestStepDetails()
				.addTestStepDetails(testStepModel.getTestStepName(),
						testStepModel.getTestStepDescription(), testStepModel.getStartTime(),
						testStepModel.getEndTime(), testStepModel.getTestStepStatus(),
						testStepModel.getScreenShotData(), testStepModel.getErrorMessage(),
						testStepModel.getErrorDetails(), testStepModel.getExpectedResponse(),
						testStepModel.getActualResponse(), testStepModel.getModuleName(),
						testStepModel.getScreenName(), testStepModel.getTestStepType(),
						testStepModel.getTestStepFormat());

		TestRunDetails.testCaseRepository.stream()
				.filter(x -> x.containsKey(tcId))
				.findFirst().get().get(tcId).getStepDetails().add(tsDetail);
		TestRunDetails.testCaseRepository.stream()
				.filter(x -> x.containsKey(tcId))
				.findFirst().get().get(tcId).browser = testCaseParam.getBrowser();

		ExtentReport extentReport = new ExtentReport();
		extentReport.addTestStepsLogs(testCaseParam, tsDetail);

		HTMLReports htmlReports = new HTMLReports();
		htmlReports.addTestStepsLogs(testCaseParam, tsDetail);

		try {
			DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

			String startTime = tsDetail.startTime.format(formatter);
			String endTime = tsDetail.endTime.format(formatter);

			String tsStatus = tsDetail.extentStatus.toString().toUpperCase();
			TestStep testStep = new TestStep();
			testStep = testStep.addTestStep(tsDetail.stepName, tsStatus, startTime, endTime, tsDetail.duration);

			TestManager.addTestStep(testCaseParam.getTestCaseName(), testCaseParam.getModuleName(), testCaseParam.getBrowser(), testStep);

		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	public List<Map<UUID, TestCaseDetails>> getTestRunDetails()
	{

		return TestRunDetails.testCaseRepository;
	}
	
	public void logPdf(TestCaseParam testCaseParam,TestStepModel testStepModel)
	{
		String browser = testCaseParam.getBrowser();
		UUID tcId = getTestCaseID(testCaseParam.getTestCaseName(), testCaseParam.getModuleName(), browser);


		TestStepDetails tsDetail= new  TestStepDetails().
				addTestStepDetailsPdf(testStepModel.getTestStepName(),
				  testStepModel.getTestStepDescription(), testStepModel.getStartTime(),
				  testStepModel.getEndTime(), testStepModel.getTestStepStatus(),
				 testStepModel.getErrorMessage());	
		TestRunDetails.testCaseRepository.stream().filter(x->x.containsKey(tcId)).findFirst().get().get(tcId).getStepDetails().add(tsDetail);
		TestRunDetails.testCaseRepository.stream().filter(x->x.containsKey(tcId)).findFirst().get().get(tcId).browser=testCaseParam.getBrowser();
		}
	
	

}
