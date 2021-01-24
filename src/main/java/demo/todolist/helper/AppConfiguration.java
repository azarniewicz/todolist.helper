package demo.todolist.helper;

import com.gargoylesoftware.htmlunit.WebClient;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableAsync;

@Configuration
@EnableAsync
public class AppConfiguration {
    @Bean
    public WebClient webClient() {
        return new WebClient();
    }
}
