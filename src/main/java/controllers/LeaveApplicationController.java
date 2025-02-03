package controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import model.LeaveApplication;
import services.LeaveApplicationService;

@Controller
@RequestMapping("/leaves")
public class LeaveApplicationController {
	@Autowired
    private LeaveApplicationService leaveApplicationService;

    @GetMapping("/apply")
    public String applyLeaveForm(Model model) {
        model.addAttribute("leaveApplication", new LeaveApplication());
        return "leave-application-form"; // Points to leave-application-form.html
    }

    @PostMapping
    public String applyForLeave(@ModelAttribute LeaveApplication leaveApplication) {
        leaveApplicationService.applyForLeave(leaveApplication);
        return "redirect:/leaves";
    }

    @GetMapping
    public String getAllLeaveApplications(Model model) {
        List<LeaveApplication> leaves = leaveApplicationService.getLeaveApplicationsByEmployee(null);
        model.addAttribute("leaves", leaves);
        return "leave-list"; // Points to leave-list.html
    }

}
