package demo.todolist.helper.task;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.util.Optional;

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

    @PatchMapping("{id}")
    public ResponseEntity<Object> updateTask(@PathVariable int id, @RequestParam String title) {
        Optional<Task> taskData = taskRepository.findById(id);
        if (taskData.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Task task = taskData.get();
        task.setTitle(title);
        taskRepository.save(task);
        return new ResponseEntity<>(HttpStatus.OK);
    }

    @DeleteMapping("{id}")
    public void deleteTask(@PathVariable int id) {
        taskRepository.deleteById(id);
    }
}
