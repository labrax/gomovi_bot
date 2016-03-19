package movile.hackathon.team_bot;

import java.util.HashMap;

public class DatabaseConnMock {
	private static DatabaseConnMock instance = null; 

	HashMap<String, Object> stateDb = new HashMap<>();
	HashMap<String, Object> subStateDb = new HashMap<>();
	
	private DatabaseConnMock() {}
	
	public static DatabaseConnMock getInstance() {
		if(instance == null)
			instance = new DatabaseConnMock();
		return instance;
	}
	
	public String getState(Integer user, Long chatId) {
		if(!stateDb.containsKey(user + "_" + chatId))
			return null;
		return (String) stateDb.get(user + "_" + chatId);
	}
	
	public void setState(Integer user, Long chatId, String state) {
		stateDb.put(user + "_" + chatId, state);
	}
	
	public String getSubState(Integer user, Long chatId) {
		return (String) subStateDb.get(user + "_" + chatId);
	}
	
	public void setSubState(Integer user, Long chatId, String state) {
		subStateDb.put(user + "_" + chatId, state);
	}
	
	public String getOptionsSelected(Integer user, Long chatId) {
		return null;
	}
	
	public void setOptionsSelected(Integer user, Long chatId, String options_txt) {
		
	}
}
