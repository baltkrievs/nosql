package by.darashchonak.mentoring.nosql.api;

import by.darashchonak.mentoring.nosql.model.Task;
import by.darashchonak.mentoring.nosql.model.TaskBackObject;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import org.bson.Document;
import org.bson.types.ObjectId;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;
import by.darashchonak.mentoring.nosql.Utils;

import static com.mongodb.client.model.Filters.*;

/**
 * Created by misha on 6/25/17.
 */
@Component
public class MongoDaoImpl implements TaskDao {

    @Autowired
    @Qualifier("collection")
    private MongoCollection<Document> collection;

    @Override
    public List<Task> getAll() {

        MongoCursor<Document> cursor = collection.find().iterator();
        List<Task> tasks = new ArrayList<>();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Task task = Utils.convertDocumentToTask(doc);
            tasks.add(task);
        }

        cursor.close();

        return tasks;
    }

    @Override
    public void save(TaskBackObject task) {

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        Date deadline = null;

        String mystring = task.getDeadline().trim();

        if (!mystring.isEmpty()){

            try {
                deadline = new SimpleDateFormat("dd/MM/yyyy").parse(mystring);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }


        Document doc = new Document("name", task.getName())
                .append("category", task.getCategory())
                .append("created", date)
                .append("deadline", deadline);

        collection.insertOne(doc);

    }

    @Override
    public Task findById(String id) {

        Document doc = collection.find(eq("_id", new ObjectId(id))).first();
        System.out.println(doc.toJson());

        return Utils.convertDocumentToTask(doc);
    }

    @Override
    public void update(String id, TaskBackObject task) {

        Date deadline = null;
        String mystring = task.getDeadline().trim();
        if (!mystring.isEmpty()){

            try {
                deadline = new SimpleDateFormat("dd/MM/yyyy").parse(mystring);
            } catch (ParseException e) {
                e.printStackTrace();
            }
        }

        Document update = new Document("name", task.getName())
                .append("category", task.getCategory())
                .append("deadline", deadline);

        collection.updateOne(eq("_id", new ObjectId(id)), new Document("$set", update));

    }

    @Override
    public void delete(String id) {
        collection.deleteMany(eq("_id", new ObjectId(id)));
    }

    @Override
    public List<Task> findByCategory(String category) {

        List<Task> tasks = new ArrayList<>();

        MongoCursor<Document> cursor = collection.find(eq("category", category)).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Task task = Utils.convertDocumentToTask(doc);
            tasks.add(task);
        }

        cursor.close();

        return tasks;
    }

    @Override
    public List<Task> getOverdue() {

        List<Task> tasks = new ArrayList<>();

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();

        MongoCursor<Document> cursor = collection.find(lt("deadline", date)).iterator();

        while (cursor.hasNext()) {
            Document doc = cursor.next();
            Task task = Utils.convertDocumentToTask(doc);
            tasks.add(task);
        }

        cursor.close();

        return tasks;
    }
}
