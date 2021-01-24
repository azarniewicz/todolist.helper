package demo.todolist.helper.solutions;

import com.fasterxml.jackson.annotation.JsonIgnore;
import demo.todolist.helper.tasks.Task;
import demo.todolist.helper.tasks.TaskDto;

import javax.persistence.*;
import javax.persistence.Entity;

@Entity
public class Solution {

    @Id
    @JsonIgnore
    @GeneratedValue
    private int id;

    @Column(nullable = false)
    private String url;

    @JsonIgnore
    @ManyToOne(optional = false)
    @JoinColumn(name = "task_id")
    private Task task;

    public Solution() {
    }

    public Solution(String url, Task task) {
        this.url = url;
        this.task = task;
    }

    public Solution(String url, TaskDto task) {
        this.url = url;
        this.task = new Task(task.id, task.title);
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
