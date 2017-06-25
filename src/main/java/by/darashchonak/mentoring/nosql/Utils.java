package by.darashchonak.mentoring.nosql;

import by.darashchonak.mentoring.nosql.model.Task;
import by.darashchonak.mentoring.nosql.model.TaskBackObject;
import org.bson.Document;

import javax.print.Doc;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by misha on 6/25/17.
 */
public class Utils {

    public static Task convertDocumentToTask(Document doc) {

        Task task = new Task();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");

        task.setId(doc.get("_id").toString());
        task.setName(doc.getString("name"));
        task.setCategory(doc.getString("category"));
        task.setCreationDate(sdf.format((Date)doc.get("created")));
        Object deadLineObject = doc.get("deadline");
        if (null != deadLineObject){
            task.setDeadline(sdf.format((Date) deadLineObject));
        }

        return task;
    }
}
