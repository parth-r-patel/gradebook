package app;

import com.mongodb.Block;
import com.mongodb.MongoException;
import com.mongodb.async.SingleResultCallback;
import com.mongodb.async.client.MongoClient;
import com.mongodb.async.client.MongoClients;
import com.mongodb.async.client.MongoCollection;
import com.mongodb.async.client.MongoDatabase;
import javafx.beans.property.SimpleLongProperty;
import org.bson.*;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;

import java.util.*;

/**
 * Created by Parth on 2017-09-07.
 */
public class Storage {
    private static MongoClient mongoClient;
    private static MongoCollection<BsonDocument> ics3u1;

    public static ArrayList<Student> students = new ArrayList<>();

    private static Block<BsonDocument> printBlock = (student) -> {
        Student s = new Student(student.get("firstName").asString().getValue() + (char) 32 +  student.get("lastName").asString().getValue());
        BsonArray tests = student.getArray("tests");
        Student.testSize = tests.size() == 0 ? 1 : tests.size();
        for(BsonValue score : tests) {
            s.getAllTestScores().add(new SimpleLongProperty(score.asDouble().longValue()));
        }
        students.add(s);
    };

    private static SingleResultCallback<Void> queryFinished = (result, t) -> {
        System.out.println("Finished Operation");
    };

    public static void connectMongoClient() {
        MongoDatabase mongoDatabase;

        if(mongoClient != null)
            cleanup();
        mongoClient = MongoClients.create("mongodb://localhost");
        mongoDatabase = mongoClient.getDatabase("grdbk");
        ics3u1 = mongoDatabase.getCollection("ics3u1", BsonDocument.class);
    }

    public static void cleanup() {
        mongoClient.close();
    }

    public static void getStudents() {
        ics3u1.find().forEach(printBlock, queryFinished);
    }
}
