package demo.todolist.helper;

import demo.todolist.helper.tasks.Task;
import demo.todolist.helper.tasks.TaskAssignedEvent;
import demo.todolist.helper.tasks.TaskRepository;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import static org.hamcrest.Matchers.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.atLeastOnce;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(SpringRunner.class)
public class TodoListTests extends TodolistHelperApplicationTests {

    @Autowired
    protected TaskRepository taskRepository;

    @Autowired
    private ConfigurableApplicationContext configurableApplicationContext;

    @Test
    public void user_can_add_a_task_list_item() throws Exception {
        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.post("/api/task/create")
                .param("title", "Write test");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isCreated());

        assertThat(taskRepository.findByTitle("Write test")).isNotNull();
    }

    @Test
    public void adding_task_results_in_publishing_task_event() throws Exception {
        ApplicationListener<TaskAssignedEvent> taskAssignedListener = Mockito.mock(ApplicationListener.class);
        configurableApplicationContext.addApplicationListener(taskAssignedListener);

        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.post("/api/task/create")
                .param("title", "Test Task");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isCreated());

        Mockito.verify(taskAssignedListener, atLeastOnce()).onApplicationEvent(any(TaskAssignedEvent.class));
    }

    @Test
    @Transactional(propagation = Propagation.NOT_SUPPORTED)
    public void task_title_must_be_unique() throws Exception {
        Task task = taskRepository.save(new Task("Test Task"));

        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.post("/api/task/create")
                .param("title", "Test Task");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isBadRequest());

        taskRepository.deleteById(task.getId());
    }

    @Test
    public void title_is_required_to_add_task_list_item() throws Exception {
        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.post("/api/task/create")
                .param("title", "");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isBadRequest());

        assertThat(taskRepository.findAll()).isEmpty();
    }

    @Test
    public void user_can_fetch_a_task_list() throws Exception {
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
    public void user_can_update_task() throws Exception {
        Task task = taskRepository.save(new Task("Test Task"));

        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.patch("/api/task/{id}", task.getId())
                .param("title", "Changed Title");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isOk());

        assertThat(taskRepository.findByTitle("Changed Title")).isNotNull();
    }

    @Test
    public void updated_task_must_exist() throws Exception {
        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.patch("/api/task/{id}", 1)
                .param("title", "Changed Title");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isNotFound());
    }

    @Test
    public void updated_title_must_not_be_blank() throws Exception {
        Task task = taskRepository.save(new Task("Test Task"));

        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.patch("/api/task/{id}", task.getId())
                .param("title", "");

        this.mockMvc.perform(apiCall)
                .andExpect(status().isBadRequest());

        assertThat(taskRepository.findByTitle("Test Task")).isNotNull();
    }

    @Test
    public void user_can_delete_task() throws Exception {
        Task task = taskRepository.save(new Task("Test Task"));

        this.mockMvc.perform(MockMvcRequestBuilders.delete("/api/task/{id}", task.getId()))
                .andExpect(status().isOk());

        assertThat(taskRepository.findById(task.getId())).isEmpty();
    }

    @Test
    public void task_to_delete_must_exist() throws Exception {
        MockHttpServletRequestBuilder apiCall = MockMvcRequestBuilders.delete("/api/task/{id}", 1);

        this.mockMvc.perform(apiCall)
                .andExpect(status().isNotFound());
    }
}
