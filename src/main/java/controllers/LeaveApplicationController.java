package controllers;

import model.*;
import services.EmployeeService;
import services.LeaveApplicationService;
import services.ManagerService;
import services.PdfGenerationService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import DTO.LeaveComputation;
import jakarta.servlet.http.HttpServletResponse;
import java.io.File;
import java.nio.file.Files;
//import java.util.Arrays;
import java.util.List;

@Controller
@RequestMapping("/leaves")
public class LeaveApplicationController {

    @Autowired
    private LeaveApplicationService leaveApplicationService;

    @Autowired
    private EmployeeService employeeService;

    @Autowired
    private ManagerService managerService;
    
    @Autowired
    private PdfGenerationService pdfGenerationService;

    /**
     * Displays the form for applying for leave.
     */
    @GetMapping("/apply")
    public String applyLeaveForm(Model model) {
        model.addAttribute("leaveApplication", new LeaveApplication());

        model.addAttribute("employees", employeeService.getAllEmployees());
        model.addAttribute("managers", managerService.getAllManagers());
        model.addAttribute("leaveTypes", LeaveType.values());
        model.addAttribute("salaryScales", SalaryScale.values());

        return "leave-application-form";
    }

    /**
     * Handles submission of leave application.
     */
    /*
    @PostMapping
    public String applyForLeave(@ModelAttribute LeaveApplication leaveApplication,
                                @RequestParam("employee.id") Long employeeId,
                                @RequestParam("manager.id") Long managerId,
                                @RequestParam("toOfficer.id") Long toOfficerId,
                                @RequestParam("thruOfficer1.id") Long thru1Id,
                                @RequestParam("thruOfficer2.id") Long thru2Id,
                                @RequestParam("leaveType") LeaveType leaveType,
                                @RequestParam("salaryScale") SalaryScale salaryScale,
                                RedirectAttributes redirectAttributes) {

        try {
            // Set linked entities
            leaveApplication.setEmployee(employeeService.getEmployeeById(employeeId));
            leaveApplication.setManager(managerService.getManagerById(managerId));
            leaveApplication.setToOfficer(employeeService.getEmployeeById(toOfficerId));
            leaveApplication.setThruOfficer1(employeeService.getEmployeeById(thru1Id));
            leaveApplication.setThruOfficer2(employeeService.getEmployeeById(thru2Id));

            // Set enums
            leaveApplication.setLeaveType(leaveType);
            leaveApplication.setSalaryScale(salaryScale);

            leaveApplicationService.applyForLeave(leaveApplication);
            redirectAttributes.addFlashAttribute("success", "Leave application submitted successfully.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to apply for leave: " + ex.getMessage());
            return "redirect:/leaves/apply";
        }

        return "redirect:/leaves";
    }*/
    
    @PostMapping
    public String applyForLeave(@ModelAttribute LeaveApplication leaveApplication,
                                @RequestParam("employee.id") Long employeeId,
                                @RequestParam("toOfficer.id") Long toOfficerId,
                                @RequestParam("thruOfficer1.id") Long thru1Id,
                                @RequestParam("thruOfficer2.id") Long thru2Id,
                                @RequestParam("leaveType") LeaveType leaveType,
                                @RequestParam("salaryScale") SalaryScale salaryScale,
                                RedirectAttributes redirectAttributes) {
        try {
            // Set resolved entities
            leaveApplication.setEmployee(employeeService.getEmployeeById(employeeId));
            leaveApplication.setToOfficer(employeeService.getEmployeeById(toOfficerId));
            leaveApplication.setThruOfficer1(employeeService.getEmployeeById(thru1Id));
            leaveApplication.setThruOfficer2(employeeService.getEmployeeById(thru2Id));

            // Set enums
            leaveApplication.setLeaveType(leaveType);
            leaveApplication.setSalaryScale(salaryScale);

            leaveApplicationService.applyForLeave(leaveApplication);
            redirectAttributes.addFlashAttribute("success", "Leave application submitted successfully.");

        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Error: " + ex.getMessage());
            return "redirect:/leaves/apply";
        }

        return "redirect:/employee/dashboard";
    }



    /**
     * View all leave applications.
     */
    @GetMapping
    public String getAllLeaveApplications(Model model,
                                          @RequestParam(value = "status", required = false) LeaveStatus status) {

    	List<LeaveApplication> leaves;

    	if (status != null) {
    	    leaves = leaveApplicationService.getAllPendingApplications();
    	} else {
    	    leaves = leaveApplicationService.getAllLeaveApplications();
    	}
    	
    	// COMPUTATION SECTION II INJECTION
        for (LeaveApplication leave : leaves) {
            LeaveComputation computation = leaveApplicationService.computeLeaveComputation(leave);
            leave.setComputation(computation);
        }


        model.addAttribute("leaves", leaves);
        model.addAttribute("statusFilter", status);
        return "leave-list";
    }
    
    
    @GetMapping("/{id}/view")
    public void viewLeavePdf(@PathVariable Long id, HttpServletResponse response) throws Exception {
        LeaveApplication leave = leaveApplicationService.getLeaveById(id);

        if (leave.getStatus() != LeaveStatus.APPROVED) {
            throw new IllegalStateException("Only approved leave applications can be viewed.");
        }

        // Ensure Section II computation is done
        leave.setComputation(leaveApplicationService.computeLeaveComputation(leave));

        File pdfFile = pdfGenerationService.generateApprovedLeavePdf(leave);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "inline; filename=leave_form_" + id + ".pdf");
        Files.copy(pdfFile.toPath(), response.getOutputStream());
        response.getOutputStream().flush();
    }

    
    @GetMapping("/{id}/download")
    public void downloadApprovedLeaveForm(@PathVariable Long id, HttpServletResponse response) throws Exception {
        LeaveApplication leave = leaveApplicationService.getLeaveById(id);

        if (leave.getStatus() != LeaveStatus.APPROVED) {
            throw new IllegalStateException("Only approved leave applications can be downloaded.");
        }

        // Ensure Section II computation is done
        leave.setComputation(leaveApplicationService.computeLeaveComputation(leave));

        File pdfFile = pdfGenerationService.generateApprovedLeavePdf(leave);

        response.setContentType("application/pdf");
        response.setHeader("Content-Disposition", "attachment; filename=leave_form_" + id + ".pdf");
        Files.copy(pdfFile.toPath(), response.getOutputStream());
        response.getOutputStream().flush();
    }

    /**
     * Approve a leave application.
     */
    @PostMapping("/{id}/approve")
    public String approveLeaveApplication(@PathVariable("id") Long leaveId,
                                          RedirectAttributes redirectAttributes) {
        try {
            leaveApplicationService.updateLeaveStatus(leaveId, LeaveStatus.APPROVED);
            redirectAttributes.addFlashAttribute("success", "Leave approved successfully.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to approve leave: " + ex.getMessage());
        }
        return "redirect:/leaves";
    }

    /**
     * Reject a leave application.
     */
    @PostMapping("/{id}/reject")
    public String rejectLeaveApplication(@PathVariable("id") Long leaveId,
                                         RedirectAttributes redirectAttributes) {
        try {
            leaveApplicationService.updateLeaveStatus(leaveId, LeaveStatus.REJECTED);
            redirectAttributes.addFlashAttribute("success", "Leave rejected successfully.");
        } catch (Exception ex) {
            redirectAttributes.addFlashAttribute("error", "Failed to reject leave: " + ex.getMessage());
        }
        return "redirect:/leaves";
    }
    
    
}
