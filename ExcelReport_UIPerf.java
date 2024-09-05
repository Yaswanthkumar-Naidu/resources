package report_utilities.UIPerfReport;

import report_utilities.ExcelReport.ExcelReportCommon;
import testsettings.TestRunSettings;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import pom.sd.InitialScreening;

import java.io.IOException;
import java.util.*;

public class ExcelReport_UIPerf 
{
	static ArrayList<String> Header = new ArrayList<String>();
	static ArrayList<Integer> HeaderSize = new ArrayList<Integer>();
	static ExcelReportCommon excelreport = new ExcelReportCommon();
	private static final Logger logger =LoggerFactory.getLogger(InitialScreening.class.getName());

	public void InitializeColumnHeaders()
	{
		Header.add("S.No");
		HeaderSize.add(10*250);
		Header.add("SourcePage");
		HeaderSize.add(20*500);
		Header.add("DestinationPage");
		HeaderSize.add(20*500);
		Header.add("AgerageNavigationTime");
		HeaderSize.add(20*250);
		Header.add("PageLoadTime");
		HeaderSize.add(20*250);
		Header.add("DOMLoadTime");
		HeaderSize.add(20*250);
		
		
	}
	
	
	
	 public static void writeDataToExcel(String filePath) throws Exception {
		 Map<HashMap<String, String>, ArrayList<String>> dataMap=new  LinkedHashMap<HashMap<String, String>, ArrayList<String>>();  
		 dataMap=TestRunSettings.getUiperfdata();
		 List<String> pageLoadtime=new LinkedList<String>();
		 pageLoadtime=TestRunSettings.getPageLoadtime();
		 List<String> domLoadtime=new LinkedList<String>();
		 domLoadtime=TestRunSettings.getDomLoadtime();
		 try (Workbook workbook = new XSSFWorkbook()) 
	        {
	            Sheet sheet = workbook.createSheet("Perofrmance");
	            	            

	            
	            Row headerrow = sheet.createRow(0);
	            
	            Cell SerialNumberCell = headerrow.createCell(0);
	            SerialNumberCell.setCellValue("S.No");
	            SerialNumberCell.setCellStyle(excelreport.getHeaderCellStyle(workbook));
	            
	            Cell SPheaderCell = headerrow.createCell(1);
	            SPheaderCell.setCellValue("SourcePage");
	            SPheaderCell.setCellStyle(excelreport.getHeaderCellStyle(workbook));
	            
	            Cell DPheaderCell = headerrow.createCell(2);
	            DPheaderCell.setCellValue("DestinationPage");
	            DPheaderCell.setCellStyle(excelreport.getHeaderCellStyle(workbook));
	            
	            Cell TimeheaderCell = headerrow.createCell(3);
	            TimeheaderCell.setCellValue("AgerageNavigationTime");
	            TimeheaderCell.setCellStyle(excelreport.getHeaderCellStyle(workbook));
	            
	            Cell PageloadCell = headerrow.createCell(4);	            
	            PageloadCell.setCellValue("PageLoadTime");
	            PageloadCell.setCellStyle(excelreport.getHeaderCellStyle(workbook));
	            
	            Cell DOMloadCell = headerrow.createCell(5);
	            DOMloadCell.setCellValue("DOMLoadTime");
	            DOMloadCell.setCellStyle(excelreport.getHeaderCellStyle(workbook));
	            
	            Cell ScreenCounter = headerrow.createCell(6);
	            ScreenCounter.setCellValue("ScreenOccurances");
	            ScreenCounter.setCellStyle(excelreport.getHeaderCellStyle(workbook));

	            int rowNum = 1;
	            int Sno=1;
	            int index=0;
	            for (Map.Entry<HashMap<String, String>, ArrayList<String>> entry : dataMap.entrySet()) {
	                Row row = sheet.createRow(rowNum++);

             
	                int arraysize=entry.getValue().size();
	                double avgtime = 0;
	                if(arraysize>0) 
	                {
	             	   avgtime=calculateAverage(entry.getValue());
	                }

	                int cellNum = 0;
        
	                Cell SerialCell = row.createCell(cellNum++);
	                SerialCell.setCellValue(String.valueOf(Sno++));
	                SerialCell.setCellStyle(excelreport.getGenericCellStyleMiddle(workbook));
	                for (Map.Entry<String, String> keyEntry : entry.getKey().entrySet())
	                {
	                	Cell keyCell1 = row.createCell(cellNum++);
	                    keyCell1.setCellValue(keyEntry.getKey());
	                    keyCell1.setCellStyle(excelreport.getGenericCellStyleMiddle(workbook));
	                    Cell keyCell2 = row.createCell(cellNum++);
	                    keyCell2.setCellValue(keyEntry.getValue());
	                    keyCell2.setCellStyle(excelreport.getGenericCellStyleMiddle(workbook));
	                }

	                Cell valuesCell = row.createCell(cellNum++);
	                valuesCell.setCellValue(avgtime);
	                valuesCell.setCellStyle(excelreport.getGenericCellStyleMiddle(workbook));
	                
	                String pageloadtime=pageLoadtime.get(index);
	                
	                Cell PLTCell = row.createCell(cellNum++);
	                PLTCell.setCellValue(pageloadtime);
	                PLTCell.setCellStyle(excelreport.getGenericCellStyleMiddle(workbook));

	                
	                String domloadtime=domLoadtime.get(index);
	                
	                Cell DLTCell = row.createCell(cellNum++);
	                DLTCell.setCellValue(domloadtime);
	                DLTCell.setCellStyle(excelreport.getGenericCellStyleMiddle(workbook));
	                
	                int screenocc=arraysize;
	                
	                Cell scrOcc = row.createCell(cellNum);
	                scrOcc.setCellValue(String.valueOf(screenocc));
	                scrOcc.setCellStyle(excelreport.getGenericCellStyleMiddle(workbook));
	                index++;
	            }
	           excelreport.closeWorkBook(workbook, filePath);

	        }
		 catch (IOException e) 
		 {
	            e.printStackTrace();
	        }
	    }
//*[@class='ssp-menuItemDropDownHeader']/p
	 
	 public static double calculateAverage(ArrayList<String> numericValues) {
	        if (numericValues == null || numericValues.isEmpty()) {
	            throw new IllegalArgumentException("List is empty or null");
	        }

	        double sum = 0.0;

	        for (String stringValue : numericValues) {
	            try {
	                // Attempt to convert each string value to a double
	                double numericValue = Double.parseDouble(stringValue);
	                sum += numericValue;
	            } catch (NumberFormatException e) {
	                // Handle cases where the string cannot be converted to a double
	                logger.info("Skipping non-numeric value: " + stringValue);
	            }
	        }

	        // Calculate the average
	        return sum / numericValues.size();
	    }
	 
}
