package controllers;

import org.springframework.boot.web.servlet.error.ErrorController;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class CustomErrorController implements ErrorController {

	@GetMapping("/error")
    public String handleError(Model model) {
        model.addAttribute("message", "Something went wrong!");
        return "error"; // Ensure this file exists in templates
    }

    public String getErrorPath() {
        return "/error"; // You can use this for custom error handling if needed
    }

}
