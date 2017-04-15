package parseToolkit;

import java.util.ArrayList;
import java.util.List;

public class GenericDataContainer {

	private List<String> headerArrayList;
	private List<GenericDataDetail> genericDataList;

	public GenericDataContainer() {
		this.headerArrayList = new ArrayList<String>();
		this.genericDataList = new ArrayList<>();
	}

	public GenericDataContainer(List<String> headerArrayList, List<GenericDataDetail> genericDataList) {
		super();
		this.headerArrayList = headerArrayList;
		this.genericDataList = genericDataList;
	}

	public List<String> getHeaderArrayList() {
		return headerArrayList;
	}

	public void setHeaderArrayList(List<String> headerArrayList) {
		this.headerArrayList = headerArrayList;
	}

	public List<GenericDataDetail> getGenericDataList() {
		return genericDataList;
	}

	public void setGenericDataList(List<GenericDataDetail> genericDataList) {
		this.genericDataList = genericDataList;
	}

	public String getHeaderAtGivenIndex(int givenIndex) {
		String headerAsString = "";

		if (getHeaderArrayList() != null && getHeaderArrayList().size() >= givenIndex) {
			headerAsString = getHeaderArrayList().get(givenIndex);
		}

		return headerAsString;
	}

	public String toString() {
		StringBuffer outputAsStringBuffer = new StringBuffer();
		if (getGenericDataList() != null) {
			GenericDataDetail currentDataDetail = null;
			List<String> currentContentArrayList = null;
			for (int gdlIndex = 0; gdlIndex < getGenericDataList().size(); gdlIndex++) {
				currentDataDetail = getGenericDataList().get(gdlIndex);
				if (currentDataDetail != null && currentDataDetail.getContentArrayList() != null) {
					currentContentArrayList = currentDataDetail.getContentArrayList();
					String currentHeader = "";
					String currentContent = "";
					for(int i = 0; i < currentContentArrayList.size(); i++) {
						currentHeader = getHeaderAtGivenIndex(i);
						currentContent = currentDataDetail.getCellContentAtGivenIndex(i);

						if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentContent)) {
							if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentHeader)) {
								outputAsStringBuffer.append(currentHeader).append(" : ");
							}
							outputAsStringBuffer.append(currentContent);
							if (i+1 < currentContentArrayList.size()) {
								outputAsStringBuffer.append("\n");
							}
						}
					}
				}
			}
		}

		return outputAsStringBuffer.toString();
	}

	public String getSpecificDataDetailAsString(int itemIndex) {

		StringBuffer outputAsStringBuffer = new StringBuffer();
		if (getGenericDataList() != null) {
			GenericDataDetail currentDataDetail = null;
			List<String> currentContentArrayList = null;
			currentDataDetail = getGenericDataList().get(itemIndex);
			if (currentDataDetail != null && currentDataDetail.getContentArrayList() != null) {
				currentContentArrayList = currentDataDetail.getContentArrayList();
				String currentHeader = "";
				String currentContent = "";
				for(int i = 0; i < currentContentArrayList.size(); i++) {
					currentHeader = getHeaderAtGivenIndex(i);
					currentContent = currentDataDetail.getCellContentAtGivenIndex(i);

					if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentContent)) {
						if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentHeader)) {
							outputAsStringBuffer.append(currentHeader).append(" : ");
						}
						outputAsStringBuffer.append(currentContent);
						if (i+1 < currentContentArrayList.size()) {
							outputAsStringBuffer.append("\n");
						}
					}
				}
			}
		}
		return outputAsStringBuffer.toString();

	}
}
