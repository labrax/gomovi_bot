package movile.hackathon.team_bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.stream.*;
import java.util.HashMap;
import java.util.List;

import movile.hackathon.team_bot.entities.Servico;
import movile.hackathon.team_bot.entities.Usuario;

public class DatabaseConnMock {
	private static DatabaseConnMock instance = null;
	private static HashMap<String, List<String>> categorias;

	HashMap<String, Object> stateDb = new HashMap<>();
	HashMap<String, Object> subStateDb = new HashMap<>();
	HashMap<String, Object> optionsSelectedDb = new HashMap<>();
	HashMap<String, Object> resultadosBuscaDb = new HashMap<>();
	HashMap<Integer, Object> usuariosDb = new HashMap<>();

	private DatabaseConnMock() {
		categorias = new HashMap<>();

		categorias.put("Assist. Técnica",
				Arrays.asList(
						"Ar condicionado,Cabeamento e Redes,Computador desktop,Notebooks e laptops,Videogame e acessórios"
								.split(",")));
		categorias.put("Moda e Beleza", 
				Arrays.asList(
						"Cabeleireiros,Costura,Depilação,Manicure,Sapateiro"
								.split(",")));
		categorias.put("Reformas", 
				Arrays.asList(
						"Chaveiro,Dedetizador,Eletricista,Encanador,Marido de aluguel,Pedreiro,Pintor"
								.split(",")));
		categorias.put("Saúde", 
				Arrays.asList(
						"Acompanhante de idosos,Enfermeira,Fisioterapeuta"
								.split(",")));
	}
	
	public static DatabaseConnMock getInstance() {
		if (instance == null)
			instance = new DatabaseConnMock();
		return instance;
	}

	public String getState(Integer user, Long chatId) {
		if (!stateDb.containsKey(user + "_" + chatId))
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
	 * 
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
	 * 
	 * @param user
	 * @param chatId
	 * @param options_txt
	 */
	public void setOptionsSelected(Integer user, Long chatId, String options_txt) {
		optionsSelectedDb.put(user +"_"+ chatId, options_txt);
	}
	
	public void addServico(int userId, String categoria, String sumario, String descricao, String subCategoria){
		if(!usuariosDb.containsKey(userId))
			return;
		Usuario usuario = (Usuario) usuariosDb.get(userId);
		usuario.getServicos().add(new Servico(categoria, sumario, descricao, subCategoria));
		usuariosDb.put(userId,usuario);
	}
	
	public void addUsuario(int userId, String userName, float latitude, float longitude){
		if(!usuariosDb.containsKey(userId))
			return;
		Usuario usuario = new Usuario(userId, userName, latitude, longitude);
		usuariosDb.put(userId, usuario);
	}

	/**
	 * Retorna a busca no bd da procura pelos termos...
	 * 
	 * @param categoria
	 * @param sub_categoria
	 * @param location
	 * @return
	 */
	public List<Usuario> getResultadosBusca(String categoria, String subCategoria, float latitude, float longitude) {
		List<Usuario> users = new ArrayList<>();
		for (Integer m : usuariosDb.keySet()) {
			Usuario usuario = (Usuario) usuariosDb.get(m);
			for (Servico n : usuario.getServicos()) {
				if(n.getCategoria().equals(categoria) &&
				 n.getSubCategoria().equals(subCategoria)){
					users.add(usuario);
				}
			}
		}
		return users;
	}
	
	public String getResultadosBuscaLocalizacaoTextual(String sub_categoria, String localizacao) {
		return "";
	}
	
	public String getResultadosBuscaTexto(String texto) {
		return "";
	}
	
	/**
	 * Retorna os serviçoes listados pelo usuário
	 * 
	 * @param user
	 * @return
	 */
	public List<Servico> getServicosUsuario(Integer user) {
		Usuario usuario = (Usuario) usuariosDb.get(user);
		List<Servico> servicos = usuario.getServicos();
		if(servicos.isEmpty())
			return null;
		return servicos;
	}

	/**
	 * deleta um serviço oferecido por um usuário (mostrado do indice em cima)
	 * 
	 * @param user
	 * @param servico
	 * @return TRUE IS SUCCESS; FALSE OTHERWISE
	 */
	public Boolean deletarServico(Integer user, Integer servico) {
		return false;
	}

	/**
	 * retorna o histórico de compras do usuário
	 * 
	 * @param user
	 * @return
	 */
	public String getHistoricoUsuario(Integer user) {
		return null;
	}

	/**
	 * avalia um usuário
	 * 
	 * @param user
	 * @param avaliacao
	 * @return TRUE IF SUCCESS; FALSE OTHERWISE
	 */
	public Boolean avaliar(Integer user, Integer avaliacao) {
		return false;
	}
	
	public List<String> getCategorias() {
		return new ArrayList<>(categorias.keySet());
	}

	public List<String> getSubCategorias(String categoria) {
		return categorias.get(categoria);
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
