package team;

import java.sql.ResultSet;
import java.util.ArrayList;

import database.DBConnection;
import database.DatabaseUtils;

public class TeamManager {
	
	/*
	 * checks to see if the user exists 
	 */
	public static boolean teamExists(DBConnection db, String teamID) {
		String query = "SELECT * FROM teams WHERE teamID = \"" + teamID + "\";";
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
		int numRows = 0;
		try {
			r.last();
			numRows = r.getRow();
		}
		catch (Exception e) {
			System.out.println("Database Error");
		}
		if (numRows > 1) {
			System.out.println("Warning: more than one user with username");
		}
		return numRows > 0;
	}
	/*
	 * checks to see if the password is correct
	 */
	public static boolean checkTeamPassword(DBConnection db, String teamID, String password) {
		String p= getTeamPassword(db, teamID);
		return (p.equals(password));
	}
	/*
	 * adds a user to the database
	 */
	public static void addTeam(DBConnection db, String teamID, String password) {
		String query = "INSERT INTO teams VALUES (\"" + teamID + "\",\"" + password + "\");";
		DatabaseUtils.updateDatabase(db, query);
	}
	private static String getTeamPassword(DBConnection db, String teamID) {
		String query = "SELECT teamPassword FROM teams WHERE teamID =\"" + teamID+ "\";";
		ResultSet r = DatabaseUtils.getResultSetFromDatabase(db, query);
		String password = "";
		try {
			r.first();
			password = r.getString(1);
		}
		catch (Exception e) {
			System.out.println("Database error");
		}
		return password;
	}
	
}
