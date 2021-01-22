package demo.todolist.helper.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("api/task")
public class TaskController {

    @Autowired
    private TaskRepository taskRepository;

    @PostMapping("create")
    @ResponseStatus(HttpStatus.CREATED)
    public void addTask(@RequestParam String title) {
        taskRepository.save(new Task(title));
    }

    @GetMapping("")
    public @ResponseBody Iterable<Task> getTasks() {
        return taskRepository.findAll();
    }

}
