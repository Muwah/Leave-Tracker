package controllers;

import model.AppUser;
import model.Employee;
import model.LeaveApplication;
import services.LeaveApplicationService;
import repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.security.Principal;
import java.util.List;
import java.util.Map;

@Controller
public class EmployeeDashboardController {
	@Autowired
    private AppUserRepository userRepository;

    @Autowired
    private LeaveApplicationService leaveService;

    @GetMapping("/employee/dashboard")
    public String employeeDashboard(Model model, Principal principal) {
        AppUser user = userRepository.findByUsername(principal.getName())
                .orElseThrow(() -> new RuntimeException("User not found: " + principal.getName()));
        
        Employee employee = user.getEmployee();

        List<LeaveApplication> pastLeaves = leaveService.getLeaveApplicationsByEmployee(employee.getId());
        Map<?, Integer> remainingDays = leaveService.getRemainingLeaveDays(employee);

        model.addAttribute("employee", employee);
        model.addAttribute("pastLeaves", pastLeaves);
        model.addAttribute("remainingDays", remainingDays);
        

        return "employee-dashboard";
    }

}
