
package api_utilities.api_common;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.logging.Level;
import java.util.logging.Logger;

public class PoiReadExcel {

	private static final Logger logger = Logger.getLogger(PoiReadExcel.class.getName());

	// Private constructor to prevent instantiation
	private PoiReadExcel() {
		throw new IllegalStateException("Utility class");
	}

	public static Map<String, List<String>> fetchWithCondition(String sheetPath, String sheetName, List<String> whereClause) {
		Map<String, List<String>> excelMap = coreListToMap(sheetPath, sheetName);
		for (String clause : whereClause) {
			excelMap = filterByClause(excelMap, clause);
		}
		return excelMap;
	}

	private static Map<String, List<String>> filterByClause(Map<String, List<String>> excelMap, String clause) {
		Map<String, List<String>> finalMap = new HashMap<>();
		List<Integer> addIndex = new ArrayList<>();

		String[] clauseParts = clause.split("::");
		String keyClause = clauseParts[0];
		String valueClause = clauseParts[1];

		for (Map.Entry<String, List<String>> entry : excelMap.entrySet()) {
			if (entry.getKey().equalsIgnoreCase(keyClause)) {
				List<String> filteredValues = new ArrayList<>();
				filterValues(entry.getValue(), valueClause, filteredValues, addIndex);
				finalMap.put(entry.getKey(), filteredValues);
			}
		}

		addRemainingValues(excelMap, finalMap, addIndex, keyClause);
		return finalMap;
	}

	private static void filterValues(List<String> values, String clauseValue, List<String> filteredValues, List<Integer> addIndex) {
		int index = 0;
		for (String value : values) {
			if (value.equalsIgnoreCase(clauseValue)) {
				filteredValues.add(value);
				addIndex.add(index);
			}
			index++;
		}
	}

	private static void addRemainingValues(Map<String, List<String>> excelMap, Map<String, List<String>> finalMap, List<Integer> addIndex, String keyClause) {
		for (Map.Entry<String, List<String>> entry : excelMap.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase(keyClause)) {
				List<String> filteredValues = new ArrayList<>();
				for (int index : addIndex) {
					filteredValues.add(entry.getValue().get(index));
				}
				finalMap.put(entry.getKey(), filteredValues);
			}
		}
	}

	public static Map<String, List<String>> coreListToMap(String sheetPath, String sheetName) {
		List<List<String>> tempStorage = coreFetch(sheetPath, sheetName);
		if (tempStorage.isEmpty()) {
			return new HashMap<>();
		}

		Map<String, List<String>> excelMap = new HashMap<>();
		for (int colIndex = 0; colIndex < tempStorage.get(0).size(); colIndex++) {
			List<String> columnData = getColumnData(tempStorage, colIndex);
			excelMap.put(tempStorage.get(0).get(colIndex), columnData);
		}
		return excelMap;
	}

	private static List<String> getColumnData(List<List<String>> tempStorage, int colIndex) {
		List<String> columnData = new ArrayList<>();
		for (int rowIndex = 1; rowIndex < tempStorage.size(); rowIndex++) {
			try {
				columnData.add(tempStorage.get(rowIndex).get(colIndex));
			} catch (IndexOutOfBoundsException e) {
				columnData.add("");
			}
		}
		return columnData;
	}

	public static List<List<String>> coreFetch(String sheetPath, String sheetName) {
		List<List<String>> tempStorage = new ArrayList<>();

		try (FileInputStream file = new FileInputStream(new File(sheetPath));
			 XSSFWorkbook workbook = new XSSFWorkbook(file)) {

			if (!new File(sheetPath).exists()) {
				logger.warning("File NOT found: {}");
				logger.warning(sheetPath);
				return tempStorage;
			}

			XSSFSheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> rowIterator = sheet.iterator();

			if (rowIterator.hasNext()) {
				tempStorage.add(fetchRow(rowIterator.next()));
			}

			while (rowIterator.hasNext()) {
				tempStorage.add(fetchRow(rowIterator.next()));
			}

		} catch (IOException e) {
			logger.log(Level.WARNING, "Error while fetching details from the file", e);
		}

		return tempStorage;
	}

	private static List<String> fetchRow(Row row) {
		List<String> rowValues = new ArrayList<>();
		for (int cellIndex = 0; cellIndex < row.getLastCellNum(); cellIndex++) {
			Cell cell = row.getCell(cellIndex);
			if (cell != null) {
				rowValues.add(getCellValue(cell));
			} else {
				rowValues.add("");
			}
		}
		return rowValues;
	}

	private static String getCellValue(Cell cell) {
		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				return Long.toString((long) cell.getNumericCellValue());
			default:
				return "";
		}
	}
}
