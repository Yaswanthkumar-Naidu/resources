package report_utilities.JSONReports;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import report_utilities.Model.ExtentModel.TestCaseDetails;

public class PublishJSONReports {



	public void logJSONResults(ArrayList<HashMap<UUID, TestCaseDetails>> testCaseRepository,String reportPath)
	{

	try {
		reportPath= reportPath+"/JSONReports";
		File dir = new File(reportPath);
        if (!dir.exists()) dir.mkdirs();

        
		if (testCaseRepository != null)
		{

			for (HashMap<UUID,TestCaseDetails> DictTC : testCaseRepository)
			{
				TestCaseDetails testCaseDetails = DictTC.values().stream().findFirst().get();
				JSONUtility jsonUtility = new JSONUtility();
				String tcName=testCaseDetails.TestCaseName.replace(" ", "");
				String moduleName=testCaseDetails.Module.replace(" ", "");
			
				String tcPath=reportPath+"/"+ tcName+"_"+moduleName+"_"+testCaseDetails.Browser+ "_"+ testCaseDetails.Iteration + ".json";
				jsonUtility.deSerializeTCDataWriteToFile(testCaseDetails, tcPath);
			}
		}
		
	
	}
		
		
		catch(Exception e) 
		{
		
		}
	
	}
	

}
