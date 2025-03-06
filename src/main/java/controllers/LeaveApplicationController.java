package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.Employee;
import model.LeaveApplication;
import model.LeaveStatus;
import model.Manager;
import services.EmployeeService;
import services.LeaveApplicationService;
import services.ManagerService;

@Controller
@RequestMapping("/leaves")
public class LeaveApplicationController {
    @Autowired
    private LeaveApplicationService leaveApplicationService;
    
    @Autowired
    private EmployeeService employeeService;
    
    @Autowired
    private ManagerService managerService;


    /**
     * Displays the form for applying for leave.
     *
     * @param model The model to pass data to the view.
     * @return The view name for the leave application form.
     */
    @GetMapping("/apply")
    public String applyLeaveForm(Model model) {
        model.addAttribute("leaveApplication", new LeaveApplication());
     // Fetch all employees for the dropdown
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
     // Fetch all managers for the dropdown
        List<Manager> managers = managerService.getAllManagers();
        model.addAttribute("managers", managers);
        return "leave-application-form"; // Points to leave-application-form.html
    }

    /**
     * Handles the submission of the leave application form.
     *
     * @param leaveApplication The leave application data submitted by the user.
     * @return Redirects to the leave list page.
     */
    @PostMapping
    public String applyForLeave(@ModelAttribute LeaveApplication leaveApplication, 
                                @RequestParam("employee.id") Long employeeId,
                                @RequestParam("manager.id") Long managerId) {
        // Fetch the employee by ID
        Employee employee = employeeService.getEmployeeById(employeeId);
        if (employee == null) {
            throw new IllegalArgumentException("Employee not found with ID: " + employeeId);
        }

        // Fetch the manager by ID
        Manager manager = managerService.getManagerById(managerId);
        if (manager == null) {
            throw new IllegalArgumentException("Manager not found with ID: " + managerId);
        }

        // Set the employee and manager for the leave application
        leaveApplication.setEmployee(employee);
        leaveApplication.setManager(manager);

        // Debugging: Print the leave application details
        System.out.println("Applying for leave: " + leaveApplication);
        System.out.println("Employee: " + employee);
        System.out.println("Manager: " + manager);

        // Save the leave application
        leaveApplicationService.applyForLeave(leaveApplication);
        return "redirect:/leaves";
    }
    /**
     * Displays the list of all leave applications.
     *
     * @param model The model to pass data to the view.
     * @return The view name for the leave list.
     */
    @GetMapping
    public String getAllLeaveApplications(Model model) {
        List<LeaveApplication> leaves = leaveApplicationService.getLeaveApplicationsByEmployee(null);
        model.addAttribute("leaves", leaves);
        return "leave-list"; // Points to leave-list.html
    }

    /**
     * Approves a leave application.
     *
     * @param leaveId The ID of the leave application to approve.
     * @return Redirects to the leave list page.
     */
    @PostMapping("/{id}/approve")
    public String approveLeaveApplication(@PathVariable("id") Long leaveId) {
    	System.out.println("Approving leave with ID: " + leaveId); // Debugging
        leaveApplicationService.updateLeaveStatus(leaveId, LeaveStatus.APPROVED);
        return "redirect:/leaves"; // Redirect to the leave list page
    }

    /**
     * Rejects a leave application.
     *
     * @param leaveId The ID of the leave application to reject.
     * @return Redirects to the leave list page.
     */
    @PostMapping("/{id}/reject")
    public String rejectLeaveApplication(@PathVariable("id") Long leaveId) {
    	System.out.println("Rejecting leave with ID: " + leaveId); // Debugging
        leaveApplicationService.updateLeaveStatus(leaveId, LeaveStatus.REJECTED);
        return "redirect:/leaves"; // Redirect to the leave list page
    }
}