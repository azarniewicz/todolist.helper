package demo.todolist.helper.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationEventPublisher;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;
import java.util.Optional;

@Validated
@RestController
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Autowired
    private ApplicationEventPublisher eventPublisher;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@RequestParam("title") @NotBlank(message = "Title must not be blank") String title) {
        Task task = taskRepository.save(new Task(title));
        eventPublisher.publishEvent(new TaskAssignedEvent(task));
    }

    @GetMapping("")
    public @ResponseBody Iterable<Task> getTasks() {
        return taskRepository.findAll();
    }

    @PatchMapping("{id}")
    public @ResponseBody Task updateTask(
            @PathVariable int id,
            @RequestParam @NotBlank(message = "Title must not be blank") String title
    ) {
        Optional<Task> taskData = taskRepository.findById(id);
        if (taskData.isEmpty()) {
            throw new EmptyResultDataAccessException(id);
        }

        Task task = taskData.get();
        task.setTitle(title);
        return taskRepository.save(task);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable int id) {
        taskRepository.deleteById(id);
    }
}
