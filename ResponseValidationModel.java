package api_utilities.models;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseValidationModel {

	private String responseType="";
	private String expectedResponseFilePath="";
	private String expectedResponse="";
	private String actualResponseFilePath="";
	private String actualResponse="";
	private Map<String,String> xPathJsonPath= new HashMap<>();
	private List<String> dataElements= new ArrayList<>();
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public String getExpectedResponseFilePath() {
		return expectedResponseFilePath;
	}
	public void setExpectedResponseFilePath(String expectedResponseFilePath) {
		this.expectedResponseFilePath = expectedResponseFilePath;
	}
	public String getExpectedResponse() {
		return expectedResponse;
	}
	public void setExpectedResponse(String expectedResponse) {
		this.expectedResponse = expectedResponse;
	}
	public String getActualResponseFilePath() {
		return actualResponseFilePath;
	}
	public void setActualResponseFilePath(String actualResponseFilePath) {
		this.actualResponseFilePath = actualResponseFilePath;
	}
	public String getActualResponse() {
		return actualResponse;
	}
	public void setActualResponse(String actualResponse) {
		this.actualResponse = actualResponse;
	}
	public Map<String,String> getxPathJsonPath() {
		return xPathJsonPath;
	}
	public void setxPathJsonPath(Map<String,String> xPathJsonPath) {
		this.xPathJsonPath = xPathJsonPath;
	}
	public List<String> getDataElements() {
		return dataElements;
	}
	public void setDataElements(List<String> dataElements) {
		this.dataElements = dataElements;
	}
	
}
