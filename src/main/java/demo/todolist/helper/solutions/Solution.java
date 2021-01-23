package demo.todolist.helper.solutions;

import demo.todolist.helper.tasks.Task;
import org.hibernate.annotations.*;
import org.hibernate.annotations.CascadeType;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Solution {

    @Id
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String url;

    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    private Task task;

    public Solution() {
    }

    public Solution(String url, Task task) {
        this.url = url;
        this.task = task;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getId() {
        return id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }
}
