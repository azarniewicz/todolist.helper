package demo.todolist.helper.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;
import javax.validation.ConstraintViolationException;
import javax.validation.constraints.NotBlank;
import java.util.Optional;

@RestController
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @Validated
    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@RequestParam("title") @NotBlank(message = "Title must not be blank") String title) {
        taskRepository.save(new Task(title));
    }

    @Validated
    @GetMapping("")
    public @ResponseBody Iterable<Task> getTasks() {
        return taskRepository.findAll();
    }

    @PatchMapping("{id}")
    public ResponseEntity<Object> updateTask(
            @PathVariable int id,
            @RequestParam @NotBlank(message = "Title must not be blank") String title
    ) {
        Optional<Task> taskData = taskRepository.findById(id);
        if (taskData.isEmpty()) {
            throw new EmptyResultDataAccessException(id);
        }

        Task task = taskData.get();
        task.setTitle(title);
        taskRepository.save(task);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @Validated
    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable int id) {
        taskRepository.deleteById(id);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(ConstraintViolationException.class)
    ResponseEntity<String> handleConstraintViolationException(ConstraintViolationException e) {
        return new ResponseEntity<>("not valid due to validation error: " + e.getMessage(), HttpStatus.BAD_REQUEST);
    }

    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ExceptionHandler(EmptyResultDataAccessException.class)
    ResponseEntity<String> handleEmptyResultDataAccessException(EmptyResultDataAccessException e) {
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @ResponseStatus(HttpStatus.CONFLICT)
    @ExceptionHandler(DataIntegrityViolationException.class)
    ResponseEntity<String> handleDataIntegrityViolationException() {
        return new ResponseEntity<>(HttpStatus.BAD_REQUEST);
    }
}
