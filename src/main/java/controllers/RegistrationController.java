package controllers;



import DTO.RegistrationForm;
import jakarta.transaction.Transactional;
import model.AppUser;
import model.Employee;
import repository.AppUserRepository;
import services.EmployeeService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import java.util.Optional;


@Controller
@RequestMapping("/register")
@Transactional
public class RegistrationController {
	@Autowired private AppUserRepository userRepo;
    @Autowired private EmployeeService employeeService;
    @Autowired private PasswordEncoder passwordEncoder;

    @GetMapping
    public String showForm(Model model) {
        model.addAttribute("registrationForm", new RegistrationForm());
        model.addAttribute("employees", employeeService.getAllEmployees()); // Dropdown if needed
        return "register";
    }

    
    @PostMapping
    public String processForm(@ModelAttribute RegistrationForm form, Model model) {
        Optional<AppUser> existingUser = userRepo.findByUsername(form.getUsername());

        if (existingUser.isPresent()) {
            model.addAttribute("error", "Username already taken.");
            model.addAttribute("employees", employeeService.getAllEmployees());
            return "register";
        }

        AppUser user = new AppUser();
        user.setUsername(form.getUsername());
        String encodedPassword = passwordEncoder.encode(form.getPassword());
        System.out.println("Encoded Password: " + encodedPassword);  // Debug line
        
        user.setPassword(encodedPassword);

        user.setRole("EMPLOYEE");

        Employee emp = employeeService.getEmployeeById(form.getEmployeeId());
        user.setEmployee(emp);

        userRepo.save(user);

        return "redirect:/login?registered";
    }

}
