package controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class IndexController {
	@GetMapping("/")
    public String showIndexPage(Model model) {
        // Add attributes to the model if needed
			System.out.println("Index page loaded.");
        return "index"; // Name of the HTML template in src/main/resources/templates/
    }
}
