package demo.todolist.helper.task;

import javax.persistence.*;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private Long id;

    private String title;

    public Task() {}

    public <title> Task(String title) {
        this.title = title;
    }

    public <title> Task(Long id, String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }
}
