package testsettings;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import report_utilities.Model.TestCaseParam;


public class SessionData {
	
	private SessionData() {
		
	}


    private static String timeTravelDate= "";
	private static String fIRSTDAYOFMONTH="";
	private static String lastDayOfMonth="";
	private static String firstDayOfpreviousMonth="";
	private static String lastDayOfpreviousMonth="";
	private static String firstDayOfNextMonth="";
	private static String lastDatOfNextMonth="";
	private static String firstDAYOFCUSTOMMONTH = "";
	static final String LASTDAYOFFUTURECUSTOMMONTH="";
	static final String FIRSTDAYOFPOSTCUSTOMMONTH="";
	static final String LASTDAYOFPASTCUSTOMMONTH="";												

	
	static final String CASENUMBER="";
	private static String randomdata="";
	private static String pNpReferralCode="";
	private static String copayamount="";
	static final String AMOUNT="";	
	private static String calculationid="";

	private static String taskId="";
	protected static Map<String,String> dbPlaceHolder= new HashMap<>();
	protected static Map<String,String> sessionKeys= new HashMap<>();
	protected static Map<String, ArrayList<String>> loginData = new HashMap<>();

	static final String LOGGINGCONNECTIONSTRING="";
	static final String LOGGINGTABLENAME="";
	static final String APPLICATIONNAME="";
	
	public static final String DATEPATTERN = "yyyy-MM-dd";	
	
	public static void setDBPlaceHolderData(String dBKey, String dBValue, TestCaseParam testCaseParam)
	{
		dbPlaceHolder.put(testCaseParam.TestCaseName +"_"+ testCaseParam.ModuleName+"_"+ testCaseParam.Browser + "_"+ testCaseParam.Iteration+"_"+dBKey,dBValue);

	}
	

	public static String getDBPlaceHolderData(String dBKey, TestCaseParam testCaseParam)
	{
		return dbPlaceHolder.get(testCaseParam.TestCaseName +"_"+ testCaseParam.ModuleName+"_"+ testCaseParam.Browser + "_"+ testCaseParam.Iteration+"_"+dBKey);

	}

	
	public static void setSessionData(TestCaseParam testCaseParam,String key, String value)
	{
		sessionKeys.put(testCaseParam.TestCaseName +"_"+ testCaseParam.ModuleName+"_"+ testCaseParam.Browser + "_"+ testCaseParam.Iteration+"_"+key,value);
	}
	

	public static String getSessionData(TestCaseParam testCaseParam,String key)
	{
		return sessionKeys.get(testCaseParam.TestCaseName +"_"+ testCaseParam.ModuleName+"_"+ testCaseParam.Browser + "_"+ testCaseParam.Iteration+"_"+key);		
	}
	
	public static String replaceSessionData(TestCaseParam testCaseParam,String key)
	{
		
		for(String s : sessionKeys.keySet())
		{
			s= s.replace(testCaseParam.TestCaseName +"_"+ testCaseParam.ModuleName+"_"+ testCaseParam.Browser + "_"+ testCaseParam.Iteration+"_", "");
			if(key.contains(s))
			{
				key=key.replace(s, sessionKeys.get(testCaseParam.TestCaseName +"_"+ testCaseParam.ModuleName+"_"+ testCaseParam.Browser + "_"+ testCaseParam.Iteration+"_"+s));
			}
		}
		return key;		
	}
	
	
	
	
	public static boolean sessionDataContainsKey(TestCaseParam testCaseParam, String key) {
	    String compositeKey = testCaseParam.TestCaseName + "_" + testCaseParam.ModuleName + "_" + testCaseParam.Browser + "_" + testCaseParam.Iteration + "_" + key;
	    return sessionKeys.containsKey(compositeKey);
	}

	
	public static String getLoginData(String key)
	{
		return loginData.get(key).get(0);
	}
	
	
	public static String getTimeTravelDate() 
	{
		return timeTravelDate;
	}
	
	public static void setTimeTravelDate(String ttdate) 
	{
		timeTravelDate=ttdate;
	}
	
	public static String getCalculationID() 
	{
		return calculationid;
	}
	
	public static void setCalculationID(String calcID) 
	{
		calculationid=calcID;
	}
	
	public static String getFirstDayofCurrentMonth() 
	{
		return fIRSTDAYOFMONTH;
	}
	
	public static String getTaskId() 
	{
		return taskId;
	}

	
	public static void setTaskID(String taskID) 
	{
		taskId=taskID;
	}
	
	
	

	public static String getLastDayofCurrentMonth() 
	{
		return lastDayOfMonth;
	}

	
	public static void setFirstDayofCurrentMonth(String incomeDate) 
	{
		fIRSTDAYOFMONTH=incomeDate;
	}
	
	public static String getFirstDayofCustomMonth() 
	{
		return firstDAYOFCUSTOMMONTH;
	}
	public static void setFirstDayofCustomMonth(String incomeDate) 
	{
		firstDAYOFCUSTOMMONTH=incomeDate;
	}
	
	public static String getFirstDayofPreviousMonth() 
	{
		return firstDayOfpreviousMonth;
	}

	
	public static String getLastDayofPreviousMonth() 
	{
		return lastDayOfpreviousMonth;
	}

	public static void setFirstDayofPreviousMonth(String incomeDate) 
	{
		firstDayOfpreviousMonth=incomeDate;
	}
	
	
	public static String getFirstDayofNextMonth() 
	{
		return firstDayOfNextMonth;
	}

	
	public static String getLastDayofNextMonth() 
	{
		return lastDatOfNextMonth;
	}

	public static String getCaseNumber() 
	{
		return CASENUMBER;
	}

		
	public static String increamentDateByMonths(int noOfMonths) 
	{
		LocalDate lDate=LocalDate.parse(timeTravelDate);
		lDate=lDate.plusMonths(noOfMonths);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATEPATTERN);
        return dtf.format(lDate);
	}

	
	public static String increamentDateByDays(int noOfDays) 
	{
		LocalDate lDate=LocalDate.parse(timeTravelDate);
		lDate=lDate.plusDays(noOfDays);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATEPATTERN);
        return dtf.format(lDate);
		
	}
	
	public static String increamentDateByYears(int noOfYears) 
	{
		LocalDate lDate=LocalDate.parse(timeTravelDate);
		lDate=lDate.plusMonths(noOfYears);
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern(DATEPATTERN);
        return dtf.format(lDate);
		
	}
	
	public static String getRandomData() 
	{
		return randomdata;
	}

	
	
	public static void setRandomData(String data) 
	{
		randomdata=data;
	}
	
	
	public static void setPnPReferralCode(String pnpReferralCode) 
	{
		pNpReferralCode=pnpReferralCode;
	}
	public static String getPnPReferralCode() 
	{
		return pNpReferralCode;
	}
	public static void setCoPayAmount(String coPayAmount) 
	{
		copayamount=coPayAmount;
	}
	public static String getCoPayAmount() 
	{
		return copayamount;
	}
	
}