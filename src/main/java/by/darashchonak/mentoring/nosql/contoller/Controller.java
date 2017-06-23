package by.darashchonak.mentoring.nosql.contoller;

import by.darashchonak.mentoring.nosql.model.TaskBackObject;
import com.mongodb.MongoClient;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by Mikhail_Darashchonak on 6/23/17.
 */
@org.springframework.stereotype.Controller
public class Controller {

    @RequestMapping(path = "/", method = RequestMethod.GET)
    public String mainPage() {



        return "index";
    }

    @RequestMapping(path = "/add", method = RequestMethod.GET)
    public String showTaskForm(){

        return "add";
    }

    @RequestMapping(path = "/add", method = RequestMethod.POST)
    public String addTask(@ModelAttribute TaskBackObject task){

        MongoClient mongoClient = new MongoClient();
        MongoDatabase database = mongoClient.getDatabase("mydb");
        MongoCollection<Document> collection = database.getCollection("tasks");

        Calendar calendar = Calendar.getInstance();
        Date date = calendar.getTime();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");


        Document doc = new Document("name", task.getName())
                .append("category", task.getCategory())
                .append("created", date);

        collection.insertOne(doc);

        mongoClient.close();

        System.out.println(task.getName());
        System.out.println(task.getCategory());
        System.out.println(task.getDeadline());

        return "add";
    }

}
