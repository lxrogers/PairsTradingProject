package database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;

public class DatabaseUtils {
	public static ResultSet getResultSetFromDatabase(DBConnection db, String query) {
	
		Statement stmt = db.getStatement();
		try {
			ResultSet r = stmt.executeQuery(query);
			r.beforeFirst();
			return r;
		}
		catch(Exception e){e.printStackTrace();}//System.out.println("Could not get result set");}
		return null;
	}
	public static ArrayList<String> getStringArrayFromDatabase(DBConnection db, String query) {
		ResultSet r = getResultSetFromDatabase(db, query);
		ArrayList<String> results = new ArrayList<String>();
		try {
			while (r.next()) {
				results.add(r.getString(1));
			}
		}
		catch (Exception e) {
			System.out.println("could not parse result set");
		}
		return results;
	}
	public static void addUpdateBatch(DBConnection db, String query) {
		Statement stmt = db.getStatement();
		try{
			stmt.addBatch(query);
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
	}
	public static int[] executeBatch(DBConnection db) {
		Statement stmt = db.getStatement();
		try{
			return stmt.executeBatch();
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return new int[]{};
	}
	public static int updateDatabase(DBConnection db, String query){
		Statement stmt = db.getStatement();
		try{
			return stmt.executeUpdate(query);
			
		}catch(SQLException e){
			System.out.println(e.getMessage());
		}
		return 0;
	}
}
