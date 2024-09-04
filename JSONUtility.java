package report_utilities.JSONReports;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.gson.*;

import report_utilities.Model.ExtentModel.TestCaseDetails;

import java.io.File;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class JSONUtility {
	
	private static final Logger logger = LoggerFactory.getLogger(JSONUtility.class.getName());

	public void deSerializeTCDataWriteToFile(TestCaseDetails tcd,String filePath)
	{
		File file = new File(filePath);
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.configure(SerializationFeature.FAIL_ON_EMPTY_BEANS, false);

		try {
			
			mapper.writeValue(file, tcd);
	
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			logger.error("An error occurred", e);
			
		}
			
	}
	
	
	

	public String getTestCaseJSONData(TestCaseDetails tcd)
	{
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		String jsonData="";
		try {
			
			Gson gson = new GsonBuilder()
				    .registerTypeAdapter(LocalDateTime.class, (JsonSerializer<LocalDateTime>) (src, typeOfSrc, context) ->
				        new JsonPrimitive(src.format(DateTimeFormatter.ISO_LOCAL_DATE_TIME)))
				    .registerTypeAdapter(LocalDateTime.class, (JsonDeserializer<LocalDateTime>) (json, typeOfT, context) ->
				        LocalDateTime.parse(json.getAsJsonPrimitive().getAsString(), DateTimeFormatter.ISO_LOCAL_DATE_TIME)).serializeNulls().create();
			jsonData = gson.toJson(tcd); 
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			logger.error("An error occurred", e);;
		}
		
		return jsonData;
	}
	
	
	public TestCaseDetails serializeTCDatafromFile(String filePath) 
	{

		TestCaseDetails tcd = new TestCaseDetails();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
		try {
			
			tcd= mapper.readValue(new File(filePath), TestCaseDetails.class);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			logger.error("An error occurred", e);
		
		}
		
		return tcd;
		
	}
	
	public TestCaseDetails serializeTCDatafromString(String tcData) 
	{

		TestCaseDetails tcd = new TestCaseDetails();
		ObjectMapper mapper = new ObjectMapper();
		mapper.registerModule(new JavaTimeModule());
		mapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);

		try {
			
			tcd= mapper.readValue(tcData, TestCaseDetails.class);
		
		}
		catch(Exception e)
		{
			logger.info(e.getMessage());
			logger.error("An error occurred", e);
			
		}
		
		return tcd;
		
	}
	
}
