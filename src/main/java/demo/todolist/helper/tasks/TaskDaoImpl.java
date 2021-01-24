package demo.todolist.helper.tasks;

import demo.todolist.helper.solutions.FindingSolutionStatus;
import demo.todolist.helper.solutions.Solution;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;
import java.util.Optional;

@Service
public class TaskDaoImpl implements TaskDao {

    private final TaskRepository taskRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public TaskDaoImpl(
            TaskRepository taskRepository,
            ApplicationEventPublisher eventPublisher
    ) {
        this.taskRepository = taskRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public Task createTask(String title) throws TaskAlreadyExistsException {
        checkIfTaskExists(title);
        Task task = taskRepository.save(new Task(title));
        eventPublisher.publishEvent(new TaskAssignedEvent(task));
        return task;
    }

    @Override
    public Iterable<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    @Override
    @Transactional
    public Task updateTaskTitle(int id, String title) throws TaskAlreadyExistsException {
        checkIfTaskExists(title);
        Optional<Task> taskData = taskRepository.findById(id);
        if (taskData.isEmpty()) {
            throw new EmptyResultDataAccessException(id);
        }

        Task task = taskData.get();
        task.setTitle(title);
        removeTaskSolutions(task);
        task = taskRepository.save(task);
        eventPublisher.publishEvent(new TaskAssignedEvent(task));
        return task;
    }

    private void removeTaskSolutions(Task task) {
        List<Solution> taskSolutions = task.getSolutions();
        if (taskSolutions != null) {
            task.getSolutions().removeAll(taskSolutions);
        }
        task.setFindingSolutionStatus(FindingSolutionStatus.IN_PROGRESS);
    }

    @Override
    public void delete(int id) {
        taskRepository.deleteById(id);
    }

    private void checkIfTaskExists(String title) throws TaskAlreadyExistsException {
        if (this.taskRepository.existsByTitle(title)) {
            throw new TaskAlreadyExistsException();
        }
    }

}
