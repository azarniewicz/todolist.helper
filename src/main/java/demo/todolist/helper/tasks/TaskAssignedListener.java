package demo.todolist.helper.tasks;

import demo.todolist.helper.solutions.SolutionFinder;
import demo.todolist.helper.solutions.SolutionRepository;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
public class TaskAssignedListener {

    final SolutionFinder solutionFinderService;

    final SolutionRepository solutionRepository;
    
    final TaskRepository taskRepository;

    public TaskAssignedListener(
            SolutionFinder solutionFinderService,
            SolutionRepository solutionRepository,
            TaskRepository taskRepository
    ) {
        this.solutionFinderService = solutionFinderService;
        this.solutionRepository = solutionRepository;
        this.taskRepository = taskRepository;
    }

    @Async
    @EventListener
    public void handle(TaskAssignedEvent event) {
        System.out.println("Works here");
        Task task = event.getTask();
        task.setSolutions(solutionFinderService.getSolutionsFor(task.toDto()));
        taskRepository.save(task);
    }
}
