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
		return false;
	}
	
	/**
	 * retorna as ofertas que um usuário tem pesquisando pelo nome dele!
	 * @param user
	 * @return
	 */
	public String getDetalhesUsuario(String nome) {
		return "";
	}
}
