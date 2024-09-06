package api_utilities.models;

public class APIConfig {

	//InterfaceTestRunSettings

	private String environment="";
    private String defaultTestDataFormat = ".xlsx";
    private String interfaceTestCaseSheet = "";
    private String urlRepositorySheet = "";
    private String requestLocation = "";
    private String responseLocation = "";
    private String certificateLocation = "";
    private String certificatePassword="";
    private String testDataPath = "";
    
    private boolean addReportToDB = false;

    private long testRunId = 1;
    private String mockRepositorySheet;
    private String interfaceSheetDetails;
    private String excelSheetExtension;
    private String xmlExtension;
    private String jsonExtension;
    private String commonMockSheetName;
    private String useCommonMockSheet="";
    private String mockSheetName;
    private String headerRepositorySheetName;
    private String headerRepository;
    private String responseSheetPath;
    private Integer defaultServiceTimeout;
    private String mockTemplateLocation;
    private String domainName;
    private String excelFileName;
	private String responseValidationFilePath;
	private String responseValidationSheetName;
	private String testDataLocationSD;
	private String testDataLocationXMLValidation;
	private String configLocationXMLValidation;
	private boolean useCommonTestDataSheet;
	private String commonTestDataSheetName;
	private String apiTestSuiteDirectory;
	private String apiTestCaseDirectory;
	private String apiDirectory;
	private String apiTestSuiteFileName;
	private String apiTestSuiteSheetName;
	private String apiTestCaseSheetName;

	private String storeResponseDataFilePath;
	private String storeResponseDataSheetName;
	
	public String getEnvironment() {
		return environment;
	}
	public void setEnvironment(String environment) {
		this.environment = environment;
	}
	public String getInterfaceTestCaseSheet() {
		return interfaceTestCaseSheet;
	}
	public void setInterfaceTestCaseSheet(String interfaceTestCaseSheet) {
		this.interfaceTestCaseSheet = interfaceTestCaseSheet;
	}
	public String getUrlRepositorySheet() {
		return urlRepositorySheet;
	}
	public void setUrlRepositorySheet(String urlRepositorySheet) {
		this.urlRepositorySheet = urlRepositorySheet;
	}
	public String getRequestLocation() {
		return requestLocation;
	}
	public void setRequestLocation(String requestLocation) {
		this.requestLocation = requestLocation;
	}
	public String getResponseLocation() {
		return responseLocation;
	}
	public void setResponseLocation(String responseLocation) {
		this.responseLocation = responseLocation;
	}
	public String getCertificateLocation() {
		return certificateLocation;
	}
	public void setCertificateLocation(String certificateLocation) {
		this.certificateLocation = certificateLocation;
	}
	public String getCertificatePassword() {
		return certificatePassword;
	}
	public void setCertificatePassword(String certificatePassword) {
		this.certificatePassword = certificatePassword;
	}
	public String getTestDataPath() {
		return testDataPath;
	}
	public void setTestDataPath(String testDataPath) {
		this.testDataPath = testDataPath;
	}
	public boolean isAddReportToDB() {
		return addReportToDB;
	}
	public void setAddReportToDB(boolean addReportToDB) {
		this.addReportToDB = addReportToDB;
	}
	public long getTestRunId() {
		return testRunId;
	}
	public void setTestRunId(long testRunId) {
		this.testRunId = testRunId;
	}
	public String getMockRepositorySheet() {
		return mockRepositorySheet;
	}
	public void setMockRepositorySheet(String mockRepositorySheet) {
		this.mockRepositorySheet = mockRepositorySheet;
	}
	public String getInterfaceSheetDetails() {
		return interfaceSheetDetails;
	}
	public void setInterfaceSheetDetails(String interfaceSheetDetails) {
		this.interfaceSheetDetails = interfaceSheetDetails;
	}
	public String getExcelSheetExtension() {
		return excelSheetExtension;
	}
	public void setExcelSheetExtension(String excelSheetExtension) {
		this.excelSheetExtension = excelSheetExtension;
	}
	public String getXmlExtension() {
		return xmlExtension;
	}
	public void setXmlExtension(String xmlExtension) {
		this.xmlExtension = xmlExtension;
	}
	public String getJsonExtension() {
		return jsonExtension;
	}
	public void setJsonExtension(String jsonExtension) {
		this.jsonExtension = jsonExtension;
	}
	public String getCommonMockSheetName() {
		return commonMockSheetName;
	}
	public void setCommonMockSheetName(String commonMockSheetName) {
		this.commonMockSheetName = commonMockSheetName;
	}
	public String getUseCommonMockSheet() {
		return useCommonMockSheet;
	}
	public void setUseCommonMockSheet(String useCommonMockSheet) {
		this.useCommonMockSheet = useCommonMockSheet;
	}
	public String getHeaderRepository() {
		return headerRepository;
	}
	public void setHeaderRepository(String headerRepository) {
		this.headerRepository = headerRepository;
	}
	public String getHeaderRepositorySheetName() {
		return headerRepositorySheetName;
	}
	public void setHeaderRepositorySheetName(String headerRepositorySheetName) {
		this.headerRepositorySheetName = headerRepositorySheetName;
	}
	public String getMockSheetName() {
		return mockSheetName;
	}
	public void setMockSheetName(String mockSheetName) {
		this.mockSheetName = mockSheetName;
	}
	public String getResponseSheetPath() {
		return responseSheetPath;
	}
	public void setResponseSheetPath(String responseSheetPath) {
		this.responseSheetPath = responseSheetPath;
	}
	public Integer getDefaultServiceTimeout() {
		return defaultServiceTimeout;
	}
	public void setDefaultServiceTimeout(Integer defaultServiceTimeout) {
		this.defaultServiceTimeout = defaultServiceTimeout;
	}
	public String getMockTemplateLocation() {
		return mockTemplateLocation;
	}
	public void setMockTemplateLocation(String mockTemplateLocation) {
		this.mockTemplateLocation = mockTemplateLocation;
	}
	public String getDomainName() {
		return domainName;
	}
	public void setDomainName(String domainName) {
		this.domainName = domainName;
	}
	public String getExcelFileName() {
		return excelFileName;
	}
	public void setExcelFileName(String excelFileName) {
		this.excelFileName = excelFileName;
	}
	public String getResponseValidationFilePath() {
		return responseValidationFilePath;
	}
	public void setResponseValidationFilePath(String responseValidationFilePath) {
		this.responseValidationFilePath = responseValidationFilePath;
	}
	public String getResponseValidationSheetName() {
		return responseValidationSheetName;
	}
	public void setResponseValidationSheetName(String responseValidationSheetName) {
		this.responseValidationSheetName = responseValidationSheetName;
	}
	public String getTestDataLocationSD() {
		return testDataLocationSD;
	}
	public void setTestDataLocationSD(String testDataLocationSD) {
		this.testDataLocationSD = testDataLocationSD;
	}
	public String getDefaultTestDataFormat() {
		return defaultTestDataFormat;
	}
	public void setDefaultTestDataFormat(String defaultTestDataFormat) {
		this.defaultTestDataFormat = defaultTestDataFormat;
	}
	public String getTestDataLocationXMLValidation() {
		return testDataLocationXMLValidation;
	}
	public void setTestDataLocationXMLValidation(String testDataLocationXMLValidation) {
		this.testDataLocationXMLValidation = testDataLocationXMLValidation;
	}
	public String getConfigLocationXMLValidation() {
		return configLocationXMLValidation;
	}
	public void setConfigLocationXMLValidation(String configLocationXMLValidation) {
		this.configLocationXMLValidation = configLocationXMLValidation;
	}
	public boolean isUseCommonTestDataSheet() {
		return useCommonTestDataSheet;
	}
	public void setUseCommonTestDataSheet(boolean useCommonTestDataSheet) {
		this.useCommonTestDataSheet = useCommonTestDataSheet;
	}
	public String getCommonTestDataSheetName() {
		return commonTestDataSheetName;
	}
	public void setCommonTestDataSheetName(String commonTestDataSheetName) {
		this.commonTestDataSheetName = commonTestDataSheetName;
	}
	public String getApiTestSuiteDirectory() {
		return apiTestSuiteDirectory;
	}
	public void setApiTestSuiteDirectory(String apiTestSuiteDirectory) {
		this.apiTestSuiteDirectory = apiTestSuiteDirectory;
	}
	public String getApiTestCaseDirectory() {
		return apiTestCaseDirectory;
	}
	public void setApiTestCaseDirectory(String apiTestCaseDirectory) {
		this.apiTestCaseDirectory = apiTestCaseDirectory;
	}
	public String getApiDirectory() {
		return apiDirectory;
	}
	public void setApiDirectory(String apiDirectory) {
		this.apiDirectory = apiDirectory;
	}
	public String getApiTestCaseSheetName() {
		return apiTestCaseSheetName;
	}
	public void setApiTestCaseSheetName(String apiTestCaseSheetName) {
		this.apiTestCaseSheetName = apiTestCaseSheetName;
	}
	public String getApiTestSuiteSheetName() {
		return apiTestSuiteSheetName;
	}
	public void setApiTestSuiteSheetName(String apiTestSuiteSheetName) {
		this.apiTestSuiteSheetName = apiTestSuiteSheetName;
	}
	public String getStoreResponseDataFilePath() {
		return storeResponseDataFilePath;
	}
	public void setStoreResponseDataFilePath(String storeResponseDataFilePath) {
		this.storeResponseDataFilePath = storeResponseDataFilePath;
	}
	public String getApiTestSuiteFileName() {
		return apiTestSuiteFileName;
	}
	public void setApiTestSuiteFileName(String apiTestSuiteFileName) {
		this.apiTestSuiteFileName = apiTestSuiteFileName;
	}
	public String getStoreResponseDataSheetName() {
		return storeResponseDataSheetName;
	}
	public void setStoreResponseDataSheetName(String storeResponseDataSheetName) {
		this.storeResponseDataSheetName = storeResponseDataSheetName;
	}
}
