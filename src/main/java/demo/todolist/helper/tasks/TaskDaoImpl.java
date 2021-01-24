package demo.todolist.helper.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class TaskDaoImpl implements TaskDao {

    private final TaskRepository taskRepository;
    private final ApplicationEventPublisher eventPublisher;

    @Autowired
    public TaskDaoImpl(TaskRepository taskRepository, ApplicationEventPublisher eventPublisher) {
        this.taskRepository = taskRepository;
        this.eventPublisher = eventPublisher;
    }

    @Override
    public void createTask(String title) throws TaskAlreadyExistsException {
        checkIfTaskExists(title);
        Task task = taskRepository.save(new Task(title));
        eventPublisher.publishEvent(new TaskAssignedEvent(task));
    }

    @Override
    public Iterable<Task> getAllTasks() {
        return this.taskRepository.findAll();
    }

    @Override
    public Task updateTaskTitle(int id, String title) throws TaskAlreadyExistsException {
        checkIfTaskExists(title);
        Optional<Task> taskData = taskRepository.findById(id);
        if (taskData.isEmpty()) {
            throw new EmptyResultDataAccessException(id);
        }

        Task task = taskData.get();
        task.setTitle(title);
        return taskRepository.save(task);
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
