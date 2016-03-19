package movile.hackathon.team_bot;

import com.mongodb.BasicDBList;
import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import movile.hackathon.team_bot.utils.Colecoes;
import movile.hackathon.team_bot.utils.MongoFacade;

import java.net.UnknownHostException;


public class DatabaseConn {
	private static DatabaseConn instance = null;
    private static DBCollection colecao = null;
	
	private DatabaseConn() throws UnknownHostException {
		colecao = MongoFacade.getColecao(Colecoes.DADOS_USUARIOS);
	}
	
	public static DatabaseConn getInstance() {
		if(instance == null)
            try {
                instance = new DatabaseConn();
            } catch (UnknownHostException e) {
                e.printStackTrace();
            }
        return instance;
	}
	
	public String getState(Integer user, Long chatId) {

        BasicDBObject queryUsuario = new BasicDBObject().append("user", user);
        BasicDBObject usuario = (BasicDBObject) colecao.findOne(queryUsuario);

        if(usuario != null) {
            BasicDBList chatStates = (BasicDBList)usuario.get("chatStates");
            BasicDBObject chatState;
            for(Object chatStateObj : chatStates) {
                chatState = (BasicDBObject) chatStateObj;
                if(chatState.getLong("chatId") == chatId) {
                    return chatState.getString("state");
                }
            }
            //throw new Exception("Usuario "+user+", ChatId nao encontrado: "+chatId);

            System.out.println("Usuario "+user+", ChatId nao encontrado: "+chatId);
            return null;
        } else {
            //throw new Exception("Usuario nao encontrado: "+user);
            System.out.println("Usuario nao encontrado: "+user);
            return null;
        }
	}
	
	public void setState(Integer user, Long chatId, String state) {
        BasicDBObject query = new BasicDBObject();
        query.append("user", user);
        query.append("chatStates.chatId", chatId);

        BasicDBObject update = new BasicDBObject("$set", new BasicDBObject().append("chatStates.$.state", state));

        colecao.update(query,update);
	}
	
	public String getSubState(Integer user, Long chatId)  {

        BasicDBObject queryUsuario = new BasicDBObject().append("user", user);
        BasicDBObject usuario = (BasicDBObject) colecao.findOne(queryUsuario);

        if(usuario != null) {
            BasicDBList chatStates = (BasicDBList)usuario.get("chatStates");
            BasicDBObject chatState;
            for(Object chatStateObj : chatStates) {
                chatState = (BasicDBObject) chatStateObj;
                if(chatState.getLong("chatId") == chatId) {
                    return chatState.getString("substate");
                }
            }
            System.out.println("Usuario "+user+", ChatId nao encontrado: "+chatId);
            return null;
        } else {
            System.out.println("Usuario nao encontrado: "+user);
            return null;
        }

	}
	
	public void setSubState(Integer user, Long chatId, String state) {

        BasicDBObject query = new BasicDBObject();
        query.append("user", user);
        query.append("chatStates.chatId", chatId);

        BasicDBObject update = new BasicDBObject("$set", new BasicDBObject().append("chatStates.$.substate", state));

        colecao.update(query,update);
    }
	
    /**
     * Pegar o temporário para os dados inseridos
     * @param user
     * @param chatId
     * @return
     */
	public String getOptionsSelected(Integer user, Long chatId)  {

        BasicDBObject queryUsuario = new BasicDBObject().append("user", user);
        BasicDBObject usuario = (BasicDBObject) colecao.findOne(queryUsuario);

        if(usuario != null) {
            BasicDBList chatStates = (BasicDBList)usuario.get("chatStates");
            BasicDBObject chatState;
            for(Object chatStateObj : chatStates) {
                chatState = (BasicDBObject) chatStateObj;
                if(chatState.getLong("chatId") == chatId) {
                    return chatState.getString("optionsSelected");
                }
            }
            System.out.println("Usuario "+user+", ChatId nao encontrado: "+chatId);
            return null;
        } else {
            System.out.println("Usuario nao encontrado: "+user);
            return null;
        }

	}
	
	/**
	 * Retornar o temporário para os dados inseridos
	 * @param user
	 * @param chatId
	 * @param options_txt
	 */
	public void setOptionsSelected(Integer user, Long chatId, String options_txt) {
        BasicDBObject query = new BasicDBObject();
        query.append("user", user);
        query.append("chatStates.chatId", chatId);

        BasicDBObject update = new BasicDBObject("$set", new BasicDBObject().append("chatStates.$.optionsSelected", options_txt));

        colecao.update(query,update);
	}

    public static void main(String[] args) {

        try {
            DatabaseConn conn = new DatabaseConn();
            conn.setState(1,Long.valueOf(5678),"blable");
        } catch (Exception e) {
            e.printStackTrace();
        }

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

        BasicDBObject query = new BasicDBObject();
        query.append("tipoDocumento","SERVICO");
        query.append("user", user);
        query.append("servico", servico);

        return true;


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
