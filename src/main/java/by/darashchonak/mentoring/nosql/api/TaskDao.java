package by.darashchonak.mentoring.nosql.api;

import by.darashchonak.mentoring.nosql.model.Task;
import by.darashchonak.mentoring.nosql.model.TaskBackObject;

import java.util.List;

/**
 * Created by misha on 6/25/17.
 */
public interface TaskDao {

    List<Task> getAll();

    void save (TaskBackObject taskBackObject);

    void update(String id, TaskBackObject taskBackObject);

    void delete(String id);

    Task findById(String id);

    List<Task> findByCategory(String category);

    List<Task> getOverdue();
}
