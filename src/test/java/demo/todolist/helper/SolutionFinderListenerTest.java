package demo.todolist.helper;

import demo.todolist.helper.solutions.*;
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
        assertEquals(updatedTask.getFindingSolutionStatus(), FindingSolutionStatus.COMPLETED);
    }

    @Test
    public void finding_solution_status_is_changed_when_finder_fails() throws IOException {
        Task task = taskRepository.save(new Task("Test Task"));
        Mockito.when(solutionFinderService.getSolutionsFor(any(TaskDto.class))).thenThrow(RuntimeException.class);

        SolutionFinderListener listener = new SolutionFinderListener(solutionFinderService, taskRepository);
        listener.handle(new TaskAssignedEvent(task));

        Task updatedTask = taskRepository.findById(task.getId()).get();
        assertEquals(updatedTask.getFindingSolutionStatus(), FindingSolutionStatus.FAILED);
    }
}
