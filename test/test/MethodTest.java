package test;

import static org.junit.Assert.*;

import Main.DBMethods;
import Main.ForeignKeyException;

import java.util.List;

	import org.junit.Before;

	public class MethodTest{

		DBMethods dbm;
		//DBMethods db_mock = Mockito.spy(DBMethods.class);
		
		@Before
		public void setup(){
			dbm = new DBMethods();		
		}
		
		Integer result;
		List<String> result1;
		List<String> result11;
		List<String> result111;
		String result2;
		String result3;
		String result4;
		int result5;
		//@Test
		//public void testGetReport() {
		//	dbm.getReport("2016-04-20", "2016-04-20");
		//}
		//@Ignore //ändra Ignore mellan dessa två om du vill lägga till och se det och sen kan du ta bort
		//@Test
		//@Ignore //ändra Ignore mellan dessa två om du vill lägga till och se det och sen kan du ta bort
		
		//@Test
		public void getDockByVolyme() {
			result = dbm.getDockByVolumeType("B005");
			System.out.println(result);
			assertEquals(result, (Integer) 201);
			result = dbm.getDockByVolumeType("B002");
			System.out.println(result);
			assertNull(result);			 
			}
		//@Test
		public void getshipvol(){
			result2 = dbm.getShipVol1("Talane", 1);
			System.out.println(result2);
			assertEquals("A005", result2);
			/**
			result2 = dbm.getShipVol1("", (Integer) null);
			System.out.println(result2);
			assertEquals(null, result2);
			result2 = dbm.getShipVol1("Talane", (Integer) "");
			System.out.println(result2);
			assertEquals(null, result2);
			**/
			}
		//@Test
		public void TestGetPeps(){
			result1 = dbm.getPeps("Booboo");
			System.out.println(result1);
			
			assertEquals(result1.toString(), "[ID: 308 Name: Honey LastName: Booboo License: A Schedule: S Status: 100%]");
			
		}
		// Väntar på att metoderna ska bli klara.
		//@Test
		public void TestAddPeps(){
			try {
				result5 = dbm.addPeps("Honey", "Booboo", "A", "S", "100%");
			} catch (ForeignKeyException e) {
				e.printStackTrace();
			}
			System.out.println(result5);
			assertEquals(result5, 1);
			//System.out.println(result2);
		}
		//@Test
		public void TestAddPepsfail(){
			try {
				result5 = dbm.addPeps("Honey", "Booboo", "A", "F", "100%");
			} catch (ForeignKeyException e) {
				e.printStackTrace();
			}
			System.out.println(result5);
			assertEquals(result5, 0);
			//System.out.println(result2);
		}
		//@Test
		public void TestRemovePeps(){
			dbm.removePeps("308", "Booboo");
			
		}
		//@Test
		public void AddAndRemovePeps(){
			
			result1 = dbm.getPeps("Booboo");
			System.out.println(result1);
			if(result1.toString() != "[]"){
				assertEquals(result1.toString(), "[]");
			}
			else if(result1.toString() == "[]"){
				System.out.println("Personen Finns Ej I Systemet");
			}
			
			try {
				result5 = dbm.addPeps("Honey", "Booboo", "A", "S", "100%");
			} catch (ForeignKeyException e) {
				e.printStackTrace();
			}
			result11 = dbm.getPeps("Booboo");
			System.out.println(result11);
			if(result11.toString() != "[]"){
				assertEquals(result11.toString(), "[ID: 308 Name: Honey LastName: Booboo License: A Schedule: S Status: 100%]");
			}
			else if(result11.toString() == "[]"){
				System.out.println("Personen Finns Fortfarande Inte I Systemet");
			}
			
			dbm.removePeps("308", "Booboo");
			result111 = dbm.getPeps("Booboo");
			System.out.println(result111);
			if(result111.toString() != "[]"){
				assertEquals(result111.toString(), "[ID: 308 Name: Honey LastName: Booboo License: A Schedule: S Status: 100%]");
			}
			else if(result111.toString() == "[]"){
				System.out.println("Personen Är Borttagen Ur Systemet");
				assertEquals(result111.toString(), "[]");
			}
			
		}
		
		//@Test
		public void AddAndRemoveShip(){
					
			result1 = dbm.getShip("milk");
			System.out.println(result1);
			if(result1.toString() != "[]"){
				assertEquals(result1.toString(), "[ID: 310 Name: milk Company: boy]");
			}
			else if(result1.toString() == "[]"){
				System.out.println("Sheppet Finns Ej I Systemet");
			}
				
			try {
				result5 = dbm.addShip("milk", "boy", "K007");
			} catch (ForeignKeyException e) {
				e.printStackTrace();
			}
			result11 = dbm.getShip("milk");
			System.out.println(result11);
			if(result11.toString() != "[]"){
				assertEquals(result11.toString(), "[ID: 310 Name: milk Company: boy]");
			}
			else if(result11.toString() == "[]"){
				System.out.println("Sheppet Finns Fortfarande Inte I Systemet");
			}
					
			dbm.removeShip("310", "milk");
			result111 = dbm.getShip("milk");
			System.out.println(result111);
			if(result111.toString() != "[]"){
				assertEquals(result111.toString(), "[ID: 310 Name: milk Company: boy]");
			}
			else if(result111.toString() == "[]"){
				System.out.println("Sheppet Är Borttagen Ur Systemet");
				assertEquals(result111.toString(), "[]");
			}
					
		}
		//@Test
		public void addShip(){
			try {
				result5 = dbm.addShip("milk", "boy", "K007");
			} catch (ForeignKeyException e) {
				e.printStackTrace();
			}
			System.out.println(result5);
			assertEquals(result5, 1);
		}
		//@Test
		public void addShipFail(){
			try {
				result5 = dbm.addShip("milk", "boy", "K000");
			} catch (ForeignKeyException e) {
				e.printStackTrace();
			}
			System.out.println(result5);
			assertEquals(result5, 0);
		}
		//@Test
		public void removeShip(){
			dbm.removeShip("310", "milk");
		}
		//@Test
		public void getShip(){
			result1 = dbm.getShip("manne");
			
			System.out.println(result1);
			assertEquals(result1.toString(), "[ID: 308 Name: manne Company: lofs]");

		}

	}
