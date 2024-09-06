package api_utilities.reports;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.databind.ObjectMapper;

import api_utilities.api_helpers.MSSQLUtilities;
import api_utilities.models.APIReportModel;
import api_utilities.models.DBModel;
import api_utilities.models.EnvModel;
import api_utilities.test_settings.APISessionData;
import api_utilities.test_settings.APITestSettings;


public class DBReport {

public void generateDBReport(String testCase,String module, String apiName,List<APIReportModel> apiReportModels) throws IOException
{

	 ArrayList<String> headers = new ArrayList<>();
     // Add the column names to the headers list
     headers.add("Env");
     headers.add("TestCaseName");
     headers.add("Module");
     headers.add("InterfaceName");
     headers.add("Result");
     headers.add("RequestData");
     headers.add("ResponseData");

     ArrayList<ArrayList<String>> queries = new ArrayList<>();
     // Add the data for each row as an ArrayList of strings
     
     for(APIReportModel apiReportModel: apiReportModels)
     {
     ArrayList<String> rowData = new ArrayList<>();
     // Add the values for the columns in the first row
     rowData.add(APITestSettings.getEnvironment());
     rowData.add(testCase);
     rowData.add(module);
     rowData.add(apiName);
     rowData.add(apiReportModel.getTestStepResult());
     rowData.add(apiReportModel.getRequest());
     apiReportModel.setResponse(sanitizeResponse(apiReportModel.getResponse()));
     rowData.add(apiReportModel.getResponse());
     queries.add(rowData);
     }
	
     
     ObjectMapper mapper = new ObjectMapper();

     DBModel dbDetails = mapper.readValue(new File(APISessionData.envModel.getConnectionString()), DBModel.class);
     EnvModel env= dbDetails.env.get(APITestSettings.getEnvironment());
     MSSQLUtilities mssqlUtilities=new MSSQLUtilities();
     mssqlUtilities.insertQueries(env.getConnectionString(), APISessionData.DB_LOGGING_TABLE, headers, queries);
     
     
	}

public String sanitizeResponse(String inputString)
{
    if (inputString.contains("'"))
    {
        inputString = inputString.replace("'", "");
        return inputString;
    }
    else
    {
        return inputString;
    }
}
}