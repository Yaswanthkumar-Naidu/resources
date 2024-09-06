package api_utilities.models;

import java.util.HashMap;
import java.util.Map;

public class DBModel {
	public static final String LOGGING_CONNECTION_STRING = "";
	public static final String LOGGING_TABLE_NAME = "";

    private DBModel() {}
	public static Map<String,EnvModel> getEnv() {
		return env;
	}
	private static final Map<String,EnvModel> env = new HashMap<>();
}
