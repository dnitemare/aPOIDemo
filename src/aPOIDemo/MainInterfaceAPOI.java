package aPOIDemo;

import java.io.IOException;
import java.util.Iterator;
import java.util.List;

import javax.swing.text.Utilities;

public class MainInterfaceAPOI {

	public static void main(String[] args) throws IOException {
		String excelFilePath = "files/SpellBookExample1.xls";
		boolean spreadsheetHasHeaderAtFirstRow = true;
		ExcelReaderAPOI reader = new ExcelReaderAPOI();
		List<GenericDataDetail> listOfData = reader.readDataFromExcelFile(excelFilePath, spreadsheetHasHeaderAtFirstRow);

		printTheListOfDataToConsole(listOfData);
		//printASpecificDataItemToConsole(listOfData, "Alarm", "Name");
	}

	/** Prints each stored genericDataDetail item using the toString method directly to the console,
	 *  provided the resulting string is not empty.
	 * @param listOfData
	 */
	public static void printTheListOfDataToConsole(List<GenericDataDetail> listOfData) {
		Iterator<GenericDataDetail> iter = listOfData.iterator();
		while(iter.hasNext()) {
			GenericDataDetail currentDetail = iter.next();
			String currentDataAsString = currentDetail.toString();
			if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentDataAsString)) {
				System.out.println(currentDataAsString);
				if (iter.hasNext())
					System.out.println("\n---\n");
			}
		}
	}

	/** Searches the list of data for the given dataToFind, looking only at the associated location that matches the nameInHeader if given.
	 * Prints the whole data element's toString content to the console if a match was found. *Only the first match will be printed*
	 * @param listOfData
	 * @param dataToFind
	 * @param nameInHeader
	 */
	public static void printASpecificDataItemToConsole(List<GenericDataDetail> listOfData,
			String dataToFind, String nameInHeader) {

		// Nothing can be found if empty, so tell the user this and stop
		if (CustomHelperUtils.givenStringIsEmptyOrNull(dataToFind)) {
			System.out.println("The data given to be found was empty or null. No search was done.");
			return;
		}

		int headerLocationInList = -1;
		// Loop through to find the header location if one was given.
		// If it is found it will be used to filter on later.
		Iterator<GenericDataDetail> iter = listOfData.iterator();
		if (!CustomHelperUtils.givenStringIsEmptyOrNull(nameInHeader)) {
			List<String> headerArrayList;
			while(iter.hasNext() && headerLocationInList == -1) {
				GenericDataDetail currentDetail = iter.next();
				headerArrayList = currentDetail.getHeaderArrayList();

				Iterator<String> headerIter = headerArrayList.iterator();
				String currentHeaderString = "";
				while(headerIter.hasNext()) {
					currentHeaderString = headerIter.next();
					if (nameInHeader.equals(currentHeaderString)) {
						headerLocationInList = headerArrayList.indexOf(currentHeaderString);
						break;
					}
				}
			}
		}

		// loop through the whole list of data to find the matching data if it exists.
		Iterator<GenericDataDetail> contentIter = listOfData.iterator();
		String matchingDataAsString = null;
		while(contentIter.hasNext() && matchingDataAsString == null) {
			GenericDataDetail currentDetail = contentIter.next();

			// see if header was given and found
			if (headerLocationInList > -1 && headerLocationInList < listOfData.size()) {
				// valid header means we can refine the search to a specific location in the list content
				String matchingData = null;
				try {
					matchingData = currentDetail.getContentArrayList().get(headerLocationInList);
				} catch (IndexOutOfBoundsException e) {
					matchingData = null;
				}
				if (matchingData != null) {
					matchingDataAsString = currentDetail.toString();
				}
			} else {
				// invalid header given, so we must look at all items for a match
				List<String> currentContentList = currentDetail.getContentArrayList();
				Iterator<String> currentContentIter = currentContentList.iterator();

				while(currentContentIter.hasNext()) {
					String currentCellAsString = currentContentIter.next();
					if (dataToFind.equals(currentCellAsString)) {
						matchingDataAsString = currentDetail.toString();
						break;
					}
				}
			}
		}
		
		// if the matching data is not empty or null, then print it out, otherwise tell the user
		if (!CustomHelperUtils.givenStringIsEmptyOrNull(matchingDataAsString)) {
			System.out.println(matchingDataAsString);
		} else {
			System.out.println("No matches found.");
		}
	}
}
