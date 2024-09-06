package api_utilities.models;

import java.util.HashMap;
import java.util.Map;

public class StoreResponseModel {

	private String responseType="";
	private Map<String,String> xpathJsonPath= new HashMap<>();
	public String getResponseType() {
		return responseType;
	}
	public void setResponseType(String responseType) {
		this.responseType = responseType;
	}
	public Map<String,String> getXpathJsonPath() {
		return xpathJsonPath;
	}
	public void setXpathJsonPath(Map<String,String> xpathJsonPath) {
		this.xpathJsonPath = xpathJsonPath;
	}
	
}
