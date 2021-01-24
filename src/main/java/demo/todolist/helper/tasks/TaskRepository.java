package demo.todolist.helper.tasks;

import org.springframework.data.repository.CrudRepository;

public interface TaskRepository extends CrudRepository<Task, Integer> {
    Task findByTitle(String title);
    boolean existsByTitle(String title);
}
