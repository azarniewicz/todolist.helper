package demo.todolist.helper.tasks;

import org.springframework.context.ApplicationEvent;

public class TaskAssignedEvent extends ApplicationEvent {

    private final Task task;

    public TaskAssignedEvent(Task source) {
        super(source);
        this.task = source;
    }

    public Task getTask() {
        return task;
    }
}
