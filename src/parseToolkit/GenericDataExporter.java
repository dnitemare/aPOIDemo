package parseToolkit;

import java.util.Iterator;
import java.util.List;

public class GenericDataExporter {
	/** Prints each stored genericDataDetail item using the toString method directly to the console,
	 *  provided the resulting string is not empty.
	 * @param listOfData
	 */
	public static void printTheListOfDataToConsole(GenericDataContainer dataContainer) {
		for (int dcIndex = 0; dcIndex < dataContainer.getGenericDataList().size(); dcIndex++) {
			String currentDataAsString = dataContainer.getSpecificDataDetailAsString(dcIndex);
			if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentDataAsString)) {
				System.out.println(currentDataAsString);
				if ((dcIndex + 1) < dataContainer.getGenericDataList().size())
					System.out.println("\n---\n");
			}
		}
	}

	/** Searches the list of data for the given dataToFind, looking at the associated location that matches the nameInHeader if given.
	 * Prints the whole data element's toString content to the console if a match was found.
	 * <br>
	 * <b>Only the first match will be printed</b>
	 * @param listOfData
	 * @param dataToFind
	 * @param nameInHeader
	 */
	public static void printASpecificDataItemToConsole(GenericDataContainer dataContainer,
			String dataToFind, String nameInHeader) {

		// Check that all data is available and fail if data crucial to the rest of the code below is missing
		if (CustomHelperUtils.givenStringIsEmptyOrNull(dataToFind)) {
			System.out.println("The data given to be found was empty or null. No search was done.");
			return;
		} else if (dataContainer == null) {
			System.out.println("The object containing the data was empty or null. No search was done.");
			return;
		}

		int headerLocationInList = getHeaderIndexForGivenHeader(dataContainer, nameInHeader);
		String matchingDataAsString = getMatchingDataAsString(dataContainer, dataToFind, headerLocationInList);


		// if the matching data is not empty or null, then print it out, otherwise tell the user
		if (!CustomHelperUtils.givenStringIsEmptyOrNull(matchingDataAsString)) {
			System.out.println(matchingDataAsString);
		} else {
			System.out.println("No matches found.");
		}
	}
	/** Given the headerArrayList from the given dataContainer object as the information to be searched, 
	 * and the nameInHeader as the string to search for, 
	 * returns the index if there was a matching string in the list of headers, 
	 * or -1 if no match could be found.
	 * @param headerArrayList
	 * @param nameInHeader
	 * @return
	 */
	private static int getHeaderIndexForGivenHeader(GenericDataContainer dataContainer, String nameInHeader) {
		int headerLocationInList = -1;
		List<String> headerArrayList = dataContainer.getHeaderArrayList();

		if (!CustomHelperUtils.givenStringIsEmptyOrNull(nameInHeader) && headerArrayList != null) {

			// Loop through each item in the list of data searching for the matching header
			// Fail here and abandon header targeting if no header list was found
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
		return headerLocationInList;
	}

	/**
	 * Searches through the dataContainer's list of data looking either at the given header location in each data array,
	 * or in every item of the array if no header location was given or the header location index is not valid.
	 * <br>
	 * Stops and returns the data as a string once the first match is found in the loop of data.
	 * 
	 * @param dataContainer
	 * @param dataToFind
	 * @param headerLocationInList
	 * @return
	 */
	private static String getMatchingDataAsString(GenericDataContainer dataContainer, String dataToFind, int headerLocationInList) {
		GenericDataDetail currentDetail = null;
		String matchingDataAsString = null;
		List<GenericDataDetail> listOfData = dataContainer.getGenericDataList();
		List<String> headerArrayList = dataContainer.getHeaderArrayList();
		for(int contentIndex = 0;
				contentIndex < listOfData.size()
				&& CustomHelperUtils.givenStringIsEmptyOrNull(matchingDataAsString);
				contentIndex++) {
			currentDetail = listOfData.get(contentIndex);

			// see if header was given and found
			if (headerLocationInList > -1
					&& headerLocationInList < listOfData.size()
					/* Also ensure that there is a 1 to 1 ratio of header data and content data) */
					&& currentDetail.getContentArrayList().size() == headerArrayList.size()) {
				// valid header means we can refine the search to a specific location in the list content
				String matchingData = null;
				try {
					matchingData = currentDetail.getContentArrayList().get(headerLocationInList);
				} catch (IndexOutOfBoundsException e) {
					matchingData = null;
				}

				// If a match was found via the header lookup, use it
				if (matchingData != null) {
					matchingDataAsString = dataContainer.getSpecificDataDetailAsString(contentIndex);
				}
			} else {
				// invalid header given, so we must look at all items for a match
				List<String> currentContentList = currentDetail.getContentArrayList();
				Iterator<String> currentContentIter = currentContentList.iterator();

				while(currentContentIter.hasNext()) {
					String currentCellAsString = currentContentIter.next();
					if (dataToFind.equals(currentCellAsString)) {
						matchingDataAsString = dataContainer.getSpecificDataDetailAsString(contentIndex);
						break;
					}
				}
			}
		}
		return matchingDataAsString;
	}
}
