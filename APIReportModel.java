package api_utilities.models;

public class APIReportModel {

	private String testStepResult="";
	private boolean boolResponseStringValidation=false;
	private boolean boolXMLJSONValidation=false;
	private boolean dataElementsValidation=false;
	private String expectedResponse="";
	private String actualResponse="";
	private String  xpathJsonKey="";
	private String  dataElementKey="";
	private String  additionalDetails="";
	private String request;
	private String response;
	private String url;
	private String dbValidation;
	private String dbExpectedValue;
	private String dbActualValue;
	private String statusCode;
	public String getTestStepResult() {
		return testStepResult;
	}
	public void setTestStepResult(String testStepResult) {
		this.testStepResult = testStepResult;
	}
	public boolean isBoolResponseStringValidation() {
		return boolResponseStringValidation;
	}
	public void setBoolResponseStringValidation(boolean boolResponseStringValidation) {
		this.boolResponseStringValidation = boolResponseStringValidation;
	}
	public boolean isBoolXMLJSONValidation() {
		return boolXMLJSONValidation;
	}
	public void setBoolXMLJSONValidation(boolean boolXMLJSONValidation) {
		this.boolXMLJSONValidation = boolXMLJSONValidation;
	}
	public boolean isDataElementsValidation() {
		return dataElementsValidation;
	}
	public void setDataElementsValidation(boolean dataElementsValidation) {
		this.dataElementsValidation = dataElementsValidation;
	}
	public String getExpectedResponse() {
		return expectedResponse;
	}
	public void setExpectedResponse(String expectedResponse) {
		this.expectedResponse = expectedResponse;
	}
	public String getActualResponse() {
		return actualResponse;
	}
	public void setActualResponse(String actualResponse) {
		this.actualResponse = actualResponse;
	}
	public String getXpathJsonKey() {
		return xpathJsonKey;
	}
	public void setXpathJsonKey(String xpathJsonKey) {
		this.xpathJsonKey = xpathJsonKey;
	}
	public String getDataElementKey() {
		return dataElementKey;
	}
	public void setDataElementKey(String dataElementKey) {
		this.dataElementKey = dataElementKey;
	}
	public String getAdditionalDetails() {
		return additionalDetails;
	}
	public void setAdditionalDetails(String additionalDetails) {
		this.additionalDetails = additionalDetails;
	}
	public String getRequest() {
		return request;
	}
	public void setRequest(String request) {
		this.request = request;
	}
	public String getResponse() {
		return response;
	}
	public void setResponse(String response) {
		this.response = response;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDbValidation() {
		return dbValidation;
	}
	public void setDbValidation(String dbValidation) {
		this.dbValidation = dbValidation;
	}
	public String getDbExpectedValue() {
		return dbExpectedValue;
	}
	public void setDbExpectedValue(String dbExpectedValue) {
		this.dbExpectedValue = dbExpectedValue;
	}
	public String getDbActualValue() {
		return dbActualValue;
	}
	public void setDbActualValue(String dbActualValue) {
		this.dbActualValue = dbActualValue;
	}
	public String getStatusCode() {
		return statusCode;
	}
	public void setStatusCode(String statusCode) {
		this.statusCode = statusCode;
	}
}
