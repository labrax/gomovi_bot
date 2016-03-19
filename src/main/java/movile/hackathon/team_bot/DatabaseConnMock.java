package movile.hackathon.team_bot;

import java.util.HashMap;

import movile.hackathon.team_bot.repository.Servico;
import movile.hackathon.team_bot.repository.Usuario;

public class DatabaseConnMock {
	private static DatabaseConnMock instance = null; 

	HashMap<String, Object> stateDb = new HashMap<>();
	HashMap<String, Object> subStateDb = new HashMap<>();
	HashMap<String, Object> optionsSelectedDb = new HashMap<>();
	HashMap<String, Object> resultadosBuscaDb = new HashMap<>();
	HashMap<String, Object> usuariosDb = new HashMap<>();
	
	
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
		if(!optionsSelectedDb.containsKey(user+"_"+chatId))
			return null;
		return (String) optionsSelectedDb.get(user+"_"+chatId);
	}
	
	/**
	 * Retornar o temporário para os dados inseridos
	 * @param user
	 * @param chatId
	 * @param options_txt
	 */
	public void setOptionsSelected(Integer user, Long chatId, String options_txt) {
		optionsSelectedDb.put(user +"_"+ chatId, options_txt);
	}
	
	public void addServico(int userId, String categoria, String sumario, String descricao, String subCategoria){
		Usuario usuario = (Usuario) usuariosDb.get(userId);
		usuario.getServicos().add(new Servico(categoria, sumario, descricao, subCategoria));
	}
	
	public void addUsuario(int userId, String userName, float latitude, float longitude){
		
		
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
	
	public String getResultadosBuscaLocalizacaoTextual(String sub_categoria, String localizacao) {
		return "";
	}
	
	public String getResultadosBuscaTexto(String texto) {
		return "";
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
