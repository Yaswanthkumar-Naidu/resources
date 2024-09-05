package report_utilities.XMLReports;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

import report_utilities.Model.ExtentModel.TestCaseDetails;

public class PublishXMLReports {


	public void LogJSONResults(ArrayList<HashMap<UUID, TestCaseDetails>> TestCaseRepository,String ReportPath) throws Exception
	{

		ReportPath= ReportPath+"/XMLReports";
		File dir = new File(ReportPath);
        if (!dir.exists()) dir.mkdirs();

		
		if (TestCaseRepository != null)
		{

			for (HashMap<UUID,TestCaseDetails> DictTC : TestCaseRepository)
			{
				TestCaseDetails testCaseDetails = DictTC.values().stream().findFirst().get();
				XMLUtility xmlUtility = new XMLUtility();
				String TCName=testCaseDetails.TestCaseName.replace(" ", "");
				String ModuleName=testCaseDetails.Module.replace(" ", "");
				
				String TCPath=ReportPath+"/"+ TCName+"_"+ModuleName+"_"+testCaseDetails.Browser+ "_"+ testCaseDetails.Iteration + ".xml";

				xmlUtility.DeSerializeTCDataWriteToFile(testCaseDetails, TCPath);
			}
		}
		
		//TestCase Details
		
		
		
	}
	

}
