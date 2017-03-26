package aPOIDemo;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

public class MainInterfaceAPOI {
	public static void main(String[] args) throws IOException {
		String excelFilePath = "files/SpellBookExample1.xls";
		boolean spreadsheetHasHeaderAtFirstRow = false;
		ExcelReaderAPOI reader = new ExcelReaderAPOI();
		List<GenericDataDetail> listOfData = reader.readDataFromExcelFile(excelFilePath, spreadsheetHasHeaderAtFirstRow);
		
		Iterator<GenericDataDetail> iter = listOfData.iterator();
		while(iter.hasNext()) {
			String currentDataAsString = iter.next().toString();
			if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentDataAsString)) {
				System.out.println(currentDataAsString);
				if (iter.hasNext())
					System.out.println("\n---\n");
			}
		}
	}
}
