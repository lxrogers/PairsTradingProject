package team;

import java.util.ArrayList;

import database.DBConnection;
import database.DatabaseUtils;

public class Team {
	private ArrayList<String> mMembers;
	private int mTeamID;
	public Team(DBConnection db, int id) {
		mTeamID = id;
		fetchMembers(db);
	}
		
	
	
	private void fetchMembers(DBConnection db) {
		String query = "SELECT username FROM users WHERE teamID=" + mTeamID +";";
		mMembers = DatabaseUtils.getStringArrayFromDatabase(db, query);
		
	}
	
	public ArrayList<String> getMembers() {
		return mMembers;
	}
}
