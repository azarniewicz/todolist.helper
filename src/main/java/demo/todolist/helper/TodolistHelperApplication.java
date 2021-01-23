package demo.todolist.helper;

import io.swagger.annotations.ApiOperation;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
@EnableSwagger2
public class TodolistHelperApplication {

	public static void main(String[] args) {
		SpringApplication.run(TodolistHelperApplication.class, args);
	}

	@Controller
	public static class HomeController {
		@GetMapping("/")
		@ApiOperation("Returns swagger documentation page for the app.")
		public @ResponseBody
		RedirectView documentation() {
			return new RedirectView("swagger-ui.html");
		}
	}

}
