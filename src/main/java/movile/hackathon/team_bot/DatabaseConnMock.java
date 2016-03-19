package movile.hackathon.team_bot;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.stream.*;

public class DatabaseConnMock {
	private static DatabaseConnMock instance = null;
	private static HashMap<String, List<String>> categorias;

	HashMap<String, Object> stateDb = new HashMap<>();
	HashMap<String, Object> subStateDb = new HashMap<>();

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
		return null;
	}

	/**
	 * Retornar o temporário para os dados inseridos
	 * 
	 * @param user
	 * @param chatId
	 * @param options_txt
	 */
	public void setOptionsSelected(Integer user, Long chatId, String options_txt) {

	}

	/**
	 * Retorna a busca no bd da procura pelos termos...
	 * 
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
	 * 
	 * @param user
	 * @return
	 */
	public String getServicosUsuario(Integer user) {
		return null;
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
}
