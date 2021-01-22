package demo.todolist.helper;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@AutoConfigureMockMvc
class HomeControllerTests extends TodolistHelperApplicationTests {

    @Autowired
    private HomeController controller;

    @Test
    void context_loads() {
        assertThat(controller).isNotNull();
    }

    @Test
    void home_should_return_swagger_page() throws Exception {
        this.mockMvc.perform(get("/"))
                .andExpect(redirectedUrl("swagger-ui.html"));
    }

}
