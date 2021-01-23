package demo.todolist.helper.solutions;

import demo.todolist.helper.tasks.TaskDto;

import java.util.List;

public interface SolutionFinder {
    public List<Solution> getSolutionsFor(TaskDto task);
}
