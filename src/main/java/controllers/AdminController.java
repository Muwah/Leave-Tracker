package controllers;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.AppUser;
import repository.AppUserRepository;

@Controller
@RequestMapping("/admin")
public class AdminController {
	@Autowired
    private AppUserRepository userRepository;
	
	

	@GetMapping("/dashboard")
    public String showAdminDashboard(Model model, Principal principal) {
        String username = principal.getName();
        Optional<AppUser> admin = userRepository.findByUsername(username);
        model.addAttribute("admin", admin);

        List<AppUser> users = userRepository.findAll();
        model.addAttribute("users", users);

        return "admin-dashboard"; // Make sure your template is named this
    }
	

	@PostMapping("/assign-role")
    public String assignRole(@RequestParam Long userId, @RequestParam String role) {
        AppUser user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        user.setRole(role);
        userRepository.save(user);
        return "redirect:/admin/dashboard";
    }
}
