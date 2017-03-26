package aPOIDemo;

import java.util.ArrayList;
import java.util.List;

public class GenericDataDetail {
	private List<String> headerArrayList = new ArrayList<String>();
	private List<String> contentArrayList = new ArrayList<String>();
	
	public GenericDataDetail(List<String> contentArrayList) {
		super();
		this.headerArrayList = null;
		this.contentArrayList = contentArrayList;
	}
	
	public GenericDataDetail(List<String> headerArrayList, List<String> contentArrayList) {
		super();
		this.headerArrayList = headerArrayList;
		this.contentArrayList = contentArrayList;
	}
	
	 public List<String> getHeaderArrayList() {
		return headerArrayList;
	}

	public void setHeaderArrayList(List<String> headerArrayList) {
		this.headerArrayList = headerArrayList;
	}

	public List<String> getContentArrayList() {
		return contentArrayList;
	}

	public void setContentArrayList(List<String> contentArrayList) {
		this.contentArrayList = contentArrayList;
	}

	public String toString() {
        StringBuffer outputAsStringBuffer = new StringBuffer();
        if (contentArrayList != null) {
        	String currentHeader = "";
        	String currentContent = "";
        	for(int i = 0; i < contentArrayList.size(); i++) {
        		currentHeader = getHeaderAtGivenIndex(i);
        		currentContent = getCellContentAtGivenIndex(i);
        		
        		if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentContent)) {
        			if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentHeader)) {
        				outputAsStringBuffer.append(currentHeader).append(" : ");
        			}
        			outputAsStringBuffer.append(currentContent);
        			if (i+1 < contentArrayList.size()) {
        				outputAsStringBuffer.append("\n");
        			}
        		}
        	}
        }
        
        return outputAsStringBuffer.toString();
    }
	 
	 public String getHeaderAtGivenIndex(int givenIndex) {
		 String headerAsString = "";
		 
		 if (getHeaderArrayList() != null && getHeaderArrayList().size() >= givenIndex) {
			 headerAsString = getHeaderArrayList().get(givenIndex);
		 }
		 
		 return headerAsString;
	 }
	 
	 public String getCellContentAtGivenIndex(int givenIndex) {
		 String contentAsString = "";
		 
		 if (getContentArrayList() != null && getContentArrayList().size() >= givenIndex) {
			 contentAsString = getContentArrayList().get(givenIndex);
		 }
		 
		 return contentAsString;
	 }
}
