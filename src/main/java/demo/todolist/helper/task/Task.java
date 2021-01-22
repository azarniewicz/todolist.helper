package demo.todolist.helper.task;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique=true)
    private String title;

    public Task() {}

    public <title> Task(String title) {
        this.title = title;
    }

    public <title> Task(int id, String title) {
        this.id = id;
        this.title = title;
    }

    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
