package api_utilities.models;

public class EnvModel {

	private String env = "";
	private String connectionString = "";
	private String mciUrl = "";
	private String edbCurl = "";
	private String dispositionUrl = "";
	public String getEnv() {
		return env;
	}
	public void setEnv(String env) {
		this.env = env;
	}
	
	public String getConnectionString() {
		return connectionString;
	}
	public void setConnectionString(String connectionString) {
		this.connectionString = connectionString;
	}
	public String getMciUrl() {
		return mciUrl;
	}
	public void setMciUrl(String mciUrl) {
		this.mciUrl = mciUrl;
	}
	public String getEdbCurl() {
		return edbCurl;
	}
	public void setEdbCurl(String edbCurl) {
		this.edbCurl = edbCurl;
	}
	public String getDispositionUrl() {
		return dispositionUrl;
	}
	public void setDispositionUrl(String dispositionUrl) {
		this.dispositionUrl = dispositionUrl;
	}
}
