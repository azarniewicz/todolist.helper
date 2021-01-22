package demo.todolist.helper;

import io.swagger.annotations.ApiOperation;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.RedirectView;

@Controller
public class HomeController {

    @GetMapping("/")
    @ApiOperation("Returns swagger documentation page for the app.")
    public @ResponseBody RedirectView documentation() {
        return new RedirectView("swagger-ui.html");
    }
}
