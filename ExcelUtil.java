package api_utilities.api_common;

import org.apache.poi.ss.usermodel.Cell;
import java.io.File;
import java.io.FileInputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.swing.JOptionPane;

public class ExcelUtil {

	// Private constructor to prevent instantiation
	private ExcelUtil() {
		}

	public static ExcelUtil getInstance() {
		return new ExcelUtil();
	}

	private static final Logger logger =LoggerFactory.getLogger(ExcelUtil.class.getName());

	// Private constructor to hide the implicit public one.


	public static Map<String, List<String>> fetchWithCondition(String sheetPath, String sheetName, List<String> whereClause) {
		Map<String, List<String>> excelMap = coreListToMap(sheetPath, sheetName);

		for (String clause : whereClause) {
			excelMap = applyClause(excelMap, clause);
		}

		return excelMap;
	}

	private static Map<String, List<String>> applyClause(Map<String, List<String>> excelMap, String clause) {
		String[] parts = clause.split("::");
		String key = parts[0];
		String value = parts[1];

		Map<String, List<String>> filteredMap = new HashMap<>();
		List<Integer> addIndex = new ArrayList<>();

		// Process entries matching the clause
		processMatchingEntries(excelMap, key, value, addIndex, filteredMap);

		// Process entries not matching the clause
		processNonMatchingEntries(excelMap, key, addIndex, filteredMap);

		return filteredMap;
	}

	private static void processMatchingEntries(Map<String, List<String>> excelMap, String key, String value,
											   List<Integer> addIndex, Map<String, List<String>> filteredMap) {
		for (Map.Entry<String, List<String>> entry : excelMap.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(key)) {
				List<String> vals = new ArrayList<>();
				int index = 0;
				for (String val : entry.getValue()) {
					if (val.equalsIgnoreCase(value)) {
						vals.add(val);
						addIndex.add(index);
					}
					index++;
				}
				filteredMap.put(entry.getKey(), vals);
			}
		}
	}
	private static void processNonMatchingEntries(Map<String, List<String>> excelMap, String key,
												  List<Integer> addIndex, Map<String, List<String>> filteredMap) {
		for (Map.Entry<String, List<String>> entry : excelMap.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase(key)) {
				List<String> vals = new ArrayList<>();
				for (int index : addIndex) {
					if (index < entry.getValue().size()) {
						vals.add(entry.getValue().get(index));
					}
				}
				filteredMap.put(entry.getKey(), vals);
			}
		}
	}
	public static Map<String, List<String>> coreListToMap(String sheetPath, String sheetName) {
		List<List<String>> tempStorage = coreFetch(sheetPath, sheetName);

		// Return an empty HashMap if tempStorage is empty
		if (tempStorage.isEmpty()) {
			return new HashMap<>();
		}

		Map<String, List<String>> excelMap = new HashMap<>();
		List<List<String>> tempList = new ArrayList<>();

		for (int j = 0; j < tempStorage.get(0).size(); j++) {
			List<String> eachCol = new ArrayList<>();
			for (int i = 1; i < tempStorage.size(); i++) {
				try {
					eachCol.add(tempStorage.get(i).get(j));
				} catch (IndexOutOfBoundsException e) {
					eachCol.add("");
				}
			}
			tempList.add(eachCol);
		}

		for (int i = 0; i < tempList.size(); i++) {
			excelMap.put(tempStorage.get(0).get(i), tempList.get(i));
		}
		return excelMap;
	}
	public static List<List<String>> coreFetch(String sheetPath, String sheetName) {
		List<List<String>> tempStorage = new ArrayList<>();

		// Check if file exists
		File file = new File(sheetPath);
		if (!file.exists()) {
			JOptionPane.showConfirmDialog(null, "File NOT found: " + sheetPath, "Warning", JOptionPane.WARNING_MESSAGE);
			return tempStorage;
		}

		// Try-with-resources for automatic resource management
		try (FileInputStream fileInputStream = new FileInputStream(file);
			 XSSFWorkbook workbook = new XSSFWorkbook(fileInputStream)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> rowIterator = sheet.iterator();

			// Process header row
			if (rowIterator.hasNext()) {
				List<String> headers = processRow(rowIterator.next());
				tempStorage.add(headers);
			}

			// Process remaining rows
			while (rowIterator.hasNext()) {
				List<String> rowValues = processRow(rowIterator.next());
				tempStorage.add(rowValues);
			}

		} catch (Exception e) {
			logger.error("{} web driver is not quit",e.getMessage());
		}

		return tempStorage;
	}

	public static List<String> processRow(Row row) {
		List<String> rowValues = new ArrayList<>();
		Iterator<Cell> cellIterator = row.cellIterator();
		int columnIndex = 0;
		int numOfHeaders = getNumberOfHeaders(row);

		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();
			// Ensure proper alignment
			while (columnIndex < cell.getColumnIndex()) {
				rowValues.add("");
				columnIndex++;
			}
			columnIndex = cell.getColumnIndex() + 1;

			rowValues.add(getCellValue(cell));
		}

		// Fill remaining columns with empty values if not enough cells are present
		while (rowValues.size() < numOfHeaders) {
			rowValues.add("");
		}

		return rowValues;
	}
	public static int getNumberOfHeaders(Row row) {
		int numOfHeaders = 0;
		Iterator<Cell> cellIterator = row.cellIterator();
		while (cellIterator.hasNext()) {
			cellIterator.next();  // Just iterate to count cells
			numOfHeaders++;
		}
		return numOfHeaders;
	}

	public static String getCellValue(Cell cell) {
		if (cell == null) {
			return "";
		}

		switch (cell.getCellType()) {
			case STRING:
				String cellValue = cell.getStringCellValue();
				return (cellValue.equalsIgnoreCase("n\\\\a") ||
						cellValue.equalsIgnoreCase("n/a") ||
						cellValue.equalsIgnoreCase("n//a")) ? "" : cellValue;
			case NUMERIC:
				return Long.toString((long) (cell.getNumericCellValue()));
			default:
				return "";
		}
	}
}