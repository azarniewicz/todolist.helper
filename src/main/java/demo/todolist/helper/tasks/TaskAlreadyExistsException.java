package demo.todolist.helper.tasks;

public class TaskAlreadyExistsException extends Exception {
    public TaskAlreadyExistsException() {
        super("Task with that title already exists");
    }
}
