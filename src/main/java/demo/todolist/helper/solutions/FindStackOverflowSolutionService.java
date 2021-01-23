package demo.todolist.helper.solutions;

import demo.todolist.helper.tasks.TaskDto;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class FindStackOverflowSolutionService implements SolutionFinder {

    public List<Solution> getSolutionsFor(TaskDto task) {
        return new ArrayList<Solution>();
    }
}
