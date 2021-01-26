package stocks.xtesting_examples;

//STEP 1. Import required packages
import java.sql.*;
import java.util.ArrayList;

public class DBTest {

    // JDBC driver name and database URL
    static final String JDBC_DRIVER = "com.mysql.jdbc.Driver";
    static final String DB_URL = "jdbc:mysql://localhost:3306/stockdata2";

    // Database credentials
    static final String USER = "root";
    static final String PASS = "Uctrickme12";

    static Connection conn = null;
    static Statement stmt = null;

    public static void main(String[] args) {

	try {


	    // STEP 2: Register JDBC driver
	    Class.forName("com.mysql.cj.jdbc.Driver");

	    // STEP 3: Open a connection
	    System.out.println("Connecting to database...");
	    conn = DriverManager.getConnection(DB_URL, USER, PASS);

	    // STEP 4: Execute a query
	    System.out.println("Creating statement...");
	    stmt = conn.createStatement();
	    String sql;

	    //----------------------------------------------



	    sql = "SELECT * FROM stocks;";




	    ArrayList<Object[]> data = getData(sql);



	    String sql2 = "";
	    sql2 = "INSERT INTO stocks (symbol, price)\r\n" 
		    + "VALUES ('AAPL', 131);";


	    stmt.executeUpdate(sql2);


	    data = getData(sql);

	    // Display values
	    for(Object[] obj : data) {
		System.out.println("-------------------");
		System.out.println("symbol: " + (String)obj[0]);
		System.out.println("Price: " + (double)obj[1]);
	    }


	    //-----------------------------------------------------

	    // STEP 6: Clean-up environment

	    stmt.close();
	    conn.close();

	} catch (Exception e) {

	    e.printStackTrace();
	} finally {
	    // finally block used to close resources
	    try {
		if (stmt != null)
		    stmt.close();

		if (conn != null)
		    conn.close();
	    } catch (SQLException se) {
		se.printStackTrace();
	    } 
	} 




	System.out.println("Goodbye!");
    }



    public static ArrayList<Object[]> getData(String sql) throws SQLException {
	ArrayList<Object[]> data = new ArrayList<Object[]>();

	ResultSet rs = stmt.executeQuery(sql);

	while (rs.next()) {
	    // Retrieve by column name

	    Object[] temp = new Object[2];
	    temp[0] = rs.getString("symbol");
	    temp[1] = rs.getDouble("price");

	    data.add(temp);
	}
	rs.close();
	return data;
    }

}
