package api_utilities.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class APIModel {

	 int testCaseIdNew = 1;
	    String testCaseNameNew = "";
	    String moduleNew = "";
	    String interfaceNameNew = "";
	    String fileFormatNew = "";
	    String apiType = "";
	    String serviceUrl = "";
	    String soapAction = "";
	    String methodTypeNew = "";
	    String dbQuery="";
	    String dbExpectedValue="";
		boolean certificateRequired;
	    String certificateFileName = "";
	    String certificatePasswordNew = "";
	    String requestFilePath = "";
	    String responseFilePath = "";
	    String testDataFilePath = "";
	    String requestDataNew="";
	    String responseDataNew = "";
	    boolean needMockNew;
	    String mockedInterfaceNameNew;
	    int startIndexforIterationNew;
	    int endIndexforIteration;
	    ArrayList<Integer> iterationsCollection = new ArrayList<>();
	    int iterationCountNew;
	    int timeoutNew;
	    ResponseValidationModel responseValidationModel= new ResponseValidationModel();
	    StoreResponseModel storeResponseModel= new StoreResponseModel();
	    MockData mockData = new MockData();

	    Map<String, String> headerDataNew = new HashMap<>();
	    private static final Logger logger =LoggerFactory.getLogger(APIModel.class.getName()); 	
		
		
	    public void setTestCaseId(String testCaseId)
	    {
	        testCaseIdNew = Integer.parseInt(testCaseId);
	    }


	    public void setTestCaseName(String testCaseName)
	    {
	        testCaseNameNew = testCaseName;
	    }

	    public void setModule(String module)
	    {
	        moduleNew = module;
	    }

	    public void setInterfaceName(String interfaceName)
	    {
	        interfaceNameNew = interfaceName;
	    }

	    public void setFileFormat(String fileFormat)
	    {
	        fileFormatNew = fileFormat;
	    }

	    public void setInterfaceType(String interfaceType)
	    {
	        apiType = interfaceType;
	    }

	    public void setServiceUrl(String url)
	    {
	        serviceUrl = url;
	    }

	    public void setSOAPAction(String sOAPAction)
	    {
	        soapAction = sOAPAction;
	    }


	    public void setMethodType(String methodType)
	    {
	        methodTypeNew = methodType;
	    }

	    public String getDBQuery() {
			return dbQuery;
		}


		public void setDBQuery(String dBQuery) {
			dbQuery = dBQuery;
		}
		
		public String getDBExpectedValue() {
			return dbExpectedValue;
		}


		public void setDBExpectedValue(String dBExpectedValue) {
			dbExpectedValue = dBExpectedValue;
		}
		
	    public void setCertificatePath(String certificationRequired,String  certificatePath, String certificatePassword)
	    {
	        if (certificationRequired.equalsIgnoreCase("YES"))
	        {
	            certificateRequired = true;
	            certificateFileName = certificatePath;
	            certificatePasswordNew = certificatePassword;
	        }
	        else
	        {
	            certificateRequired = false;
	            certificateFileName = "";
	            certificatePasswordNew = "";
	        }
	    }


	    public void setRequestData(String requestData)
	    {
	        requestDataNew = requestData;
	    }

	    public void setResponseData(String responseData)
	    {
	        responseDataNew = responseData;
	    }

	    public void setRequestPath(String requestPath)
	    {
	        requestFilePath = requestPath;
	    }

	    public void setResponsePath(String responsePath)
	    {
	        responseFilePath = responsePath;
	    }

	    public void setTestDataFileName(String testDataFileName)
	    {
	        testDataFilePath = testDataFileName;
	    }

	    public void setMockDetails(String needMock, String mockedInterfaceName)
	    {
	        if (needMock.toUpperCase().trim().equals("YES"))
	        {
	            needMockNew = true;
	            mockedInterfaceNameNew = mockedInterfaceName;

	        }
	        else
	        {
	            needMockNew = false;
	            mockedInterfaceNameNew = "";
	        }
	    }

	    public void setStartIndexAndEndforIteration(String startIndexforIteration, String iterationCount)
	    {
	       iterationCountNew = 1;
	        startIndexforIterationNew = 1;
	        try
	        {
	            iterationCountNew = Integer.parseInt(iterationCount);

	        }

	        catch (Exception e)
	        {
             logger.info("iterationCountNew started");
	        }
	        try
	        {
	            startIndexforIterationNew = Integer.parseInt(startIndexforIteration);

	        }

	        catch (Exception e)
	        {
	        	logger.info("startIndexforIterationNew started");
	        }

	        if (iterationCountNew == 1)
	        {
	            endIndexforIteration = startIndexforIterationNew;

	            iterationsCollection.add(endIndexforIteration);
	        }
	        else
	        {
	            endIndexforIteration = startIndexforIterationNew + (iterationCountNew - 1);

	            for (int ii = 0; ii < iterationCountNew; ii++)
	            {
	                iterationsCollection.add(startIndexforIterationNew + ii);
	            }
	        }

	    }


	    public void setTestDataPath(String testDataPath)
	    {
	        testDataFilePath = testDataPath;
	    }


	    public void setHeaderData(Map<String, String> headerData)
	    {
	        headerDataNew = headerData;
	    }




	    public void setMockData(MockData lclmockData)
	    {
	        mockData = lclmockData;
	    }

	    public void setTimeout(int timeout)
	    {
	        timeoutNew = timeout;
	    }


	    public int getTestCaseId()
	    {
	        return testCaseIdNew;
	    }


	    public String getTestCaseName()
	    {
	        return testCaseNameNew;
	    }

	    public String getModule()
	    {
	        return moduleNew;
	    }

	    public String getInterfaceName()
	    {
	        return interfaceNameNew;
	    }

	    public String getFileFormat()
	    {
	        return fileFormatNew;
	    }

	    public String getInterfaceType()
	    {
	        return apiType;
	    }

	    public String getServiceUrl()
	    {
	        return serviceUrl;
	    }

	    public String getSOAPAction()
	    {
	        return soapAction;
	    }

	    public String getMethodType()
	    {
	        return methodTypeNew;
	    }

	    public boolean isCertificateRequired()
	    {
	        return certificateRequired;
	    }


	    public String getCertificatePath()
	    {
	        return certificateFileName;
	    }


	    public String getCertificatePassword()
	    {
	        return certificatePasswordNew;
	    }

	    public String gettRequestPath()
	    {
	        return requestFilePath;
	    }
	    public String gettRequestData()
	    {
	        return requestDataNew;
	    }

	    public String getResponsePath()
	    {
	        return responseFilePath;
	    }
	    public String getResponseData()
	    {
	        return responseDataNew;
	    }


	    public String getTestDataPath()
	    {
	        return testDataFilePath;
	    }

	    public boolean isMockRequired()
	    {
	        return needMockNew;
	    }


	    public String getMockedInterfaceName()
	    {
	        return mockedInterfaceNameNew;
	    }

	    public int getStartIndexForIteration()
	    {
	        return startIndexforIterationNew;
	    }

	    public int getEndIndexforIteration()
	    {
	        return endIndexforIteration;
	    }

	    public int getIterationCount()
	    {
	        return iterationCountNew;
	    }

	    public int getIterationFromCollection(int iterationCount)
	    {
	        try
	        {

	            return iterationsCollection.get(iterationCount);

	        }
	        catch (Exception e)
	        {
	        	
	        	logger.error("{} Failed to generate the Live Results",e.getMessage());
	        }
			return iterationCount;
	    }
	    public MockData getMockData()
	    {
	        return mockData;
	    }


	    public Map<String, String> getHeaderData()
	    {
	        return headerDataNew;
	    }

	    public int getTimeout()
	    {
	        return timeoutNew;
	    }

	    public ResponseValidationModel getResponseValidationModel()
	    {
	        return responseValidationModel;
	    }

	    public void setResponseValidationModel(ResponseValidationModel responseValidationModel)
	    {
	    	this.responseValidationModel=responseValidationModel;
	    }

	    
	    public StoreResponseModel getStoreResponseModel()
	    {
	        return storeResponseModel;
	    }

	    public void setStoreResponseModel(StoreResponseModel storeResponseModel)
	    {
	    	this.storeResponseModel=storeResponseModel;
	    }


	    public void setInterfaceDetails(String module, String interfaceName, String fileFormat, String interfaceType,String serviceUrl,String soapAction,String methodType, String certificateRequired, String certificateFileName, String certificatePassword, String requestData, String responseData, String needMock, String mockedInterfaceName, MockData mockData, Map<String,String> headerData, int timeout,ResponseValidationModel responseValidationModel ,StoreResponseModel storeResponseModel, String dbQuery, String dbExpectedValue)
		{

	        setModule(module);
			setInterfaceName(interfaceName);
	        setFileFormat(fileFormat);
	        setInterfaceType(interfaceType);
	        setServiceUrl(serviceUrl);
	        setSOAPAction(soapAction);
	        setMethodType(methodType);
	        setCertificatePath(certificateRequired, certificateFileName,certificatePassword);
			setRequestData(requestData);
			setResponseData(responseData);
	        setMockDetails(needMock, mockedInterfaceName);
	        setHeaderData(headerData);
	        setMockData(mockData);
	        setTimeout(timeout);
	        setResponseValidationModel(responseValidationModel);
	        setStoreResponseModel(storeResponseModel);
	        setDBQuery(dbQuery);
	        setDBExpectedValue(dbExpectedValue);

	    }
	
	
}
