package api_utilities.api_common;

import api_utilities.exceptions.APIUtilException;
import api_utilities.test_settings.APISessionData;
import com.github.javafaker.Faker;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;


public class APIUtil {

    public static final ExcelUtil poiObject = ExcelUtil.getInstance();
    private Faker faker = new Faker();
    public enum Mode {
        ALPHA, ALPHANUMERIC, NUMERIC
    }

    public Properties loadProperties(String filepath) throws IOException {
        Properties prop = new Properties();

        // Try-with-resources ensures the FileInputStream is closed automatically
        try (FileInputStream propsInput = new FileInputStream(filepath)) {
            prop.load(propsInput);
        }

        return prop;
    }

    private static final Logger logger = LoggerFactory.getLogger(APIUtil.class);
    private static final Random r = new Random();

    public Map<String, String> getTestData(
            String tdpath, String testdatasheet, String testcase,
            String module, String browser, String environment,
            String currentIteration) throws APIUtilException {

        Map<String, String> testData = new HashMap<>();


            String testDataPath = tdpath + File.separator + testdatasheet + ".xlsx";
            processTestDataSheet(testDataPath, environment, currentIteration, testData, testcase, module, browser);


        return testData;
    }

    private void processTestDataSheet(String testDataPath, String environment, String currentIteration,
                                      Map<String, String> testData, String testcase, String module, String browser) throws APIUtilException {

        // Create the condition for fetching data
        List<String> whereClauseTestData = Collections.singletonList("Iteration::" + currentIteration);


            // Fetch data with conditions from the Excel file
            Map<String, List<String>> result = ExcelUtil.fetchWithCondition(testDataPath, environment, whereClauseTestData);

            // Check if the Iteration column is empty
            if (result.getOrDefault("Iteration", Collections.emptyList()).isEmpty()) {
                logger.error("Blank column in Test Data - No data for Iteration {} of Environment {}", currentIteration, environment);
            }

            // Process each entry in the result map
            result.forEach((key, valueList) -> processTestDataEntry(key, valueList, testData, testcase, module, browser, currentIteration));


    }


    private void processTestDataEntry(String key, List<String> valueList, Map<String, String> testData,
                                      String testcase, String module, String browser, String currentIteration) throws APIUtilException {
        String value = valueList.get(0);
            value = handleRandomValue(value);
            value = handleSessionData(value, testcase, module, browser, currentIteration);
            testData.put(key, value);

    }

    private String handleRandomValue(String value) {
        if (value.toLowerCase().trim().startsWith("random_")) {
            try {
                String randomValue = getRandom(value);
                if (logger.isInfoEnabled()) {
                    logger.info("Random Value Added: {}", randomValue);
                }
                return randomValue;
            } catch (Exception e) {
                logger.error("Error generating random value for '{}': {}", value, e.getMessage(), e);
            }
        }
        return value;
    }

    private String handleSessionData(String value, String testcase, String module, String browser, String currentIteration) throws APIUtilException {
        if (value.toLowerCase().trim().startsWith("##") || value.toLowerCase().trim().startsWith("session=")) {
            String sessionKey = value.split("=")[1];

            String sessionData = getSessionData(testcase, module, browser, currentIteration, sessionKey);
                if (logger.isInfoEnabled()) {
                    logger.info("Session Value Added: {}", sessionData);
                }
                return sessionData;

        }
        return value;
    }

    public String generateRandomString(int length, Mode mode) {
        StringBuilder buffer = new StringBuilder();
        String characters;

        switch (mode) {
            case ALPHA:
                characters = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
                break;
            case ALPHANUMERIC:
                characters = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ1234567890";
                break;
            case NUMERIC:
                characters = "1234567890";
                break;
            default:
                throw new IllegalArgumentException("Unsupported mode: " + mode);
        }

        for (int i = 0; i < length; i++) {
            int index = r.nextInt(characters.length());
            buffer.append(characters.charAt(index));
        }
        return buffer.toString();
    }

    public String getSessionData(String testcase, String module, String browser, String currentIteration, String key) {

            return APISessionData.getSessionData(testcase, module, browser, currentIteration, key);

    }

    public String getRandom(String value)
        {

        if (value.toLowerCase().contains("name"))
        {

            value =generateFirstName();
        }
        else if (value.toLowerCase().contains("dobsoap"))
        {
            value = getDateOfBirthSOAP(value);
        }

        else if (value.toLowerCase().contains("dob"))
        {
            value = getDateOfBirth(value);
        }

        else if (value.toLowerCase().contains("individualid"))
        {
            value = generateRandomString(9, Mode.NUMERIC);
        }
        else if(value.toLowerCase().contains("number"))
        {

            List<String> values = List.of(value.split(";"));
            int length = 10; // Default value

            if (values != null && values.size() > 1) {
                length = Integer.parseInt(values.get(1));
            }
             value = generateRandomString(length, Mode.NUMERIC);
        }
        else if (value.toLowerCase().contains("alphanum"))
        {
            value = generateRandomString(30, Mode.ALPHANUMERIC);
        }
       else if(value.toLowerCase().contains("uuid") || value.toLowerCase().contains("guid"))
        {
            UUID guid = UUID.randomUUID();
            value = guid.toString();
        }

        return value;
    }



    public String getDateOfBirth(String dataValue) {
        return getDateOfBirth(dataValue, "MM/dd/yyyy");
    }

    public String getDateOfBirth(String dataValue, String dateTimeFormat) {
        String dateFormat = (dateTimeFormat != null && !dateTimeFormat.trim().isEmpty()) ? dateTimeFormat : "MM/dd/yyyy";


            String[] parts = dataValue.split(";");
            if (parts.length < 2) {
                throw new IllegalArgumentException("Invalid dataValue format. Expected format: 'value;years'");
            }

            int years = Integer.parseInt(parts[1]);
            int noOfDays = (years * 365) + (years / 4); // Leap year adjustment
            int min = noOfDays + 1;
            int max = noOfDays + 364;

            int dateRange = r.nextInt(max - min + 1) + min;
            LocalDate birthDate = LocalDate.now().minusDays(dateRange);
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern(dateFormat);
            return birthDate.format(formatter);


    }

    public String getDateOfBirthSOAP(String dataValue) {

            String date = getDateOfBirth(dataValue, "yyyy-MM-dd");
            return date + "T00:00:00.000"; // Add time part for SOAP format

    }

    public String generateFirstName() {
            return faker.name().firstName();
    }

    public String getRandomSSN(String... delimiter) {

            if (delimiter.length > 0 && "-".equals(delimiter[0])) {
                return String.format("%03d-%02d-%04d", r.nextInt(900) + 100, r.nextInt(90) + 10, r.nextInt(10000));
            }
            return String.format("%09d", r.nextInt(1000000000));

    }
}
