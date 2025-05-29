package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.ui.Model;
import model.Manager;
import services.ManagerService;

@Controller
@RequestMapping("/managers")
public class ManagerController {
	@Autowired
    private ManagerService managerService;

    @GetMapping
    public String getAllManagers(Model model) {
        List<Manager> managers = managerService.getAllManagers();
        model.addAttribute("managers", managers);
        return "manager-list"; // Points to manager-list.html
    }

    @GetMapping("/add")
    public String addManagerForm(Model model) {
        model.addAttribute("manager", new Manager());
        return "manager-form"; // Points to manager-form.html
    }
    
    @GetMapping("/{id}/edit")
    public String editManagerForm(@PathVariable("id") Long id, Model model) {
        Manager manager = managerService.getManagerById(id);
        model.addAttribute("manager", manager); // For editing
        return "manager-form"; // Points to manager-form.html
    }

    @PostMapping
    public String saveManager(@ModelAttribute Manager manager) {
        managerService.saveManager(manager);
        return "redirect:/managers";
    }
    
    @PostMapping("/{id}/edit")
    public String updateManager(@PathVariable("id") Long id, @ModelAttribute Manager manager) {
        manager.setId(id); // Ensure the ID is set for updating
        managerService.saveManager(manager);
        return "redirect:/managers";
    }

}
