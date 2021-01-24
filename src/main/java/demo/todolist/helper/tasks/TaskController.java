package demo.todolist.helper.tasks;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import javax.validation.constraints.NotBlank;

@Validated
@RestController
@RequestMapping("api/task")
public class TaskController {

    private final TaskDao taskDao;

    @Autowired
    public TaskController(TaskDao taskDao) {
        this.taskDao = taskDao;
    }

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody Task addTask(
            @RequestParam("title")
            @NotBlank(message = "Title must not be blank") String title
    ) throws TaskAlreadyExistsException {
        return taskDao.createTask(title);
    }

    @GetMapping("")
    public @ResponseBody Iterable<Task> getTasks() {
        return taskDao.getAllTasks();
    }

    @PatchMapping("{id}")
    public @ResponseBody Task updateTask(
            @PathVariable int id,
            @RequestParam @NotBlank(message = "Title must not be blank") String title
    ) throws TaskAlreadyExistsException {
        return taskDao.updateTaskTitle(id, title);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable int id) {
        taskDao.delete(id);
    }
}
