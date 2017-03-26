package aPOIDemo;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class MainInterfaceAPOI {
	protected List<String> headerArrayList = null;
	protected List<String> contentArrayList = null;

	public static void main(String[] args) {
		//headerArrayList = new ArrayList<String>();
		//contentArrayList = new ArrayList<String>();
		
	}

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
	
	public List<GenericDataDetail> readBooksFromExcelFile(String excelFilePath) throws IOException {
	    List<GenericDataDetail> listOfGenericData = new ArrayList<>();
	    FileInputStream inputStream = new FileInputStream(new File(excelFilePath));
	 
	    Workbook workbook = new XSSFWorkbook(inputStream);
	    Sheet firstSheet = workbook.getSheetAt(0);
	    Iterator<Row> iterator = firstSheet.iterator();
	 
	    while (iterator.hasNext()) {
	        Row nextRow = iterator.next();
	        Iterator<Cell> cellIterator = nextRow.cellIterator();
	        GenericDataDetail dataDetail = new GenericDataDetail(null);
	 
	        while (cellIterator.hasNext()) {
	            Cell nextCell = cellIterator.next();
	            if (nextRow.getRowNum() == 1) {
	            	headerArrayList.add(getCellValue(nextCell).toString());
	            } else {
	            	contentArrayList.add(getCellValue(nextCell).toString());
	            }
	            
	        }
	        listOfGenericData.add(dataDetail);
	    }
	 
	    workbook.close();
	    inputStream.close();
	 
	    return listOfGenericData;
	}
}
