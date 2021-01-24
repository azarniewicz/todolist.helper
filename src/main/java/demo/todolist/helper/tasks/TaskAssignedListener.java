package demo.todolist.helper.tasks;

import demo.todolist.helper.solutions.SolutionFinder;
import demo.todolist.helper.solutions.SolutionRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class TaskAssignedListener {

    final SolutionFinder solutionFinderService;
    final SolutionRepository solutionRepository;
    final TaskRepository taskRepository;
    Logger logger = LoggerFactory.getLogger(TaskAssignedListener.class);

    @Autowired
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
        Task task = event.getTask();
        try {
            task.setSolutions(solutionFinderService.getSolutionsFor(task.toDto()));
            taskRepository.save(task);
        } catch (Exception e) {
            logger.error(e.getMessage());
        }
    }
}
