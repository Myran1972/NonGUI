package Main;


import java.sql.*;

public class Database {
    public static Connection connection;
    public static Statement stm;
    public static ResultSet rs;

    final String DB_CONN_STR = "jdbc:sqlite:projectX";
    //C:\\Users\\cathrine\\Desktop\\ProjectX\\AdminSystem\\projectX";
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
    		con = DriverManager.getConnection(DB_CONN_STR);
    	}catch(Exception e){
    	System.err.println("Error getting connection to " + DB_CONN_STR);
    	}
    }
    
    public boolean hasConnection(){
    	return con != null;
   	}
    public void executeQuery(String sql){
    	if(hasConnection()){
    	Statement stm = null;
    	ResultSet rs = null;
    	try{
    		
    		stm = con.createStatement();
    		rs = stm.executeQuery(sql);
    		System.out.println("rs  " + rs.getString(0));
    		while(rs.next()){
    			System.out.println(rs.getString("Name") + " " +rs.getString("Name"));
    		}
    		}catch(SQLException sqle){
    			System.err.println(sqle.getMessage());
    		}finally{
    			try{
    				rs.close();
    				stm.close();
    		}catch(Exception e){}
    		}
    		}
    		}

    public String fetchCity(){
        String result = null;
        Statement stm = null;
        ResultSet rs  = null;
        
        try{
            stm = con.createStatement();
            rs = stm.executeQuery( "SELECT * FROM ships where ID = '7' and Name = 'Dazzlesphere';" );
            while ( rs.next() ) {
               int id = rs.getInt("ID");
               String  name = rs.getString("Name");
               
               String  address = rs.getString("VolumeType");
               
               System.out.println( "ID = " + id );
               System.out.println( "NAME = " + name );
               System.out.println( "ADDRESS = " + address );
               System.out.println();
            }
            
        }catch(Exception e){
            System.err.println("Exception fetching "  + e.getMessage());
        }finally{
            closeBoth(rs, stm);
            return result;
        }
    }    
    
    private void closeBoth(AutoCloseable a1, AutoCloseable a2){
        try{
            closeIt(a1);          
            closeIt(a2);
        }catch(Exception e){
            System.err.println("Exception closing: "+e.getMessage());
        }
    }
    private void closeIt(AutoCloseable it){     
        try{
            it.close();
        }catch(Exception e){
            System.err.println("Exception closing: "+e.getMessage());
        }
    }
    
    public void getShipVol(String Name, String ID){
        if(hasConnection()){
            Statement stm = null;
            ResultSet rs = null;
            try{
            	System.out.println("rs  "); 
            	String sql = "SELECT VolumeType FROM Ships WHERE Name='" + Name + "' AND ID='" + ID + "';";
            	stm = con.createStatement();
            	rs = stm.executeQuery(sql);
            while(rs.next()){
            	System.out.println(rs.getString("VolumeType")); 
        	//getDockByVolumeType(rs.getString("VolymType");
          }
                
            }catch(SQLException sqle){
                System.err.println(sqle.getMessage());
            }finally{
                closeBoth(rs, stm);
            }
        }
    }

    

}
