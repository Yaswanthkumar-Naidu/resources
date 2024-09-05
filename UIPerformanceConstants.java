package uiperformanceutilities.constants;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import uiperformanceutilities.model.UIPerformanceModel;

public class UIPerformanceConstants {

	private static Map<String,ArrayList<UIPerformanceModel>> uiPerfData= new HashMap<>();

	public static final long EXPECTEDRESPONSETIMEINMILLISECOND=1000;
	
	public void addUIPerfData(String sourceScreen,String destinationScreen,UIPerformanceModel uiPerfModel)
	{
		ArrayList<UIPerformanceModel> uiPerfModels= new ArrayList<>();
		uiPerfModels.add(uiPerfModel);
		getUiPerfData().put(sourceScreen +"_"+ destinationScreen, uiPerfModels);
	}
	
	public void addUpdatePerfData(String sourceScreen,UIPerformanceModel uiPerfModel)
	{
		
		if(getUiPerfData().containsKey(sourceScreen))
		{
			ArrayList<UIPerformanceModel> uiPerfModels=getUiPerfData().get(sourceScreen );
			uiPerfModels.add(uiPerfModel);
			getUiPerfData().put(sourceScreen , uiPerfModels);
		}
		else
		{
			ArrayList<UIPerformanceModel> uiPerfModels= new ArrayList<>();
			uiPerfModels.add(uiPerfModel);
			getUiPerfData().put(sourceScreen , uiPerfModels);	
		}
	}
	
	public List<UIPerformanceModel> getPerfData(String sourceScreen, String destinationScreen) {
	    String key = sourceScreen + "_" + destinationScreen;
	    return getUiPerfData().getOrDefault(key, new ArrayList<>());
	}

	public static Map<String,ArrayList<UIPerformanceModel>> getUiPerfData() {
		return uiPerfData;
	}

	public static void setUiPerfData(Map<String,ArrayList<UIPerformanceModel>> uiPerfData) {
		UIPerformanceConstants.uiPerfData = uiPerfData;
	}

}