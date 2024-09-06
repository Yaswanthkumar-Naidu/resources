package api_utilities.api_helpers;

import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import api_utilities.api_common.APIMaster;
import api_utilities.models.APIModel;
import api_utilities.models.APIReportModel;
import api_utilities.test_settings.APISessionData;
import api_utilities.test_settings.APITestSettings;
import io.restassured.response.Response;

public class APIController {
	private static final Logger logger =LoggerFactory.getLogger(APIController.class.getName());

	public List<APIReportModel> executeAPI(String testcase,String moduleName, String interfaceName,String browser, String iteration,String apiFilePath)
	{
		APIMaster interfaceMaster = new APIMaster();
		ArrayList<APIReportModel> apiReportModels = new ArrayList<>();
		Response response = null;
		try {
			APIModel iModel=	interfaceMaster.fetchInterfaceRepository(APITestSettings.getApiTestSettings(),testcase,moduleName,interfaceName,iteration,apiFilePath);

			RESTHelper apiREST = new RESTHelper();
			SOAPHelper apiSOAP = new SOAPHelper();

			String apiType=iModel.getInterfaceType();
			String methodType=iModel.getMethodType();
			RESTValidation validateREST= new RESTValidation();
			SOAPValidations validateSOAP= new SOAPValidations();
			DBValidation validateDB = new DBValidation();
			String request=iModel.gettRequestData();
			String expectedResponse=iModel.getResponseData();
			String url= iModel.getServiceUrl();
			String dbQuery = iModel.getDBQuery();
			
			if(apiType.equalsIgnoreCase("REST"))
			{
				response=apiREST.executeRESTAPI(iModel,iteration,methodType);	
				APIReportModel apiReportModel = new APIReportModel();
				apiReportModel.setUrl(url);
				apiReportModel.setRequest(request);
				apiReportModel.setResponse(response.getBody().asString());
				apiReportModel.setStatusCode("Status Code: =>"+response.getStatusCode()+"; Status Line: =>"+response.getStatusLine());


				if(response.statusCode()==200)
				{
					apiReportModels = extracted(testcase, moduleName, browser, iteration, apiReportModels, response,
							iModel, validateREST, expectedResponse, apiReportModel);
				}
				else
				{
					apiReportModels.add(apiReportModel);
					apiReportModels.get(0).setTestStepResult("FAIL");
					apiReportModels.get(0).setAdditionalDetails(iModel.getInterfaceName() + " Failed. Response Code : +" +response.statusCode() + "Error Details : " + response.asString());
					
				}
				//DB VALIDATION
				if (APITestSettings.DBVALIDATION.equalsIgnoreCase("Yes") && !dbQuery.equalsIgnoreCase("N//A")) {
						apiReportModel.setDbActualValue(validateDB.validateDBStatus(iModel));
						apiReportModel.setDbExpectedValue(iModel.getDBExpectedValue());
						apiReportModel.setDbValidation(iModel.getDBQuery());
						if(iModel.getDBExpectedValue().equals(apiReportModel.getDbActualValue())) {
							apiReportModels.get(0).setTestStepResult("PASS");
							apiReportModels.get(0).setAdditionalDetails(apiReportModel.getAdditionalDetails()+":DB Validation was successful.");
						}
						else
						{
							apiReportModels.get(0).setTestStepResult("FAIL");
							apiReportModels.get(0).setAdditionalDetails(apiReportModel.getAdditionalDetails()+":Expected value mismatch in DB validation");
						}

					}
				

			}
			else if(apiType.equalsIgnoreCase("SOAP"))
			{

				extracted(testcase, moduleName, browser, iteration, apiReportModels, iModel, apiSOAP, validateSOAP,
						validateDB, request, expectedResponse, url, dbQuery);
			}
			
		} catch (Exception e) {
		
			e.printStackTrace();
		}

		return apiReportModels;

	}

	private ArrayList<APIReportModel> extracted(String testcase, String moduleName, String browser, String iteration,
			ArrayList<APIReportModel> apiReportModels, Response response, APIModel iModel, RESTValidation validateREST,
			String expectedResponse, APIReportModel apiReportModel) throws Exception {
		if(!iModel.getResponsePath().equals(("")))
		{
			apiReportModels=validateREST.validateAPIPostResponse(apiReportModel, expectedResponse);

		}
		else if(!iModel.getResponseValidationModel().getxPathJsonPath().isEmpty())
		{
			apiReportModels=validateREST.validateJSONPath(apiReportModel,iModel.getResponseValidationModel().getxPathJsonPath());


		}

		else {
		HashMap<String,String> sessionData = validateREST.storeJSONPath(response.getBody().asString(), iModel.getStoreResponseModel().getXpathJsonPath());


		APISessionData.setSessionDataCollection(testcase, moduleName, browser, iteration, sessionData);

		apiReportModel.setTestStepResult("PASS");
		apiReportModel.setAdditionalDetails(iModel.getInterfaceName() + " executed successfully.");
		apiReportModels.add(apiReportModel);
		}
		return apiReportModels;
	}

	private void extracted(String testcase, String moduleName, String browser, String iteration,
			ArrayList<APIReportModel> apiReportModels, APIModel iModel, SOAPHelper apiSOAP,
			SOAPValidations validateSOAP, DBValidation validateDB, String request, String expectedResponse, String url,
			String dbQuery) throws Exception {
		Response response;
		response=apiSOAP.executeSOAPAPI(iModel,iteration);	
		APIReportModel apiReportModel = new APIReportModel();
		apiReportModel.setUrl(url);
		apiReportModel.setRequest(request);
		apiReportModel.setResponse(response.getBody().asString());	
		if(response.statusCode()==200)
		{
			if(!iModel.getResponsePath().equals(("")))
			{
				apiReportModels=validateSOAP.validateAPIPostResponse(apiReportModel, expectedResponse);
			}
			else if(!iModel.getResponseValidationModel().getxPathJsonPath().isEmpty())
			{

				apiReportModels=validateSOAP.validateXPath(apiReportModel,iModel.getResponseValidationModel().getxPathJsonPath());
			}

			else {
			HashMap<String,String> sessionData = validateSOAP.storeXPath(response.getBody().asString(), iModel.getStoreResponseModel().getXpathJsonPath());


			APISessionData.setSessionDataCollection(testcase, moduleName, browser, iteration, sessionData);
			apiReportModel.setTestStepResult("PASS");
			apiReportModel.setAdditionalDetails(iModel.getInterfaceName() + " executed successfully.");
			apiReportModels.add(apiReportModel);
			}

		}
		else
		{
			apiReportModels.add(apiReportModel);
			apiReportModels.get(0).setTestStepResult("FAIL");
			apiReportModels.get(0).setAdditionalDetails(iModel.getInterfaceName() + " Failed. Response Code : +" +response.statusCode() + "Error Details : " + response.asString());
			
		}
		
		//DB VALIDATION
		if (APITestSettings.DBVALIDATION.equalsIgnoreCase("Yes") && !(dbQuery.equalsIgnoreCase("N//A") 
				|| dbQuery.equalsIgnoreCase(""))) {
				apiReportModel.setDbActualValue(validateDB.validateDBStatus(iModel));
				apiReportModel.setDbExpectedValue(iModel.getDBExpectedValue());
				apiReportModel.setDbValidation(iModel.getDBQuery());
				if(iModel.getDBExpectedValue().equals(apiReportModel.getDbActualValue())) {
					apiReportModels.get(0).setTestStepResult("PASS");
					apiReportModels.get(0).setAdditionalDetails(apiReportModel.getAdditionalDetails()+":DB Validation was successful.");
					
				}
				else
				{
					apiReportModels.get(0).setTestStepResult("FAIL");
					apiReportModels.get(0).setAdditionalDetails(apiReportModel.getAdditionalDetails()+":Expected value mismatch in DB validation");
					
				}

			}
	}

	/**
	 * Executes the API for the given module and interface.
	 *
	 * @param moduleName   the name of the module
	 * @param interfaceName the name of the interface
	 * @throws UnsupportedOperationException if this method is not overridden and implemented
	 */
	public void executeAPI(String moduleName, String interfaceName) {
	    /*
	     * This method is intended to be overridden by subclasses or other classes
	     * to provide the actual implementation for executing the API based on the
	     * given module and interface names. The default implementation throws an
	     * UnsupportedOperationException to indicate that it should not be called
	     * directly.
	     */
	    throw new UnsupportedOperationException("This method should be overridden and implemented.");
	}



	public void storeRESTResponse(String responseLocation, Response responsePost) {
	    try (FileWriter file = new FileWriter(responseLocation)) {
	        file.write(responsePost.prettyPrint());
	        file.flush();
	    } catch (IOException e) {
	        logger.error("Exception while storing REST response: {}", e.getMessage(), e);
	    }
	}

}