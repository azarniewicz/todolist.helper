package demo.todolist.helper.solutions;

import demo.todolist.helper.tasks.Task;
import demo.todolist.helper.tasks.TaskAssignedEvent;
import demo.todolist.helper.tasks.TaskRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
public class SolutionFinderListener {

    final SolutionFinder solutionFinderService;
    final TaskRepository taskRepository;
    Logger logger = LoggerFactory.getLogger(SolutionFinderListener.class);

    @Autowired
    public SolutionFinderListener(SolutionFinder solutionFinderService, TaskRepository taskRepository) {
        this.solutionFinderService = solutionFinderService;
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
