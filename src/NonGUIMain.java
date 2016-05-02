

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
					
					case 1: //search finns bara för ok truckar och du kan bara få en - gör om som getReport
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
	}
	
	public static void subMenu(String option){
		System.out.println(option);
		System.out.println("1. Search ");
		System.out.println("2. Add");
		System.out.println("3. Remove");
		System.out.println("4. Go to main menu");
	}

	public static void bookShip(){ 
		System.out.println("ShipName");
		String sName = sc.nextLine();
		System.out.println("ShipID");
		int SID = sc.nextInt();
		sc.nextLine();
		try{
			int dockId = dbm.getDockByVolumeType(dbm.getShipVol1(sName, SID));
			System.out.println("Date YYYY-MM-DD");
			String date = sc.nextLine();
			System.out.println("Time hh-hh");
			String time = sc.nextLine();
			List<String> booked = dbm.getReport(date, date);
			boolean available = true;
			for(int i = 0; i<booked.size(); i++){
				if(booked.get(i).contains(time) & booked.get(i).contains(Integer.toString(dockId))){
					System.out.println("Already booked that time and date, please try again!");
					available = false;
					break;
				}						
			}
			if(available){
				dbm.bookDock(dockId, date, time, sName, SID);
				System.out.println("The ship is booked!");
			}
		}catch(NullPointerException e){
			System.out.println("The ship doesn't exist in the database. Please add ship or try again!");
		}
	}
	
	public static void getReport(){
		System.out.println("First Date YYYY-MM-DD");
		String firstDate = sc.nextLine();
		System.out.println("Second Date YYYY-MM-DD (Leave it empty to get First Date)");
		String secondDate = sc.nextLine();
		if(secondDate.equals("")){
			secondDate = firstDate;
		}
		for(String s : dbm.getReport(firstDate, secondDate))
			System.out.println(s);
	}
	
	public static void searchStaff(){
		System.out.println("Enter ID");
		String ID = sc.nextLine(); 
		System.out.println("Enter lastname");
		String lastName = sc.nextLine();
		System.out.println(dbm.getPeps(ID, lastName));
	}
	
	public static void addStaff(){
		System.out.println("Enter first name");
		String name = sc.nextLine();
		System.out.println("Enter last name");
		String familyName = sc.nextLine();
		System.out.println("Enter license type");
		String license = sc.nextLine();
		System.out.println("Enter schedule (MF/LS/S)");
		String schedule = sc.nextLine();
		System.out.println("Enter status");
		String status = sc.nextLine();
		dbm.addPeps(name, familyName, license, schedule, status); 
		System.out.println("Staff added!");
	}
	
	public static void removeStaff(){
		System.out.println("Enter ID");
		String PID = sc.nextLine();
		System.out.println("Enter lastname");
		String lastname = sc.nextLine();
		dbm.removePeps(PID, lastname);
		System.out.println("Staff removed!");
	}
	
	public static void getOkTrucks(){
		System.out.println("Enter ship volume");
		String shipVolume = sc.nextLine(); 
		System.out.println(dbm.getOKTrucks(shipVolume));
	}
	
	public static void addTrucks(){
		System.out.println("Enter truck type");
		String type = sc.nextLine();
		System.out.println("Enter status");
		String status = sc.nextLine();
		dbm.addTrucks(type, status);
		System.out.println("Truck added!");
	}
	
	public static void removeTrucks(){
		System.out.println("Enter ID");
		String ID = sc.nextLine();
		System.out.println("Enter type");
		String truckType = sc.nextLine();
		dbm.removeTrucks(ID, truckType);
		System.out.println("Truck removed!");
	}
	
	public static void searchShip(){
		System.out.println("This option doesn't exist yet, please come back later!");
	}
	
	public static void addShip(){
		System.out.println("Enter ship name");
		String name = sc.nextLine();
		System.out.println("Enter company name");
		String company = sc.nextLine();
		System.out.println("Enter volume type");
		String volType = sc.nextLine();
		dbm.addShip(name, company, volType);
		System.out.println("Ship added!");
	}
	
	public static void removeShip(){
		System.out.println("Enter ship ID");
		String name = sc.nextLine();
		System.out.println("Enter ship name");
		String ID = sc.nextLine();
		System.out.println("Enter company name");
		String company = sc.nextLine();
		dbm.removeShip(ID, name, company);
		System.out.println("Ship removed!");
	}
}

