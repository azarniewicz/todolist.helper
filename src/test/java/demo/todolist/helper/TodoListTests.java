package demo.todolist.helper;

import demo.todolist.helper.task.Task;
import demo.todolist.helper.task.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;
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
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    void task_title_must_be_unique() throws Exception {
        Task task = taskRepository.save(new Task("Test Task"));

        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.post("/api/task/create")
                .param("title", "Test Task");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isBadRequest());

        taskRepository.deleteById(task.getId());
    }

    @Test
    void title_is_required_to_add_task_list_item() throws Exception {
        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.post("/api/task/create")
                .param("title", "");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isBadRequest());

        assertThat(taskRepository.findAll()).isEmpty();
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
    void updated_task_must_exist() throws Exception {
        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.patch("/api/task/{id}", 1)
                .param("title", "Changed Title");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isNotFound());
    }

    @Test
    void updated_title_must_not_be_blank() throws Exception {
        Task task = taskRepository.save(new Task("Test Task"));

        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.patch("/api/task/{id}", task.getId())
                .param("title", "");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isBadRequest());

        assertThat(taskRepository.findByTitle("Test Task")).isNotNull();
    }

    @Test
    void user_can_delete_task() throws Exception {
        Task task = taskRepository.save(new Task("Test Task"));

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/task/{id}", task.getId()))
                .andExpect(status().isOk());

        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }

    @Test
    void task_to_delete_must_exist() throws Exception {
        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.delete("/api/task/{id}", 1);

        this.mockMvc.perform(apiCall)
                .andExpect(status().isNotFound());
    }
}
