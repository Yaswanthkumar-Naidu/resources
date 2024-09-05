package uiperformanceutilities.reports.jsonreports;

import java.io.File;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.ObjectMapper;

import uiperformanceutilities.model.UIPerformanceModel;



public class JsonUtility {
	
	private static final Logger logger =LoggerFactory.getLogger(JsonUtility.class.getName());

	public void deSerializeTCDataWriteToFile(List<UIPerformanceModel> uiPerfModels,String filePath)
	{
		File file = new File(filePath);
		ObjectMapper mapper = new ObjectMapper();

		try {
			
			mapper.writeValue(file, uiPerfModels);
			String jsonData=mapper.writeValueAsString(uiPerfModels);
			logger.info(jsonData);
		}
		catch(Exception e)
		{
			logger.error("An exception occurred {}", e.getMessage());
			
		}
			
	}
	
	public UIPerformanceModel serializeTCDatafromFile(String filePath)
	{

		UIPerformanceModel uiPerf = new UIPerformanceModel();
		ObjectMapper mapper = new ObjectMapper();
		try {
			
			uiPerf= mapper.readValue(new File(filePath), UIPerformanceModel.class);
		
		}
		catch(Exception e)
		{
			logger.error("An Exception occurred {}", e.getMessage());
		}
		
		return uiPerf;
		
	} 
	
	
	
	
}