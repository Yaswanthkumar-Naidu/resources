package api_utilities.models;

public class APITestCaseModel {

	private String testCase;
	private String testCaseDescription;
	private String directory;
	private String testCaseFilePath;
	private String userStory;
	public String getTestCase() {
		return testCase;
	}
	public void setTestCase(String testCase) {
		this.testCase = testCase;
	}
	public String getTestCaseDescription() {
		return testCaseDescription;
	}
	public void setTestCaseDescription(String testCaseDescription) {
		this.testCaseDescription = testCaseDescription;
	}
	public String getDirectory() {
		return directory;
	}
	public void setDirectory(String directory) {
		this.directory = directory;
	}
	public String getTestCaseFilePath() {
		return testCaseFilePath;
	}
	public void setTestCaseFilePath(String testCaseFilePath) {
		this.testCaseFilePath = testCaseFilePath;
	}
	public String getUserStory() {
		return userStory;
	}
	public void setUserStory(String userStory) {
		this.userStory = userStory;
	}
		
}
