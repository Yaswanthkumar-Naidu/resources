package api_utilities.api_common;

import java.io.File;
import java.io.FileInputStream;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

public class ApiPoiReadExcel {

	private static final Logger logger = Logger.getLogger(ApiPoiReadExcel.class.getName());

	// Private constructor to hide the implicit public one
	private ApiPoiReadExcel() {
		throw new IllegalStateException("Utility class");
	}

	public static Map<String, ArrayList<String>> fetchWithCondition(String sheetPath, String sheetName, List<String> whereClause) {
		Map<String, ArrayList<String>> excelMap = coreListToMap(sheetPath, sheetName);
		for (String clause : whereClause) {
			excelMap = applyWhereClause(excelMap, clause);
		}
		return excelMap;
	}

	private static Map<String, ArrayList<String>> applyWhereClause(Map<String, ArrayList<String>> excelMap, String clause) {
		HashMap<String, ArrayList<String>> finalMap = new HashMap<>();
		ArrayList<Integer> addIndex = new ArrayList<>();
		String[] clauseParts = clause.split("::");

		for (Map.Entry<String, ArrayList<String>> entry : excelMap.entrySet()) {
			int k = 0;
			if (entry.getKey().equalsIgnoreCase(clauseParts[0])) {
				ArrayList<String> vals = new ArrayList<>();
				for (String val : new ArrayList<>(entry.getValue())) {
					if (val.equalsIgnoreCase(clauseParts[1])) {
						vals.add(val);
						addIndex.add(k);
					}
					k++;
				}
				finalMap.put(entry.getKey(), vals);
			}
		}

		updateNonMatchingKeys(excelMap, finalMap, clauseParts[0], addIndex);
		return finalMap;
	}

	private static void updateNonMatchingKeys(Map<String, ArrayList<String>> excelMap, HashMap<String, ArrayList<String>> finalMap,
											  String clauseKey, ArrayList<Integer> addIndex) {
		for (Map.Entry<String, ArrayList<String>> entry : excelMap.entrySet()) {
			if (!entry.getKey().equalsIgnoreCase(clauseKey)) {
				ArrayList<String> vals = new ArrayList<>();
				for (int add : addIndex) {
					vals.add(entry.getValue().get(add));
				}
				finalMap.put(entry.getKey(), vals);
			}
		}
	}

	public static Map<String, ArrayList<String>> coreListToMap(String sheetPath, String sheetName) {
		List<ArrayList<String>> tempStorage = coreFetch(sheetPath, sheetName);


		HashMap<String, ArrayList<String>> excelMap = new HashMap<>();
		ArrayList<ArrayList<String>> tempList = transposeRowsToColumns(tempStorage);

		for (int i = 0; i < tempList.size(); i++) {
			excelMap.put(tempStorage.get(0).get(i), tempList.get(i));
		}
		return excelMap;
	}

	private static ArrayList<ArrayList<String>> transposeRowsToColumns(List<ArrayList<String>> tempStorage) {
		ArrayList<ArrayList<String>> tempList = new ArrayList<>();
		for (int j = 0; j < tempStorage.get(0).size(); j++) {
			ArrayList<String> eachCol = new ArrayList<>();
			for (int i = 1; i < tempStorage.size(); i++) {
				try {
					eachCol.add(tempStorage.get(i).get(j));
				} catch (IndexOutOfBoundsException e) {
					eachCol.add("");
				}
			}
			tempList.add(eachCol);
		}
		return tempList;
	}

	@SuppressWarnings("deprecation")
	public static List<ArrayList<String>> coreFetch(String sheetPath, String sheetName) {
		ArrayList<ArrayList<String>> tempStorage = new ArrayList<>();

		if (!new File(sheetPath).exists()) {
			JOptionPane.showConfirmDialog(null, "File NOT found: " + sheetPath, "Warning", 2);
			return tempStorage;
		}

		try (FileInputStream file = new FileInputStream(new File(sheetPath));
			 XSSFWorkbook workbook = new XSSFWorkbook(file)) {

			XSSFSheet sheet = workbook.getSheet(sheetName);
			Iterator<Row> rowIterator = sheet.iterator();

			if (rowIterator.hasNext()) {
				tempStorage.add(fetchRowHeaders(rowIterator));
			}

			while (rowIterator.hasNext()) {
				tempStorage.add(fetchRowData(rowIterator, tempStorage.get(0).size()));
			}

		} catch (Exception e) {
			logger.log(Level.WARNING, "Error while fetching details from TestCase file", e);
		}
		return tempStorage;
	}

	private static ArrayList<String> fetchRowHeaders(Iterator<Row> rowIterator) {
		ArrayList<String> rowWise = new ArrayList<>();
		Row row = rowIterator.next();
		Iterator<Cell> cellIterator = row.cellIterator();

		int i = 0;
		while (cellIterator.hasNext()) {
			Cell cell = cellIterator.next();

			while (i != cell.getColumnIndex()) {
				rowWise.add("");
				i++;
			}

			rowWise.add(getCellValueAsString(cell));
			i++;
		}
		return rowWise;
	}

	private static ArrayList<String> fetchRowData(Iterator<Row> rowIterator, int numOfHeaders) {
		ArrayList<String> rowWise = new ArrayList<>();
		Row row = rowIterator.next();

		for (int cellNumber = 0; cellNumber < numOfHeaders; cellNumber++) {
			Cell cell = row.getCell(cellNumber);
			rowWise.add(cell != null ? getCellValueAsString(cell) : "");
		}
		return rowWise;
	}

	private static String getCellValueAsString(Cell cell) {
		switch (cell.getCellType()) {
			case STRING:
				return cell.getStringCellValue();
			case NUMERIC:
				return Long.toString((long) (cell.getNumericCellValue()));
			default:
				return "";
		}
	}
}
