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

import model.Employee;
import model.SalaryScale;
import services.EmployeeService;

@Controller
@RequestMapping("/employees")
public class EmployeeController {
	@Autowired
    private EmployeeService employeeService;

    @GetMapping
    public String getAllEmployees(Model model) {
        List<Employee> employees = employeeService.getAllEmployees();
        model.addAttribute("employees", employees);
        return "employee-list"; // Points to employee-list.html in templates folder
    }

    @GetMapping("/add")
    public String addEmployeeForm(Model model) {
        model.addAttribute("employee", new Employee());
        model.addAttribute("salaryScales", SalaryScale.values());
        return "employee-form"; // Points to employee-form.html
    }
    
    @GetMapping("/{id}/edit")
    public String editEmployeeForm(@PathVariable("id") Long id, Model model) {
    	Employee employee = employeeService.getEmployeeById(id);
        model.addAttribute("employee", employee);
        model.addAttribute("salaryScales", SalaryScale.values());// For editing
        return "employee-form"; // Points to employee-form.html
    }

    @PostMapping
    public String saveEmployee(@ModelAttribute Employee employee) {
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }
    
    @PostMapping("/{id}/edit")
    public String updateEmployee(@PathVariable("id") Long id, @ModelAttribute Employee employee) {
        employee.setId(id); // Ensure the ID is set for updating
        employeeService.saveEmployee(employee);
        return "redirect:/employees";
    }

}
