package demo.todolist.helper.solutions;

import demo.todolist.helper.tasks.TaskDto;

import java.io.IOException;
import java.util.List;

public interface SolutionFinder {
    List<Solution> getSolutionsFor(TaskDto task) throws IOException;
}
