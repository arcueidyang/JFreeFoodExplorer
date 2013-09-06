package analysis;

import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;
import java.util.regex.Pattern;

public class KeywordExtracter {

	private static final String KEY_WORD_PATH = "resources.keyWords";
	private static final String BUILDING_NAME_PATH = "resources.buildings";
	
	
	private ResourceBundle myKeywords;
	private ResourceBundle myBuildingNames;
	private Pattern myTimePattern;
	
	
	public KeywordExtracter() {
		myKeywords = ResourceBundle.getBundle(KEY_WORD_PATH);
		myBuildingNames = ResourceBundle.getBundle(BUILDING_NAME_PATH);
		myTimePattern = Pattern.compile("\\d[0-2]?\\s*\\d\\s*:\\s*\\d{2}\\s*[pPaA]?[mM]?");
	}
	
	public boolean verifyTime(String time) {
		return myTimePattern.matcher(time).matches();
	}
	
	public boolean containKeyword(String info) {
		for(String str : myKeywords.keySet()) {
			if(info.contains(str)) return true;
		}
		return false;
	}
	
	public String[] getBuilding(String info) {
        List<String> result = new ArrayList<String>();
        int position = 0;
        for(String str : myBuildingNames.keySet()) {
            position = info.indexOf(str);
            if(position != -1) {
            	result.add(getBuildingInformation(info, position));
            }
        }
        return (String[])result.toArray();
	}
	
	
	public String getBuildingInformation(String info, int startIndex) {
	    int endIndex = info.indexOf(" ", startIndex);
	    for(int i = 0; i < 4; i ++) {
	    	endIndex = info.indexOf(" ", endIndex + 1);
	    }
	    return info.substring(startIndex, endIndex);
	}
	
	
	
	
}
