package parseToolkit;

import java.util.ArrayList;
import java.util.List;

public class GenericDataDetail {
	private List<String> contentArrayList = new ArrayList<String>();
	
	public GenericDataDetail(List<String> contentArrayList) {
		super();
		this.contentArrayList = contentArrayList;
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
        	String currentContent = "";
        	for(int i = 0; i < contentArrayList.size(); i++) {
        		currentContent = getCellContentAtGivenIndex(i);
        		
        		if (!CustomHelperUtils.givenStringIsEmptyOrNull(currentContent)) {
        			outputAsStringBuffer.append(currentContent);
        			if (i+1 < contentArrayList.size()) {
        				outputAsStringBuffer.append("\n");
        			}
        		}
        	}
        }
        
        return outputAsStringBuffer.toString();
    }
	 
	 public String getCellContentAtGivenIndex(int givenIndex) {
		 String contentAsString = "";
		 
		 if (getContentArrayList() != null && getContentArrayList().size() >= givenIndex) {
			 contentAsString = getContentArrayList().get(givenIndex);
		 }
		 
		 return contentAsString;
	 }
}
