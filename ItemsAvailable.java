package satya_assessment;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Scanner;

public class ItemsAvailable {
	
	private void addItems(Map<String, TimeofTheDay> menus) {
		// items being added for the morning menu
		System.out.println("Morning menu items being added...");
		TimeofTheDay morningMenu = new Schedule();
		morningMenu.insertItem(1, "Eggs");
		morningMenu.insertItem(2, "Toast");
		morningMenu.insertItem(3, "Cofee");
		morningMenu.insertItem(4, "");
		
		menus.put("morning", morningMenu);
		
		// items being added for the dinner menu
		System.out.println("Dinner menu items being added...");
		TimeofTheDay nightMenu = new Schedule();
		nightMenu.insertItem(1, "Steak");
		nightMenu.insertItem(2, "Potato");
		nightMenu.insertItem(3, "Wine");
		nightMenu.insertItem(4, "Cake");
		
		menus.put("night", nightMenu);
		
		System.out.println("Menu for morning and night are ready... lets proceed");
	}
	public static void main(String[] args) {
		
		// Before doing any kind of processing, lets have some dishTypes and items filled in.
		// In real time we will have databases to retrieve the item details, but for now let add some hardcoded.
		ItemsAvailable classObj = new ItemsAvailable();
		Map<String, TimeofTheDay> menus = new HashMap<String, TimeofTheDay>(); 
		
		classObj.addItems(menus);
		Scanner sc = new Scanner(System.in);
		String order = sc.next();
		String[] splitOrder = order.split(",");
		if(splitOrder.length < 2){
			System.out.println("Your order is not complete... please check and order again");
			return;
		}
		String menu = splitOrder[0];
		if(menu.matches("\\d+")){
			System.out.println("Wrong input provided... please provide valid inputs");
			return;
		}
		
		List<String> items = new ArrayList<String>(Arrays.asList(splitOrder));  
		//Items is a list variable which will have all the data from the console
		//Remove the first value in the list... as it represents the time of menu
		try{
			
				items.remove(0);
				Collections.sort(items);
			
		}catch(Exception e){
			System.out.println("Wrong input provided..." + e);
		}
		
		if(!menus.containsKey(menu)){
			System.out.println("Ordered time menu is not available... sorry for the inconvenience");
			return;
		}
		
		//Getting the TimeOfTheDay from the Items.
		Iterator<Entry<String, TimeofTheDay>> it =  menus.entrySet().iterator();
		Schedule mobj = null;
		while(it.hasNext()){
			Map.Entry<String, TimeofTheDay> timeEntry = (Map.Entry<String, TimeofTheDay>) it.next();
			if(timeEntry.getKey().equalsIgnoreCase(menu)){
				mobj = (Schedule) timeEntry.getValue();
			}
		}
		//To repeat Coffee and Potato
		int dishRepeated;
		if(menu.equalsIgnoreCase("morning"))
			dishRepeated = 3;
		else
			dishRepeated = 2;
		
		// Order acceptance should be in the following order
		// Time of the day (morning or night) and the list of dish types
		boolean dishNotAvailable = false;
		String entree = "";
		Map<String, Integer> itemsTobeDisplayed = new LinkedHashMap<String, Integer>();
		try{
			for(int i=0; i<items.size(); i++){
				boolean dishType = mobj.getItems().containsKey(Integer.parseInt(items.get(i)));
				if(!dishType || 
						(dishType && (mobj.getDishType(Integer.parseInt(items.get(i))) == ""))) {
					dishNotAvailable = true;
					continue;
				}
				
				if(!itemsTobeDisplayed.containsKey(mobj.getDishType(Integer.parseInt(items.get(i))))){
					itemsTobeDisplayed.put(mobj.getDishType(Integer.parseInt(items.get(i))), 1);
				}
				else{
					itemsTobeDisplayed.put(mobj.getDishType(Integer.parseInt(items.get(i))), itemsTobeDisplayed.get(mobj.getDishType(Integer.parseInt(items.get(i)))) + 1);
				}
				if(Integer.parseInt(items.get(i)) == dishRepeated){
					entree = mobj.getDishType(Integer.parseInt(items.get(i)));
				}
			}
		}catch(NullPointerException e){
			System.out.println(">>> Given input is not proper... please check");
		}
		
		Iterator<Entry<String, Integer>> its =  itemsTobeDisplayed.entrySet().iterator();
		while(its.hasNext()){
			Map.Entry<String, Integer> entry = (Map.Entry<String, Integer>) its.next();
			if((entry.getValue() > 1) && !(entry.getKey().equals(entree))) {
				dishNotAvailable = true;
				continue;
			}
			//Displaying the items after getting matched
			
			System.out.print(entry.getKey());
			if((entry.getKey().equals(entree)) && (entry.getValue() > 1))
				System.out.print("(X" + entry.getValue() + "), ");    //Multiplier for Coffee and Potato
			
			if(entry.getValue() == 1)
				System.out.print(", "); // Separator for each item in the list
		}
		
		if(dishNotAvailable)
			System.out.println(", error");  // Displaying error when condition fails i.e. based on boolean value
		
		System.out.println();
		System.out.println("=====Thanks for your order=====");
	}

}
