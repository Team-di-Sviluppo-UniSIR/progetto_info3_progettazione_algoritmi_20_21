package testVarieCose;

import java.util.Iterator;

import org.bson.Document;
import org.bson.conversions.Bson;
import org.json.JSONObject;

import com.mongodb.BasicDBObject;
import com.mongodb.DBCursor;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

/*Classe di test per query di dati in MongoDB Atlas*/

public class trialGetCapacita {
		
	private static int countQueryResults(FindIterable<Document> docToCount) {
		int count=0;
		for(Document d: docToCount) {
			count++;
		}
		
		/*	Introdurre test invece di usare questo metodo
			System.out.println("numero di risultati:"+count);*/
		
		return count;
	}
	
	public static int getCapacita(String nomeMensa) {
		
		//-1 = errore
		int capacita = -1;
		
		//stabilisco la connessione con database: DBCampus, collezione: DBCampusCollection
		MongoClientURI uri = new MongoClientURI(
				"mongodb://admin:admin@cluster0-shard-00-00.qfzol.mongodb.net:27017,cluster0-shard-00-01.qfzol.mongodb.net:27017,cluster0-shard-00-02.qfzol.mongodb.net:27017/DBCampus?ssl=true&replicaSet=atlas-9gfc0g-shard-0&authSource=admin&retryWrites=true&w=majority");
		MongoClient mongoClient = new MongoClient(uri);
		MongoDatabase mongoDB = mongoClient.getDatabase("DBCampus");
		MongoCollection<Document> collection = mongoDB.getCollection("DBCampusCollection");
		
		//preparazione filtro di query
		final Bson filterQueryLemon = new Document("nome", nomeMensa);
		
		//risultati ottenuti (come lista di Document)
		FindIterable<Document> queryRes=collection.find(filterQueryLemon);
		
		//mi assicuro di ricevere 1 solo risultato (1 sola mensa per nome)
		if(countQueryResults(queryRes)!=1)
			throw new RuntimeException();
		else {
			JSONObject JMensa = new JSONObject(queryRes.first().toJson());
			capacita = JMensa.getInt("capacita");
			}
		
		return capacita;
	}
	
	public static void main(String[] args) {
		
		String nomeMensa = "Green Canteen";
		System.out.println("Capacit� trovata: " + getCapacita(nomeMensa));
		
	}

}
