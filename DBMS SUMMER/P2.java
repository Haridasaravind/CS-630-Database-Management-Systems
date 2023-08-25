import java.io.*;
import java.sql.*;
import java.util.Scanner;

class P2{
	// the host name of the server and the server instance name/id
	public static final String oracleServer = "dbs3.cs.umb.edu";
	public static final String oracleServerSid = "dbs3";

    /**
    Entry point method
    **/
	public static void main(String args[]) {
		Connection conn = null;
		conn = getConnection();
		if (conn==null)
			System.exit(1);

		//now execute query
		Scanner input = new Scanner(System.in);
		try {
		  // Create statement object
		  Statement stmt = conn.createStatement();
		  while(true){
	 	    //get a minimum rating for sailors
		    System.out.print("Enter the minimum rating (enter -1 to exit the program): ");
		    int rating_no=input.nextInt();
		    if (rating_no==-1){
			System.out.println("Program will exit.");
			break;
		    }
		    ResultSet rs = stmt.executeQuery("select sname, rating,age from sailors"
			+ " where rating >="+rating_no);
		    if (rs.next()){
		    	do{
		        	System.out.println(
					"Name = " + rs.getString("sname")+
					", Rating = " + rs.getString("rating")+
					", Age = " + rs.getString("age")
		        	);
		      	}while(rs.next());
	 	    }	
		    else
			System.out.println("No Records Retrieved");
		  }
		} catch (SQLException e) {
			System.out.println ("ERROR OCCURRED");	
			e.printStackTrace();
		}
	}

    /**
    Method used to return a db getConnection
    Args: -
    Returns: an object of type Connection, which has an established connection to the Oracle server
    **/
	public static Connection getConnection(){

		// first we need to load the driver
		String jdbcDriver = "oracle.jdbc.OracleDriver";
		try {
			Class.forName(jdbcDriver); 
		} catch (Exception e) {
			e.printStackTrace();
		}

		// Get username and password
		Scanner input = new Scanner(System.in);
		System.out.print("Username:");
		String username = input.nextLine();
		System.out.print("Password:");
		//the following is used to mask the password
		Console console = System.console();
		String password = new String(console.readPassword()); 
		String connString = "jdbc:oracle:thin:@" + oracleServer + ":1521:"
				+ oracleServerSid;

		System.out.println("Connecting to the database...");
	
		Connection conn;
		// Connect to the database
		try{
			conn = DriverManager.getConnection(connString, username, password);
			System.out.println("Connection Successful");
		}
		catch(SQLException e){
			System.out.println("Connection ERROR");
			e.printStackTrace();	
			return null;
		}

		return conn;
	}
}
