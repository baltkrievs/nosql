package by.darashchonak.mentoring.nosql.model;

/**
 * Created by Mikhail_Darashchonak on 6/23/17.
 */
public class TaskBackObject {

    String name;
    String category;
    String deadline;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getDeadline() {
        return deadline;
    }

    public void setDeadline(String deadline) {
        this.deadline = deadline;
    }
}
