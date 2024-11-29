package com.cpifppiramide.animalitos.animalito.infrastructure;

import com.cpifppiramide.animalitos.animalito.domain.Animalito;
import com.cpifppiramide.animalitos.animalito.domain.AnimalitosRepository;
import com.cpifppiramide.animalitos.context.MongoDBConnection;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.result.InsertOneResult;
import org.bson.BsonObjectId;
import org.bson.Document;
import org.bson.types.ObjectId;

import java.util.ArrayList;
import java.util.List;

import static com.mongodb.client.model.Filters.eq;

public class AnimalitosRepositoryMongoDB implements AnimalitosRepository {
    @Override
    public Animalito save(Animalito animalito) {
        Document document = new Document();
        document.append("nombre", animalito.getNombre());
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("animalitos");
        InsertOneResult result = collection.insertOne(document);
        String ida = ((BsonObjectId)result.getInsertedId()).getValue().toHexString();
        return new Animalito(collection.find(eq("_id", new ObjectId(ida))).first().getString("nombre"));

    }

    @Override
    public List<Animalito> getAll() {
        List<Animalito> animalitos = new ArrayList<>();
        MongoCollection<Document> collection = MongoDBConnection.getDatabase().getCollection("animalitos");
        for (Document document : collection.find()) {
            //animalitos.add(new Animalito((String) document.get("nombre"), ((String) document.get("nivel")));
            //System.out.println(document.toJson());
        }
        return animalitos;
    }
}
