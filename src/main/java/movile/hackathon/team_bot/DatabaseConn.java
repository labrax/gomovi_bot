package movile.hackathon.team_bot;

import org.telegram.telegrambots.api.objects.Location;

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
	
	/**
	 * Pegar o temporário para os dados inseridos
	 * @param user
	 * @param chatId
	 * @return
	 */
	public String getOptionsSelected(Integer user, Long chatId) {
		return null;
	}
	
	/**
	 * Retornar o temporário para os dados inseridos
	 * @param user
	 * @param chatId
	 * @param options_txt
	 */
	public void setOptionsSelected(Integer user, Long chatId, String options_txt) {
		
	}
	
	/**
	 * Retorna a busca no bd da procura pelos termos...
	 * @param categoria
	 * @param sub_categoria
	 * @param location
	 * @return
	 */
	public String getResultadosBusca(String categoria, String sub_categoria, float latitude, float longitude) {
		return null;
	}
	
	/**
	 * Retorna os serviçoes listados pelo usuário
	 * @param user
	 * @return
	 */
	public String getServicosUsuario(Integer user) {
		return null;
	}
	
	/**
	 * deleta um serviço oferecido por um usuário (mostrado do indice em cima)
	 * @param user
	 * @param servico
	 * @return TRUE IS SUCCESS; FALSE OTHERWISE
	 */
	public Boolean deletarServico(Integer user, Integer servico) {
		return false;
	}
	
	/**
	 * retorna o histórico de compras do usuário
	 * @param user
	 * @return
	 */
	public String getHistoricoUsuario(Integer user) {
		return null;
	}
	
	/**
	 * avalia um usuário
	 * @param user
	 * @param avaliacao
	 * @return TRUE IF SUCCESS; FALSE OTHERWISE
	 */
	public Boolean avaliar(Integer user, Integer avaliacao) {
		
	}
}
