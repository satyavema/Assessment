package satya_assessment;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

public class Schedule implements TimeofTheDay{
	private Map<Integer, ArrayList<String>> items = null;
	
	public Map<Integer, ArrayList<String>> getItems() {
		return items;
	}

	public void insertItem(int dishType, String entree){
		if(items == null) { items = new HashMap<Integer, ArrayList<String>>(); }
		
		if(items.containsKey(dishType)){
			if(items.get(dishType) !=null) {
				items.get(dishType).add(entree);
			}
		}else{
			ArrayList<String> entrees = new ArrayList<String>();
			entrees.add(entree);
			items.put(dishType, entrees);
		}
	}
	
	public String getDishType(int dishType){
		if(items != null){
			// For now, the assessment is only for one item for each dish type.
			// So returning the first indexed value.
			return items.get(dishType).get(0);
		}
		return null;
	}
}
