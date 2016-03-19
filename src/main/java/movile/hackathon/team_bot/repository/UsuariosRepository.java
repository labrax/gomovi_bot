package movile.hackathon.team_bot.repository;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCollection;
import movile.hackathon.team_bot.utils.Colecoes;
import movile.hackathon.team_bot.utils.MongoFacade;

import java.net.UnknownHostException;

/**
 * Created by alvaro on 3/19/16.
 */
public class UsuariosRepository {

    DBCollection colecao;

    public UsuariosRepository() throws UnknownHostException {
        this.colecao = MongoFacade.getColecao(Colecoes.DADOS_USUARIOS);
    }

    public BasicDBObject findUsuario(String idUsuario) {

        BasicDBObject query = new BasicDBObject();

        query.append("idUsuario", idUsuario);

        BasicDBObject usuario = (BasicDBObject) this.colecao.findOne(query);

        return usuario;
    }



    public static void main(String[] args) {
        try {
            UsuariosRepository repository = new UsuariosRepository();
            BasicDBObject usuario = repository.findUsuario("12345");
            System.out.println(usuario.get("idUsuario"));
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

}
