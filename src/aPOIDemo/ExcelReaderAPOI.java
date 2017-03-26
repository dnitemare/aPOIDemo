package aPOIDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelReaderAPOI {
	protected List<String> headerArrayList = null;

	private Object getCellValue(Cell cell) {
		switch (cell.getCellType()) {
		case Cell.CELL_TYPE_STRING:
			return cell.getStringCellValue();

		case Cell.CELL_TYPE_BOOLEAN:
			return cell.getBooleanCellValue();

		case Cell.CELL_TYPE_NUMERIC:
			return cell.getNumericCellValue();
		}

		return null;
	}
	
	private Workbook getWorkbook(FileInputStream inputStream, String excelFilePath)
	        throws IOException {
	    Workbook workbook = null;
	 
	    if (excelFilePath.endsWith("xlsx")) {
	        workbook = new XSSFWorkbook(inputStream);
	    } else if (excelFilePath.endsWith("xls")) {
	        workbook = new HSSFWorkbook(inputStream);
	    } else {
	        throw new IllegalArgumentException("The specified file is not Excel file");
	    }
	 
	    return workbook;
	}

	public List<GenericDataDetail> readDataFromExcelFile(String excelFilePath, boolean spreadsheetHasHeaderAtFirstRow) throws IOException {
		List<GenericDataDetail> listOfGenericData = new ArrayList<>();
		FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
		List<String> currentRowCellList = new ArrayList<String>();

		Workbook workbook = getWorkbook(inputStream, excelFilePath);
		Sheet firstSheet = workbook.getSheetAt(0);
		Iterator<Row> iterator = firstSheet.iterator();

		while (iterator.hasNext()) {
			Row nextRow = iterator.next();
			Iterator<Cell> cellIterator = nextRow.cellIterator();
			currentRowCellList = new ArrayList<String>();
			
			while (cellIterator.hasNext()) {
				Cell nextCell = cellIterator.next();
				currentRowCellList.add(getCellValue(nextCell).toString());
			}
			if (spreadsheetHasHeaderAtFirstRow && nextRow.getRowNum() == 0) {
				headerArrayList = currentRowCellList;
				continue;
			}
			GenericDataDetail dataDetail = new GenericDataDetail(headerArrayList, currentRowCellList);
			listOfGenericData.add(dataDetail);
		}

		workbook.close();
		inputStream.close();

		return listOfGenericData;
	}
}
