package demo.todolist.helper;

import demo.todolist.helper.task.Task;
import demo.todolist.helper.task.TaskRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.hamcrest.Matchers.containsString;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;


import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

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
                .andExpect(content().string(containsString("Task 1")))
                .andExpect(content().string(containsString("Task 2")));

    }

}
