package movile.hackathon.team_bot.repository;

import java.util.List;

public class Usuario {
	 private int userId;
	 private String userName;
	 private float latitude, longitude;
	 private List<Servico> servicos;
	 
	 public Usuario(int userId, String userName, float latitude, float longitude, List<Servico> servicos) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.latitude = latitude;
		this.longitude = longitude;
		this.servicos = servicos;
	}
	
	 
	 public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public float getLatitude() {
		return latitude;
	}
	public void setLatitude(float latitude) {
		this.latitude = latitude;
	}
	public float getLongitude() {
		return longitude;
	}
	public void setLongitude(float longitude) {
		this.longitude = longitude;
	}
	public List<Servico> getServicos() {
		return servicos;
	}
	public void setServicos(List<Servico> servicos) {
		this.servicos = servicos;
	}
	
	 
	 
}
