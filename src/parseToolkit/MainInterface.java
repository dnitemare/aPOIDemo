package parseToolkit;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class MainInterface {
	
	private HashMap<String, String> parameters = new HashMap<String, String>();
	private final String [] validParameterList = { "filename", "useheader", "tasktype", "exporttype" };
	private final String [] defaultParameterList = { "files/SpellBookExample1.xls", "Y", "exportall", "console" };
	
	public static void main(String[] args) throws IOException {
		MainInterface interfaceInstance = new MainInterface();
		// Convert the arguments into a single string we can parse in the config setup
		StringBuffer argumentsAsStringBuffer = new StringBuffer(100);
		for (int i = 0; i < args.length; i++) {
			argumentsAsStringBuffer.append(args[i]);
			if (i+1 < args.length)
				argumentsAsStringBuffer.append(" ");
		}
		interfaceInstance.configureParametersForMainInterface(argumentsAsStringBuffer.toString());
		interfaceInstance.runMainInterface();
	}
	
	private void runMainInterface() throws IOException {
		String excelFilePath = parameters.get("filename");
		boolean spreadsheetHasHeaderAtFirstRow = "Y".equals(parameters.get("useheader"));
		ExcelDataParser reader = new ExcelDataParser();
		GenericDataContainer dataContainer = reader.readDataFromExcelFile(excelFilePath, spreadsheetHasHeaderAtFirstRow);
		String exportType = parameters.get("exporttype").toLowerCase();
		String taskType = parameters.get("tasktype").toLowerCase();
		
		if ("console".equals(exportType)) {
			if ("exportall".equals(taskType))
				GenericDataExporter.printTheListOfDataToConsole(dataContainer);
			else if ("searchdemo".equals(taskType))
				GenericDataExporter.printASpecificDataItemToConsole(dataContainer, "Alarm", "Name");
		}
	}
	
	/** Set up the parameters the application will use to parse and export the file,
	 * as well as what file to use, and any other data that may be specific to the current
	 * execution of the application, based on user arguments given.
	 * <br>
	 * If no arguments were given, the default argument will be used instead
	 * @param argumentsAsString
	 */
	private void configureParametersForMainInterface(String argumentsAsString) {
		ArrayList<String> argsSplitOnDoubleDashes = null;
		if (!CustomHelperUtils.givenStringIsEmptyOrNull(argumentsAsString))
			argsSplitOnDoubleDashes = new ArrayList<String>(Arrays.asList(argumentsAsString.split("--")));
		
		// For each known parameter for the app, see if user input was given to assign a value other than the default
		String currentParameter = null;
		String currentValue = null;
		String currentUserParameter = null;
		String currentUserValueGiven = null;
		int indexOfFirstSpace = -1;
		for (int parmIndex = 0; parmIndex < validParameterList.length; parmIndex++) {
			currentParameter = validParameterList[parmIndex];
			currentValue = null;
			if (argsSplitOnDoubleDashes != null && argsSplitOnDoubleDashes.size() > 0) {
				// Search the arguments for a matching parameter in the format --Parameter value
				for (int dashIndex = 0; dashIndex < argsSplitOnDoubleDashes.size(); dashIndex++) {
					String currentUserCommand = argsSplitOnDoubleDashes.get(dashIndex);
					indexOfFirstSpace = currentUserCommand.indexOf(" ");
					if (indexOfFirstSpace != -1) {
						currentUserParameter = currentUserCommand.substring(0, indexOfFirstSpace);
						currentUserValueGiven = currentUserCommand.substring(indexOfFirstSpace);
					} else {
						currentUserParameter = null;
						currentUserValueGiven = null;
					}
					
					if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentUserValueGiven))
						currentUserValueGiven = currentUserValueGiven.trim();
					
					if (currentParameter.equals(currentUserParameter)) {
						//Validate the data given by the user
						switch(currentParameter) {
						case "useheader":
							String valueAsLowercase = CustomHelperUtils.givenStringIsEmptyOrNull(currentUserValueGiven) ? "" : currentUserValueGiven.toLowerCase();
							if ("y".equals(valueAsLowercase) || "yes".equals(valueAsLowercase))
								currentValue = "Y";
							else if ("n".equals(valueAsLowercase) || "no".equals(valueAsLowercase))
								currentValue = "N";
							else
								currentValue = null; // null will receive default value later
							break;
						default:
							currentValue = currentUserValueGiven;
						}
						
						//Remove the parameter from the user list of parms, to save processing time
						argsSplitOnDoubleDashes.remove(dashIndex);
					}
				}
			}
			
			// If the parameter was not given, or the given data was not valid, use the default value instead
			if (CustomHelperUtils.givenStringIsEmptyOrNull(currentValue))
				currentValue = defaultParameterList[parmIndex];
				
			parameters.put(currentParameter, currentValue);
		}
	}
}
