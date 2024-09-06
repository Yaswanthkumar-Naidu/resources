package api_utilities.models;

public class MockData {
	private String id;
	private String mockLocation;
	private String templateName;
	private String interfaceType;
	private String recordCount;
	private String mockFileName;
	private String mockFileFormat;
	private String mockFileContent;
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getMockLocation() {
		return mockLocation;
	}
	public void setMockLocation(String mockLocation) {
		this.mockLocation = mockLocation;
	}
	public String getTemplateName() {
		return templateName;
	}
	public void setTemplateName(String templateName) {
		this.templateName = templateName;
	}
	public String getInterfaceType() {
		return interfaceType;
	}
	public void setInterfaceType(String interfaceType) {
		this.interfaceType = interfaceType;
	}
	public String getMockFileName() {
		return mockFileName;
	}
	public void setMockFileName(String mockFileName) {
		this.mockFileName = mockFileName;
	}
	public String getMockFileFormat() {
		return mockFileFormat;
	}
	public void setMockFileFormat(String mockFileFormat) {
		this.mockFileFormat = mockFileFormat;
	}
	public String getMockFileContent() {
		return mockFileContent;
	}
	public void setMockFileContent(String mockFileContent) {
		this.mockFileContent = mockFileContent;
	}
	public String getRecordCount() {
		return recordCount;
	}
	public void setRecordCount(String recordCount) {
		this.recordCount = recordCount;
	}
}
