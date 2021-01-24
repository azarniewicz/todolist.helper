package demo.todolist.helper;

import demo.todolist.helper.solutions.Solution;
import demo.todolist.helper.solutions.SolutionFinder;
import demo.todolist.helper.solutions.SolutionRepository;
import demo.todolist.helper.solutions.SolutionFinderListener;
import demo.todolist.helper.tasks.*;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class SolutionFinderListenerTest extends TodolistHelperApplicationTests {

    @Mock
    private SolutionFinder solutionFinderService;

    @Mock
    private SolutionRepository solutionRepository;

    @Autowired
    private TaskRepository taskRepository;

    @Test
    public void listener_calls_service_to_find_solutions_and_stores_the_result() throws IOException {
        Task task = taskRepository.save(new Task("Test Task"));
        List<Solution> solutions = new ArrayList<>();
        solutions.add(new Solution("URL 1", task));
        solutions.add(new Solution("URL 2", task));
        Mockito.when(solutionFinderService.getSolutionsFor(any(TaskDto.class))).thenReturn(solutions);

        SolutionFinderListener listener = new SolutionFinderListener(solutionFinderService, taskRepository);
        listener.handle(new TaskAssignedEvent(task));

        Task updatedTask = taskRepository.findById(task.getId()).get();

        assertEquals(updatedTask.getSolutions(), solutions);
    }
}
