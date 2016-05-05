package Main;


import java.util.List;
import java.util.Scanner;

public class NonGUIMain {
	static Scanner sc = new Scanner(System.in);
	static DBMethods dbm = new DBMethods();
	
	public static void main(String[] args) {
		
		int choice = 0;
		int subChoice = 0;
		String option;
		
		do{
			menu();
			choice = sc.nextInt();
			sc.nextLine();
			switch(choice){
			
			case 1: //booking
				bookShip();
				break;
			case 2: //report
				getReport();
				break;
			case 3: //staff
				option = "Staff";
				
				do{
					subMenu(option);
					subChoice = sc.nextInt();
					sc.nextLine();
					switch(subChoice){
					
					case 1: //search
						searchStaff();
						break;
						
					case 2: //add
						addStaff();
						break;
						
					case 3: //remove
						removeStaff();
						break;
						
					case 4: //return
						break;
						
					default:
						System.out.println("Wrong choice! Try again!");
					break;
					}
					
				}while(subChoice!=4);
				
				break;
			case 4: //trucks
				option = "Trucks";
				do{
					subMenu(option);
					subChoice = sc.nextInt();
					sc.nextLine();
					switch(subChoice){
					
					case 1: //search för ok truckar 
						getOkTrucks();
						break;
						
					case 2: //add
						addTrucks();
						break;
						
					case 3: //remove
						removeTrucks();
						break;
						
					case 4: //return
						break;
						
					default:
						System.out.println("Wrong choice! Try again!");
					break;
					}
					
				}while(subChoice!=4);
				break;
				
			case 5: //ships
				option = "Ships";
				do{
					subMenu(option);
					subChoice = sc.nextInt();
					sc.nextLine();
					switch(subChoice){
					
					case 1: 
						searchShip();
						break;
						
					case 2: //add
						addShip();
						break;
						
					case 3: //remove
						removeShip();
						break;
						
					case 4: //return
						break;
						
					default:
						System.out.println("Wrong choice! Try again!");
					break;
					}
					
				}while(subChoice!=4);
				break;
				
			case 6:
				System.out.println("Thank you and Good bye!");
				break;
			default:
				System.out.println("Wrong choice! Try again!");
			break;
			}
			
		}while(choice!=6);
		
		sc.close();

	}
	
	public static void menu(){
		System.out.println("Booking Ships");
		System.out.println("1. Booking");
		System.out.println("2. Report");
		System.out.println("3. Staff");
		System.out.println("4. Trucks");
		System.out.println("5. Ships");
		System.out.println("6. Quit");
		System.out.println("Please, enter your choice!");
	}
	
	public static void subMenu(String option){
		if(option.equals("Trucks")){
			System.out.println(option);
			System.out.println("1. Search trucks with OK status ");
			System.out.println("2. Add");
			System.out.println("3. Remove");
			System.out.println("4. Go to main menu");
			System.out.println("Please, enter your choice!");
		}
		else{
			System.out.println(option);
			System.out.println("1. Search ");
			System.out.println("2. Add");
			System.out.println("3. Remove");
			System.out.println("4. Go to main menu");
			System.out.println("Please, enter your choice!");
		}
	}

	public static void bookShip(){ 
		System.out.println("Enter the ship name");
		String sName = sc.nextLine();
		try{
			checkNotNull(sName);
		}catch(NullPointerException e){
			System.out.println("No name was entered, please try again!");
			return;
		}
		System.out.println("Enter the ship's ID");
		String s = sc.nextLine();
		try{
			checkNotNull(s);
		}catch(NullPointerException e){
			System.out.println("No ship ID was entered, please try again!");
			return;
		}
		int SID = Integer.parseInt(s);
		int dockId;
		try{
			dockId = dbm.getDockByVolumeType(dbm.getShipVol1(sName, SID));
		}catch(NullPointerException e){
			System.out.println("The ship doesn't exist in the database. Please add ship or try again!");
			return;
		}
			System.out.println("Enter the date you wish to book (YYYY-MM-DD)");
			String date = sc.nextLine();
			try{
				checkNotNull(date);
			}catch(NullPointerException e){
				System.out.println("No date was entered, please try again!");
				return;
			}
			System.out.println("Enter the time interval you wish to book (00-08, 08-16 or 16-00)");
			String time = sc.nextLine();
			try{
				checkNotNull(time);
			}catch(NullPointerException e){
				System.out.println("No time was entered, please try again!");
				return;
			}
			List<String> booked = dbm.getReport(date, date);
			boolean available = true;
			for(int i = 0; i<booked.size(); i++){
				if(booked.get(i).contains(time) & booked.get(i).contains("Dock: " + Integer.toString(dockId))){
					System.out.println("Sorry, already booked that time and date, please try again!");
					available = false;
					break;
				}						
			}
			if(available){
				try{
					dbm.bookDock(dockId, date, time, sName, SID);
				}catch(ForeignKeyException e){
					System.out.println("Error: The date was incorrect. Please try again!");
					return;
				}catch(Exception e){
					System.out.println("Error: The time was incorrect. Please try again!");
					return;
				}
				System.out.println("The ship " + sName + " is booked!");
			}
	}
	
	public static void getReport(){
		System.out.println("Enter the start date for the report (YYYY-MM-DD)");
		String firstDate = sc.nextLine();
		System.out.println("Enter the end date (YYYY-MM-DD) or leave it empty to get start date)");
		String secondDate = sc.nextLine();
		if(secondDate.equals("")){
			secondDate = firstDate;
		}
		for(String s : dbm.getReport(firstDate, secondDate))
			System.out.println(s);
	}
	
	public static void searchStaff(){
		System.out.println("Enter lastname");
		String lastName = sc.nextLine();
		try{
			checkNotNull(lastName);
		}catch(NullPointerException e){
			System.out.println("No name was entered, please try again!");
			return;
		}
		List<String> peps = dbm.getPeps(lastName);
		if(peps.size() != 0){
			for(String s : peps){
				System.out.println(s);
			}
		}
		else{
			System.out.println("Sorry, no match! Please, add person or try again!");
		}
	}
	
	public static void addStaff(){
		System.out.println("Enter first name");
		String name = sc.nextLine();
		try{
			checkNotNull(name);
		}catch(NullPointerException e){
			System.out.println("No name was entered, please try again!");
			return;
		}
		System.out.println("Enter last name");
		String familyName = sc.nextLine();
		try{
			checkNotNull(familyName);
		}catch(NullPointerException e){
			System.out.println("No last name was entered, please try again!");
			return;
		}
		System.out.println("Enter license type");
		String license = sc.nextLine();
		try{
			checkNotNull(license);
		}catch(NullPointerException e){
			System.out.println("No license was entered, please try again!");
			return;
		}
		System.out.println("Enter schedule (MF/LS/S)");
		String schedule = sc.nextLine();
		try{
			checkNotNull(schedule);
		}catch(NullPointerException e){
			System.out.println("No schedule was entered, please try again!");
			return;
		}
		System.out.println("Enter status");
		String status = sc.nextLine();
		try{
			checkNotNull(status);
		}catch(NullPointerException e){
			System.out.println("No status was entered, please try again!");
			return;
		}
		try{
			dbm.addPeps(name, familyName, license, schedule, status); 
		}catch(ForeignKeyException e){
			System.out.println("Oops, you've entered something incorrect... Please, try again!");
			return;
		}
		System.out.println("Staff added!");
	}
	
	public static void removeStaff(){
		System.out.println("Enter ID");
		String PID = sc.nextLine();
		try{
			checkNotNull(PID);
		}catch(NullPointerException e){
			System.out.println("No ID was entered, please try again!");
			return;
		}
		System.out.println("Enter lastname");
		String lastname = sc.nextLine();
		try{
			checkNotNull(lastname);
		}catch(NullPointerException e){
			System.out.println("No last name was entered, please try again!");
			return;
		}
			int rows = dbm.removePeps(PID, lastname);
			if(rows == 1){
				System.out.println(rows + " person removed!");
			}
			else
				System.out.println("Person not found, couldn't remove, please try again!");
	}
	
	public static void getOkTrucks(){
		System.out.println("Enter ship volume");
		String shipVolume = sc.nextLine(); 
		try{
			checkNotNull(shipVolume);
		}catch(NullPointerException e){
			System.out.println("No ship volume was entered, please try again!");
			return;
		}
		for(String s : dbm.getOKTrucks(shipVolume))
			System.out.println(s);
	}
	
	public static void addTrucks(){
		System.out.println("Enter truck type");
		String type = sc.nextLine();
		try{
			checkNotNull(type);
		}catch(NullPointerException e){
			System.out.println("No truck type was entered, please try again!");
			return;
		}
		System.out.println("Enter status");
		String status = sc.nextLine();
		try{
			checkNotNull(status);
		}catch(NullPointerException e){
			System.out.println("No status was entered, please try again!");
			return;
		}
		try{
			dbm.addTrucks(type, status);
		}catch(ForeignKeyException e){
			System.out.println("Oops, you've entered something incorrect... Please, try again!");
			return;
		}
		System.out.println("Truck added!");
	}
	
	public static void removeTrucks(){
		System.out.println("Enter ID");
		String ID = sc.nextLine();
		try{
			checkNotNull(ID);
		}catch(NullPointerException e){
			System.out.println("No ID was entered, please try again!");
			return;
		}
		System.out.println("Enter type");
		String truckType = sc.nextLine();
		try{
			checkNotNull(truckType);
		}catch(NullPointerException e){
			System.out.println("No truck type was entered, please try again!");
			return;
		}
		int rows = dbm.removeTrucks(ID, truckType);
		if(rows == 1){
			System.out.println(rows + " truck removed!");
		}
		else
			System.out.println("Truck not found, couldn't remove it. Please, try again!");
	}
	
	public static void searchShip(){
		System.out.println("Enter ship name");
		String name = sc.nextLine();
		try{
			checkNotNull(name);
		}catch(NullPointerException e){
			System.out.println("No name was entered, please try again!");
			return;
		}
		List<String> ships = dbm.getShip(name);
		if(ships.size() != 0){
			for(String s : ships){
				System.out.println(s);
			}
		}
		else{
			System.out.println("Sorry, no match! Please, add ship or try again!");
		}
	}
	
	public static void addShip(){
		System.out.println("Enter ship name");
		String name = sc.nextLine();
		try{
			checkNotNull(name);
		}catch(NullPointerException e){
			System.out.println("No name was entered, please try again!");
			return;
		}
		System.out.println("Enter company name");
		String company = sc.nextLine();
		try{
			checkNotNull(company);
		}catch(NullPointerException e){
			System.out.println("No company name was entered, please try again!");
			return;
		}
		System.out.println("Enter volume type");
		String volType = sc.nextLine();
		try{
			checkNotNull(volType);
		}catch(NullPointerException e){
			System.out.println("No volume type was entered, please try again!");
			return;
		}
		try{
			dbm.addShip(name, company, volType);
		}catch(ForeignKeyException e){
			System.out.println("Oops, you've entered something incorrect... Please, try again!");
			return;
		}
		System.out.println("Ship added!");
	}
	
	public static void removeShip(){
		System.out.println("Enter ship ID");
		String ID = sc.nextLine();
		try{
			checkNotNull(ID);
		}catch(NullPointerException e){
			System.out.println("No ID was entered, please try again!");
			return;
		}
		System.out.println("Enter ship name");
		String name = sc.nextLine();
		try{
			checkNotNull(name);
		}catch(NullPointerException e){
			System.out.println("No ship name was entered, please try again!");
			return;
		}
		int rows = dbm.removeShip(ID, name);
		if(rows == 1){
			System.out.println(rows + " ship removed!");
		}
		else
			System.out.println("No ship found, couldn't remove! Please, try again!");
	}
	
	public static void checkNotNull(String s){
		if(s.equals("")){
			throw new NullPointerException();
		}
	}
}

