package Main;


import java.sql.*;

import org.sqlite.SQLiteConfig;

public class Database {
    public static Connection connection;
    public static Statement stm;
    public static ResultSet rs;

    final String DB_CONN_STR = "jdbc:sqlite:projectX";

  	static{
    	try{
    	Class.forName("org.sqlite.JDBC");
    	}catch(ClassNotFoundException cnfe){
    	System.err.println("Could not load driver: "+cnfe.getMessage());
    	}
    }
  	
    public static Connection con;
    
    public Database(){
    	getConnection();
    }
    
    private void getConnection(){
    	try{
    		SQLiteConfig config = new SQLiteConfig();
    		config.enforceForeignKeys(true);
    		con = DriverManager.getConnection(DB_CONN_STR,config.toProperties());
    	}catch(Exception e){
    	System.err.println("Error getting connection to " + DB_CONN_STR);
    	}
    }
    
    public boolean hasConnection(){
    	return con != null;
   	}
    
//    public void executeQuery(String sql){
//    	if(hasConnection()){
//    		Statement stm = null;
//    		ResultSet rs = null;
//    		try{
//    			stm = con.createStatement();
//    			rs = stm.executeQuery(sql);
//    			System.out.println("rs  " + rs.getString(0));
//    			while(rs.next()){
//    				System.out.println(rs.getString("Name") + " " +rs.getString("Name"));
//    			}
//    		}catch(SQLException sqle){
//    			System.err.println(sqle.getMessage());
//    		}finally{
//    			try{
//    				closeBoth(rs,stm);
//    			}catch(Exception e){
//    				System.err.println("Exception closing: "+e.getMessage());
//    			}
//    		}
//    	}
// 	}
//    
//    private void closeBoth(AutoCloseable a1, AutoCloseable a2){
//        try{
//            closeIt(a1);          
//            closeIt(a2);
//        }catch(Exception e){
//            System.err.println("Exception closing: "+e.getMessage());
//        }
//    }
//    private void closeIt(AutoCloseable it){     
//        try{
//            it.close();
//        }catch(Exception e){
//            System.err.println("Exception closing: "+e.getMessage());
//        }
//    }
}
