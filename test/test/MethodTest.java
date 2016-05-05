package test;

import static org.junit.Assert.*;

import org.junit.Test;

import Main.DBMethods;
import Main.Database;

import static org.junit.Assert.*;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.List;

	import org.junit.Before;
	import org.junit.Ignore;
	import org.junit.Test;
	//import org.mockito.Mockito;

	import junit.framework.Assert;
	//import org.mockito.Mockito;

	public class MethodTest{

		DBMethods dbm;
		//DBMethods db_mock = Mockito.spy(DBMethods.class);
		
		@Before
		public void setup(){
			dbm = new DBMethods();		
		}
		
		Integer result;
		List<String> result1;
		String result2;
		String result3;
		String result4;
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
		public void getvol(){
			result2 = dbm.getTruckVol("A005");
			System.out.println(result);
		}
		//@Test
		public void TestGetPeps(){
			result2 = dbm.getPeps("307", "Testnamn");
			System.out.println(result2);
			assertEquals(result2, "ID: 307 Name: Test LastName: Testnamn License: A Schedule: MF Status: 100%");
		}
		// Väntar på att metoderna ska bli klara.
		//@Test
		public void TestAddPeps(){
			dbm.addPeps("Honey", "Booboo", "A", "S", "100%");
			//System.out.println(result2);
		}
		//@Test
		public void TestRemovePeps(){
			dbm.removePeps("308", "Booboo");
			System.out.println(result2);
		}
		//@Test
		public void AddAndRemovePeps(){
			
			result2 = dbm.getPeps("308", "Booboo");
			System.out.println(result2);
			if(result2 != null){
				assertEquals(result2, "ID: 308 Name: Honey LastName: Booboo License: A Schedule: S Status: 100%");
			}
			else if(result2 == null){
				System.out.println("Personen Finns Ej I Systemet");
			}
			
			dbm.addPeps("Honey", "Booboo", "A", "S", "100%");
			result3 = dbm.getPeps("308", "Booboo");
			System.out.println(result3);
			if(result3 != null){
				assertEquals(result3, "ID: 308 Name: Honey LastName: Booboo License: A Schedule: S Status: 100%");
			}
			else if(result3 == null){
				System.out.println("Personen Finns Fortfarande Inte I Systemet");
			}
			
			dbm.removePeps("308", "Booboo");
			result4 = dbm.getPeps("308", "Booboo");
			System.out.println(result4);
			if(result4 != null){
				assertEquals(result4, "ID: 308 Name: Honey LastName: Booboo License: A Schedule: S Status: 100%");
			}
			else if(result4 == null){
				System.out.println("Personen Är Borttagen Ur Systemet");
			}
			
		}
		
		//@Test
		public void AddAndRemoveShip(){
					
			result2 = dbm.getShip("milk", "boy");
			System.out.println(result2);
			if(result2 != null){
				assertEquals(result2, "ID: 310 Name: milk Company: boy");
			}
			else if(result2 == null){
				System.out.println("Sheppet Finns Ej I Systemet");
			}
					
			dbm.addShip("milk", "boy", "K007");
			result3 = dbm.getShip("milk", "boy");
			System.out.println(result3);
			if(result3 != null){
				assertEquals(result3, "ID: 310 Name: milk Company: boy");
			}
			else if(result3 == null){
				System.out.println("Sheppet Finns Fortfarande Inte I Systemet");
			}
					
			dbm.removeShip("310", "milk");
			result4 = dbm.getShip("milk", "boy");
			System.out.println(result4);
			if(result4 != null){
				assertEquals(result4, "ID: 310 Name: milk Company: boy");
			}
			else if(result4 == null){
				System.out.println("Sheppet Är Borttagen Ur Systemet");
			}
					
		}

	}
