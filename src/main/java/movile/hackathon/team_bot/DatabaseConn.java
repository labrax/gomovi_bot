package movile.hackathon.team_bot;

public class DatabaseConn {
	private static DatabaseConn instance = null; 
	
	private DatabaseConn() {
		
	}
	
	public static DatabaseConn getInstance() {
		if(instance == null)
			instance = new DatabaseConn();
		return instance;
	}
	
	public String getState(Integer user, Long chatId) {
		return null;
	}
	
	public void setState(Integer user, Long chatId, String state) {
		
	}
	
	public String getSubState(Integer user, Long chatId) {
		return null;
	}
	
	public void setSubState(Integer user, Long chatId, String state) {
		
	}
	
	public String getOptionsSelected(Integer user, Long chatId) {
		return null;
	}
	
	public void setOptionsSelected(Integer user, Long chatId, String options_txt) {
		
	}
}
