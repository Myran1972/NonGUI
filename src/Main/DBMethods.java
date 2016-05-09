package Main;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class DBMethods extends Database {
	public static DBMethods methods = new DBMethods();
	
	public Integer getDockByVolumeType(String volType){ 
		if(hasConnection()){
			Statement stm = null;
			ResultSet rs = null;
			try{
				String sql = "SELECT * FROM TypeTable WHERE VolumeType='" + volType + "'";
				stm = con.createStatement();
				   rs = stm.executeQuery(sql);
				   while(rs.next()){
					   return rs.getInt("DockID");
				   }
			   }catch(SQLException sqle){
				   System.err.println(sqle.getMessage());
			   }finally{
				   try {
					rs.close();
					stm.close();
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
	return null;
	}

	public String getShipVol1(String Name, int ID){ 
		if(hasConnection()){
			String sql = "SELECT VolumeType FROM Ships WHERE Name='" + Name + "' AND ID='" + ID + "';";
			Statement stm = null;
			ResultSet rs = null;
			try{
				stm = con.createStatement(); 
				rs = stm.executeQuery(sql);
				while(rs.next()){
						String VolT = rs.getString("VolumeType");
						return VolT;
				   }
				
			}catch(SQLException sqle){
				System.err.println(sqle.getMessage());
			}finally{
				try{
					rs.close();
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public List<String> getOKTrucks(String shipVolume){ 		
		String  truck = getTruckVol(shipVolume);
		if(hasConnection()){
			Statement stm = null;
			ResultSet rs = null;
			try{
				String sql = "SELECT * FROM Trucks WHERE Type='" + truck + "' AND Status='OK'";
				stm = con.createStatement();
				rs = stm.executeQuery(sql);
				List<String> array = new ArrayList<String>();
				while(rs.next()){
					int id = rs.getInt("ID");
					String type = rs.getString("Type");
					String status = rs.getString("Status");
					String line = "ID:" + id + " Type:" + type + " Status:" + status;
					array.add(line);
				}
				return array;
			}catch(SQLException sqle){
				System.err.println(sqle.getMessage());
			}finally{
				try{
					rs.close();
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public List<String> getReport(String firstDate, String secondDate){ 

		if(hasConnection()){
			Statement stm = null;
			ResultSet rs = null;
			try{
				String sql = "SELECT * FROM Ship_Booked WHERE Date BETWEEN '" + firstDate + "' AND '" + secondDate + "' ORDER BY Date, DockID_00 DESC, DockID_08 DESC, DockID_16 DESC;";
				stm = con.createStatement();
				rs = stm.executeQuery(sql);
				List<String> array = new ArrayList<String>();
				while(rs.next()){
					String date = rs.getString("Date");
					String ship = rs.getString("ShipName");
					int shipId = rs.getInt("ShipID");					
					int dock = rs.getInt("DockID_00");
					String time = "00-08";
					if(rs.wasNull()){
						dock = rs.getInt("DockID_08");
						time = "08-16";
						if(rs.wasNull()){
							dock = rs.getInt("DockID_16");
							time = "16-00";
						}
					}
					String test = "Date: " + date + " Time: " + time + " Dock: " + dock + " ShipID: " + shipId + " ShipName: " + ship;
					array.add(test);
				}
				return array;
			}catch(SQLException sqle){
				System.err.println(sqle.getMessage());
			}finally{
				try{
					rs.close();
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return null;	
	}
	
	public void bookDock(int dockId, String date, String time, String sName, int SID) throws Exception{ 
		if(hasConnection()){
			Statement stm = null;
			String sql = null;
			try{
				if(time.equals("00-08")){
					sql = "INSERT INTO Ship_Booked" + " (Date, DockID_00, ShipName, ShipID)" + 
							"VALUES ('"+ date +"', '"+ dockId +"', '" + sName + "', "+ SID +");";
				}
				else if(time.equals("08-16")){
					sql = "INSERT INTO Ship_Booked" + " (Date, DockID_08, ShipName, ShipID)" + 
							"VALUES ('"+ date +"', '"+ dockId +"', '" + sName + "', "+ SID +");";
				}
				else if(time.equals("16-00")){ 
					sql = "INSERT INTO Ship_Booked" + " (Date, DockID_16, ShipName, ShipID)" + 
							"VALUES ('"+ date +"', '"+ dockId +"', '" + sName + "', "+ SID +");";
				}
				else{
					throw new Exception(); //om fel tidsintervall
				}
				stm = con.createStatement();
				stm.executeUpdate(sql); 
				
			}catch(SQLException sqle){
				//System.err.println(sqle.getMessage()); kan förvirra användare
				throw new ForeignKeyException(); //om fel datumformat
			}finally{
				try{
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
	}
	
	public void clearTestBookDock(int dockId, String date, String time, String sName, int SID){ //endast test
		String sql = "DELETE FROM Ship_Booked WHERE ShipID = '" + SID + "'";
		Statement stm = null;
		try{
			stm = con.createStatement();
			stm.executeUpdate(sql);
			}catch(Exception e){
			System.err.println(e.getMessage());
			}finally{
				try{
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
	}
	
	public int addPeps(String name, String lastName, String license, String schedule, String status) throws ForeignKeyException {
		int rows = -1;
		if(hasConnection()){
			Statement stm = null;
			try{
				String sql = "INSERT INTO Staff (Name, LastName, License, Schedule, Status)"
						   + "VALUES ('"+ name +"', '"+ lastName  +"', '"+ license +"', '"+ schedule +"', '"+ status +"');";
			stm = con.createStatement();
			rows = stm.executeUpdate(sql);
			}catch(SQLException sqle){
				//System.err.println(sqle.getMessage()); kan förvirra användare
				throw new ForeignKeyException();
			}finally{
				try{
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return rows;
	}
	
	public int removePeps(String ID, String lastName){
		int rows = -1;
		if(hasConnection()){
			Statement stm = null;
			try{
				String sql = "DELETE FROM Staff WHERE ID='"+ ID +"' AND LastName='"+ lastName +"';";
			stm = con.createStatement();
			rows = stm.executeUpdate(sql);
			}catch(SQLException sqle){
				System.err.println(sqle.getMessage());
			}finally{
				try{
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return rows;
	}
	
	public List<String> getPeps(String LastName){
		if(hasConnection()){
			Statement stm = null;
			ResultSet rs = null;
			try{
				String sql = "SELECT * FROM Staff WHERE Lastname='"+ LastName + "';";
				stm = con.createStatement();
				rs = stm.executeQuery(sql);
				List<String> array = new ArrayList<String>();
				while(rs.next()){
					String PID = rs.getString("ID");
					String Name = rs.getString("Name");
					String LName = rs.getString("LastName");
					String License = rs.getString("License");
					String Schedule = rs.getString("Schedule");
					String Status = rs.getString("Status");	
					String line = "ID: " + PID + " Name: " + Name + " LastName: " + LName + " License: " + License + " Schedule: " + Schedule + " Status: " + Status;	
					array.add(line);
				}
				return array;
			}catch(SQLException sqle){
				System.err.println(sqle.getMessage());
			}finally{
				try{
					rs.close();
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}
	
	public int addTrucks(String type, String status) throws ForeignKeyException{
		int rows = -1;
		if(hasConnection()){
			Statement stm = null;
			try{
				String sql = "INSERT INTO Trucks (Type, Status) VALUES ('"+ type +"', '"+ status +"');"; 
			stm = con.createStatement();
			rows = stm.executeUpdate(sql);
			}catch(SQLException sqle){
				//System.err.println(sqle.getMessage()); kan förvirra användare
				throw new ForeignKeyException();
			}finally{
				try{
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return rows;
	}
	
	public int removeTrucks(String ID, String Type){
		int rows = -1; 
		if(hasConnection()){
			Statement stm = null;
			try{
				String sql = "DELETE FROM Trucks WHERE ID='"+ ID +"' AND Type='"+ Type +"';";
				stm = con.createStatement();
				rows = stm.executeUpdate(sql);
			}catch(SQLException sqle){
				System.err.println(sqle.getMessage());
			}finally{
				try{
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return rows;
	}
	
	public List<String> getShip(String Name){ //ändrade till sökning på bara namn och returnerar lista /mr
		if(hasConnection()){
			Statement stm = null;
			ResultSet rs = null;
			try{
				String sql = "SELECT * FROM Ships WHERE Name='"+ Name + "';";
				stm = con.createStatement();
				rs = stm.executeQuery(sql);
				List<String> array = new ArrayList<String>();
				while(rs.next()){
					String PID = rs.getString("ID");
					String SName = rs.getString("Name");
					String SCompany = rs.getString("Company");
					String line = "ID: " + PID + " Name: " + SName + " Company: " + SCompany; 
					array.add(line);
				}
				return array;
			}catch(SQLException sqle){
				System.err.println(sqle.getMessage());
			}finally{
				try{
					rs.close();
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return null;
	}

	public int removeShip(String ID, String name){
		int rows = -1;
		if(hasConnection()){
			Statement stm = null;
			try{
				String sql = "DELETE FROM Ships WHERE ID='"+ ID +"' AND Name='"+ name +"';";
				stm = con.createStatement();
				rows = stm.executeUpdate(sql);
			}catch(SQLException sqle){
				System.err.println(sqle.getMessage());
			}finally{
				try{
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return rows;
	}
	
	public int addShip(String name, String comp, String volType) throws ForeignKeyException{
		int rows = -1;
		if(hasConnection()){
			Statement stm = null;
			try{
				String sql = "INSERT INTO Ships (Name, Company, VolumeType) VALUES ('"+ name +"', '"+ comp +"', '"+ volType +"');";
				stm = con.createStatement();
				rows = stm.executeUpdate(sql);
			}catch(SQLException sqle){
				//System.err.println(sqle.getMessage()); kan förvirra användare
				throw new ForeignKeyException();
			}finally{
				try{
					stm.close();
				}catch(SQLException e){
					e.printStackTrace();
				}
			}
		}
		return rows;
	}
	
	public String getTruckVol(String volume){ 
		
		switch(volume){
			case "A005":
				return "A001";
			case "AA07":
				return "AA01";
			case "B005":
				return "B001";
			case "BB07":
				return "BB01";
			case "C005":
				return "C001";
			case "CC07":
				return "CC01";
			case "CCC5":
				return "CCC1";
			case "K007":
				return "K001";
		}
		return null;
	}
}
