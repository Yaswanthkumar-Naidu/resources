package report_utilities.TestResultModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class TestCaseResult {

	public String testcaseName="";
	public String testcaseDescription="";
	public String testcaseCategory="";
	public String module="";
	public String browser="";
	public String testcaseStatus="";
	public LocalDateTime startTime=LocalDateTime.now();
	public LocalDateTime endTime=LocalDateTime.now();
	public String duration="";
	
	//**********Temp Chnages//
	public String caseNumber="";
	public String applicationNumber="";
	
	//****************//
	public String getModule() {
        return module;
    }
	
	public String getBrowser() {
        return browser;
    }
	
	public String getTestCaseStatus() {
        return testcaseStatus;
    }
	
	public String getStartTime()
	{
	    LocalDateTime today = startTime;
	    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:MM:SS.SSS");
	    String date = today.format(format);
	    return date;
	}
	
	public String getEndTime()
	{
	    LocalDateTime today = endTime;
	    DateTimeFormatter format = DateTimeFormatter.ofPattern("MM-dd-yyyy HH:MM:SS.SSS");
	    String date = today.format(format);
	    return date;
	}
}
