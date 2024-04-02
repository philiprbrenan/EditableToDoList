import java.util.ArrayList;
import java.util.Scanner;
import java.io.*;


public class ToDoList {
	
	public static void main(String[] args) {
		// create the array list to hold list items
		ArrayList<String> toDoList = new ArrayList<String>();	
		
		try {
		    // open the file to extract any previously made to do list
		    FileInputStream file = new FileInputStream("ToDoList.txt");
		    Scanner input = new Scanner(file);
		    // read and print file contents
		    while (input.hasNextLine()) {
                String line = input.nextLine();
                System.out.println(line);
                // removing the number from each item (if any) and copying it to the new, editable list array
                if(line.contains(".")) {
                	int startPoint = line.indexOf(".");
                	// item in list starts two above the index of the .
                	startPoint += 2;
                	// extracting just the item from the file line and adding it to editable array
                	String fullItem = line.substring(startPoint);
                	toDoList.add(fullItem);
                }
		    }
		    System.out.println("\n");
		    input.close(); 
		 // catches if file is not found
		} catch (FileNotFoundException e) {
		    System.out.println("File could not be opened!");
		}		
		
		
		System.out.println("Welcome to your to do list!");
		// create user input scanner to edit the array list
		Scanner in = new Scanner (System.in);
		String action = "";
		// instructions on what to input to edit the array list
		System.out.println("If you want to add an item, press \"a\"");
		System.out.println("If you want to remove an item, press \"r\"");
		System.out.println("If you want to quit and save your list to a file press \"q\"\n");
				
		// loop to edit the arraylist
		while(true) {		
			action = in.nextLine();
			int length = toDoList.size();			
			// adding to the array list when "a" is input
			if(action.equals("a")) {
				System.out.println("Enter the item to add to the list: ");
				String item = "";
				item = in.nextLine();
				toDoList.add(item);
				// inputing priority level (high priority items will show up first in the to do list)
				System.out.println("Is this high or low priority? (enter \"h\" or \"l\")");
				String priority = "";
				priority = in.nextLine();
				if(priority.equals("h")) {
					// adding (!) to the end of the item to indicate that it is high priority
					int size = toDoList.size();
					// make a copy of the item that needs (!) added
					String task = toDoList.get(size-1);
					toDoList.add(size, task + "(!)");
					// removing the copy without the (!)
					toDoList.remove(size-1);
					// sort and print methods
					sortList(toDoList);
					System.out.println(printList(toDoList));
				}
				// if string is input as low priority, nothing is added
				else if(priority.equals("l")) {
					//print method
					System.out.println(printList(toDoList));
				}
				else {
				// if the priority input is not "h" or "l"
					System.out.println("Not valid option");
				}
			}
			
			// removing from the array list when "r" is input
			else if(action.equals("r") ) {
				System.out.println("Enter the number of the item you want to remove (or press 0 to exit):");
				int removal = 0;
				removal = in.nextInt();
				// user can press 0 if they don't want to remove any item
				if(removal == 0) {
					System.out.println(printList(toDoList));
				}
				else if((removal <= length) && (removal > 0)) {
					// removes the item entered using its index (the index being it's designated number minus 1)
					toDoList.remove(removal-1);
					System.out.println(printList(toDoList));
				}
				else {
					System.out.println("Not a valid option");
				}
			}
		
			// quit program and save/overwrite any edits to the toDoList.java file
			// lines 121 & 122 used additional notes used from https://ianfinlayson.net/class/cpsc220/notes/12-files
			else if(action.equals("q") ) {
				try {
					PrintWriter file = new PrintWriter("toDoList.java");
					file.print(printList(toDoList));
					file.close();
					System.out.println("Finished: your list has been saved to toDoList.java!");
					break;
				// catches if a file is not found
				} catch (FileNotFoundException e) {
	            System.out.println("Error, could not open file");
				}
			}	
		}
	}
	
	
	// method that captures the full to do list in a string variable in order to be printed
	// loops through the array already holding the items
	public static String printList (ArrayList<String> array) {		
		String totalList = "\nTo Do List:\n";		
		int size = array.size();
		// looping through the array holding the list items and adding each full item to the string version of the list
		for (int i=0; i<size; i++) {
			// adding the number that is added before the task
			Integer num = i+1;
			String stringNum = num.toString();
			String fullItem = (stringNum + ". " + array.get(i));
			// adding full item to the string version of the list
			totalList = totalList + fullItem + "\n";
		}	
		return totalList;
	}
	
	// method that loops through the list and sorts priority items to the beginning of the list
	public static void sortList(ArrayList<String> array) {
		int size = array.size();
		for (int i=0; i<size; i++) {
			String item = array.get(i);
			if(item.contains("(!)")) {
				array.remove(item);
				array.add(0, item);				
			}
		}		
	}
	
}