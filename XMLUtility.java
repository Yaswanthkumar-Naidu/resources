package report_utilities.XMLReports;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.dataformat.xml.XmlMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;

import report_utilities.Model.ExtentModel.TestCaseDetails;

import java.io.File;

public class XMLUtility {

	
	public void DeSerializeTCDataWriteToFile(TestCaseDetails TCD,String FilePath) throws Exception
	{
		File file = new File(FilePath);
//		ObjectMapper mapper = new ObjectMapper();

		try {
			
			 XmlMapper xmlMapper = new XmlMapper();
			 xmlMapper.registerModule(new JavaTimeModule());
			    xmlMapper.writeValue(file, TCD);
			    }
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			throw e;
		}
			
	}
	

	public String getTestCaseXMLData(TestCaseDetails TCD) throws Exception
	{



		try {
			 XmlMapper xmlMapper = new XmlMapper();
			 xmlMapper.registerModule(new JavaTimeModule());
			 
			    String xmlData = xmlMapper.writeValueAsString(TCD);
			return xmlData;
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			throw e;
		}
			
	}
	
	
	public TestCaseDetails SerializeTCDatafromFile(String FilePath) throws Exception
	{

		TestCaseDetails TCD = new TestCaseDetails();
		
		try {
			XmlMapper xmlMapper = new XmlMapper();
			 xmlMapper.registerModule(new JavaTimeModule());
			 xmlMapper.disable(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES);
			TCD= xmlMapper.readValue(new File(FilePath), TestCaseDetails.class);
		
		}
		catch(Exception e)
		{
			System.out.println(e.getMessage());
			System.out.println(e.getStackTrace());
			throw e;
		}
		
		return TCD;
		
	}
	
}
