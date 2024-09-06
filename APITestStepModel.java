package api_utilities.models;

public class APITestStepModel {

	private String module;
	private String apiName;
	private String filePath;
	private String sheetName;
	private int startIndexforIteration;
	private int endIndexforIteration;
	public String getModule() {
		return module;
	}
	public void setModule(String module) {
		this.module = module;
	}
	public String getApiName() {
		return apiName;
	}
	public void setApiName(String apiName) {
		this.apiName = apiName;
	}
	public String getFilePath() {
		return filePath;
	}
	public void setFilePath(String filePath) {
		this.filePath = filePath;
	}
	public String getSheetName() {
		return sheetName;
	}
	public void setSheetName(String sheetName) {
		this.sheetName = sheetName;
	}
	public int getStartIndexforIteration() {
		return startIndexforIteration;
	}
	public void setStartIndexforIteration(int startIndexforIteration) {
		this.startIndexforIteration = startIndexforIteration;
	}
	public int getEndIndexforIteration() {
		return endIndexforIteration;
	}
	public void setEndIndexforIteration(int endIndexforIteration) {
		this.endIndexforIteration = endIndexforIteration;
	}	
}
