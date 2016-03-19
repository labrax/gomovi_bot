package movile.hackathon.team_bot.utils;

import com.mongodb.*;
import com.mongodb.gridfs.GridFS;
import com.mongodb.util.JSON;

import java.net.UnknownHostException;
import java.security.InvalidParameterException;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;


public class MongoFacade {

    private static DB mongoDB;

    static{
        mongoDB = null;
    }


    public static DBCollection getColecao(Colecoes coll)
            throws UnknownHostException, MongoException {
        String pNomeColecao = coll.toString().toLowerCase();

        if (mongoDB == null) {
            ResourceBundle rb = ResourceBundle.getBundle("config");

            int port = 27017;
            String portStr = rb.getString("mongodb.port");
            if(portStr != null && !portStr.trim().isEmpty()){
                port = Integer.valueOf(portStr);
            }

            Mongo mongo = new Mongo(rb.getString("mongodb.ip"),port);

            final String dbName = rb.getString("mongodb.db");
            if(mongo.getDatabaseNames().contains(dbName)){
                mongoDB = mongo.getDB(dbName);
            }
            else{
                throw new InvalidParameterException(
                        "Banco de dados não encontrado. Nome: '"+dbName+"'");
            }
        }

        DBCollection colecao = mongoDB.getCollection(pNomeColecao);
        return colecao;
    }


    public static GridFS getGridFS(Colecoes coll)
            throws UnknownHostException, MongoException {
        String pNomeColecao = coll.toString().toLowerCase();

        if (mongoDB == null) {
            ResourceBundle rb = ResourceBundle.getBundle("config");

            Mongo mongo = new Mongo(rb.getString("mongodb.ip"));

            final String dbName = rb.getString("mongodb.db");
            if(mongo.getDatabaseNames().contains(dbName)){
                mongoDB = mongo.getDB(dbName);
            }
            else{
                throw new InvalidParameterException(
                        "Banco de dados não encontrado. Nome: '"+dbName+"'");
            }
        }

        GridFS grid = new GridFS(mongoDB, pNomeColecao);
        return grid;
    }


    private static MapReduceOutput performMapReduce(DBCollection colecao,
                                                    String mapReduce, DBObject query) {
        DBObject cmd = new BasicDBObject();

        cmd.put("mapreduce", colecao.getName());

        cmd.putAll((DBObject) JSON.parse(mapReduce));

        if (query != null){
            cmd.put("query", query);
        }

        return colecao.mapReduce(cmd);
    }

    public static DBCollection mapReduceDBCollection(DBCollection colecao,
                                                     String mapReduce, DBObject query) {

        MapReduceOutput out = performMapReduce(colecao, mapReduce, query);

        return out.getOutputCollection();
    }



    public static Iterable<DBObject> mapReduceIterable(DBCollection colecao,
                                                       String mapReduce, DBObject query) {

        MapReduceOutput out = performMapReduce(colecao, mapReduce, query);
        return out.results();
    }

    public static List<DBObject> group(DBCollection colecao, String group, DBObject filter) {
        DBObject cmd = (DBObject) JSON.parse(group);

        GroupCommand command = new GroupCommand(colecao, (DBObject)cmd.get("key"), filter,
                (DBObject)cmd.get("initial"), (String)cmd.get("reduce"), (String)cmd.get("finalize"));

        List<DBObject> listaRetorno = new ArrayList<DBObject>();
        DBObject resultado = colecao.group(command);

        if(resultado instanceof BasicDBObject){
            listaRetorno.add(resultado);
        }
        else if(resultado instanceof BasicDBList){
            BasicDBList lista = (BasicDBList) resultado;
            for(Object objeto : lista){
                listaRetorno.add((DBObject) objeto);
            }
        }

        return listaRetorno;
    }
}