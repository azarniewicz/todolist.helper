package demo.todolist.helper.tasks;

import demo.todolist.helper.solutions.Solution;
import demo.todolist.helper.solutions.SolutionRepository;

import javax.persistence.*;
import java.util.List;

@Entity
public class Task {

    @Id
    @GeneratedValue
    private int id;

    @Column(unique=true)
    private String title;

    @OneToMany(
            targetEntity = Solution.class,
            mappedBy = "task",
            fetch = FetchType.EAGER,
            cascade = CascadeType.REMOVE,
            orphanRemoval = true
    )
    private List<Solution> solutions;

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

    public List<Solution> getSolutions() {
        return solutions;
    }

    public void setSolutions(List<Solution> solutions) {
        this.solutions = solutions;
    }

    public TaskDto toDto() {
        return new TaskDto(this.getId(), this.getTitle());
    }
}
