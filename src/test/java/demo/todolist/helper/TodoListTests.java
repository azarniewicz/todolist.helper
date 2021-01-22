package demo.todolist.helper;

import demo.todolist.helper.task.Task;
import demo.todolist.helper.task.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.Matchers.*;

public class TodoListTests extends TodolistHelperApplicationTests {

    @Autowired
    protected TaskRepository taskRepository;

    @Test
    void user_can_add_a_task_list_item() throws Exception {
        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.post("/api/task/create")
                .param("title", "Write test");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isCreated());

        assertThat(taskRepository.findByTitle("Write test")).isNotNull();
    }

    @Test
    void user_can_fetch_a_task_list() throws Exception {
        taskRepository.save(new Task("Task 1"));
        taskRepository.save(new Task("Task 2"));

        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.get("/api/task")
                .param("title", "Write test");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$", hasSize(2)))
                .andExpect(jsonPath("$[0].title", is("Task 1")))
                .andExpect(jsonPath("$[1].title", is("Task 2")));
    }

    @Test
    void user_can_update_task() throws Exception {
        Task task = taskRepository.save(new Task("Test Task"));

        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.patch("/api/task/{id}", task.getId())
                .param("title", "Changed Title");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isOk());

        assertThat(taskRepository.findByTitle("Changed Title")).isNotNull();
    }

    @Test
    void user_can_delete_task() throws Exception {
        Task task = taskRepository.save(new Task("Test Task"));

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/task/{id}", task.getId()))
                .andExpect(status().isOk());

        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }
}
