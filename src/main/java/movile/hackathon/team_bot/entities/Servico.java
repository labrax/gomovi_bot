package movile.hackathon.team_bot.entities;

public class Servico {
	private String categoria;
	private String sumario;
	private String descricao;
	private String subCategoria;

	public Servico(String categoria, String sumario, String descricao, String subCategoria) {
		super();
		this.categoria = categoria;
		this.sumario = sumario;
		this.descricao = descricao;
		this.subCategoria = subCategoria;
	}
	
	public String getSubCategoria() {
		return subCategoria;
	}
	public void setSubCategoria(String subCategoria) {
		this.subCategoria = subCategoria;
	}
	public String getCategoria() {
		return categoria;
	}
	public void setCategoria(String categoria) {
		this.categoria = categoria;
	}
	public String getSumario() {
		return sumario;
	}
	public void setSumario(String sumario) {
		this.sumario = sumario;
	}
	public String getDescricao() {
		return descricao;
	}
	public void setDescricao(String descricao) {
		this.descricao = descricao;
	}
}
