package demo.todolist.helper.tasks;

public interface TaskDao {
    void createTask(String title) throws TaskAlreadyExistsException;
    Iterable<Task> getAllTasks();
    Task updateTaskTitle(int id, String title) throws TaskAlreadyExistsException;
    void delete(int id);
}
