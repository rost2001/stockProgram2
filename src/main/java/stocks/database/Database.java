package stocks.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

public class Database {

    /*
     * url = jdbc:mysql://localhost:3306/stockdata2
     */

    Connection conn = null;
    Statement statement = null;


    public Database(String url, String username, String password) throws SQLException, ClassNotFoundException{

	Class.forName("com.mysql.cj.jdbc.Driver");

	conn = DriverManager.getConnection(url, username, password);
	
	statement = conn.createStatement();
	
    }
    
    public static void main(String[] args) throws ClassNotFoundException, SQLException {
    
	
	Database database = new Database("jdbc:mysql://localhost:3306/stockdata2", "root", "Uctrickme12");
	   
	ArrayList<Object[]> tableData = database.getFromTable(new String[] {"symbol", "price"}, "stocks");
		
	for(Object[] obj : tableData) {
	    for(Object obj2 : obj)
	    System.out.println((String)obj2);
	}
	
	database.insertIntoTable(new String[] {"symbol", "price"}, "stocks", new String[] {"gme", "346.123"});
    
	System.out.println("--------------------------------------------");
	tableData = database.getFromTable(new String[] {"symbol", "price"}, "stocks");
	
	for(Object[] obj : tableData) {
	    for(Object obj2 : obj)
	    System.out.println((String)obj2);
	}
    
    }
    
    
    public ArrayList<Object[]> getFromTable(String[] columns, String table) throws SQLException{
	if(columns == null || columns.length == 0 || table == null) {
	    return null;
	}
	
	ArrayList<Object[]> tableData = new ArrayList<Object[]>();
	String sql = "SELECT ";
	
	for(int i = 0; i < columns.length; i++) {
	    
	    sql += columns[i];
	    
	    if(i != columns.length-1)
		sql += ", ";
	    else
		sql += " ";
	}
	sql += "FROM " + table + ";";
	
	
	ResultSet rs = statement.executeQuery(sql);

	while (rs.next()) {
	    // Retrieve by column name

	    Object[] columnValues = new Object[columns.length];
	    for (int i = 0; i < columns.length; i++) {
		columnValues[i] = rs.getString(columns[i]);
	    }

	    tableData.add(columnValues);
	}
	rs.close();
	
	return tableData;
    }

    public void insertIntoTable (String[] columns, String table, String[] values) throws SQLException {
	
	for(int i = 0; i < values.length; i++) {
	    String temp = "'" + values[i] + "'";
	    values[i] = temp;
	}
	    
	String sql = "INSERT INTO " + table + " " + "("+String.join(",", columns)+")\r\n"
		+ "VALUES (" + String.join(",", values) + ");";
	
	statement.executeUpdate(sql);
    }
    
}
