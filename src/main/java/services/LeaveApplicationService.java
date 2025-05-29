package services;

import exception.LeaveApplicationNotFoundException;
import model.*;
import repository.LeaveApplicationRepository;
import repository.LeavePolicyRepository;

//import org.hibernate.mapping.Map;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import DTO.LeaveComputation;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@Service
public class LeaveApplicationService {

    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    @Autowired
    private LeavePolicyRepository leavePolicyRepository;
    
    

    /**
     * Apply for leave. Validates against policy rules.
     */
    public LeaveApplication applyForLeave(LeaveApplication leaveApplication) {
        validateLeaveApplication(leaveApplication);

        leaveApplication.setStatus(LeaveStatus.PENDING);
        return leaveApplicationRepository.save(leaveApplication);
    }

    /**
     * Validate leave application against policy rules.
     */
    private void validateLeaveApplication(LeaveApplication application) {
        if (application.getStartDate().isAfter(application.getEndDate())) {
            throw new IllegalArgumentException("Start date cannot be after end date.");
        }

        // Check overlapping leave
        List<LeaveApplication> overlaps = leaveApplicationRepository
        	    .findOverlappingLeaveForEmployee(
        	        application.getEmployee().getId(),
        	        application.getStartDate(),
        	        application.getEndDate()
        	    );

        	if (!overlaps.isEmpty()) {
        	    throw new IllegalStateException("This leave overlaps with an existing application.");
        	}


        // Check policy constraints
        Optional<LeavePolicy> optionalPolicy = leavePolicyRepository
                .findByLeaveTypeAndApplicableToScale(application.getLeaveType(), application.getEmployee().getSalaryScale());
        System.out.println("Leave Type: " + application.getLeaveType());
        System.out.println("Salary Scale: " + application.getEmployee().getSalaryScale());


        if (optionalPolicy.isEmpty()) {
            optionalPolicy = leavePolicyRepository
                .findByLeaveTypeWithFallback(application.getLeaveType(), application.getEmployee().getSalaryScale());
        }

        LeavePolicy policy = optionalPolicy.orElseThrow(() ->
                new IllegalArgumentException("No leave policy defined for this type and salary scale."));


        if (application.getDaysApplied() > policy.getMaxAllowedDaysPerYear()) {
            throw new IllegalArgumentException("Requested leave days exceed the maximum allowed: " + policy.getMaxAllowedDaysPerYear());
        }

        if (policy.isRequiresMedicalReport() && application.getLeaveType() == LeaveType.SICK
                && (application.getReason() == null || application.getReason().isBlank())) {
            throw new IllegalArgumentException("Medical reason required for sick leave.");
        }

        // Future: Add checks for gender-specific leave like maternity/paternity
    }

    public List<LeaveApplication> getLeaveApplicationsByEmployee(Long employeeId) {
        return leaveApplicationRepository.findByEmployeeId(employeeId);
    }

    public List<LeaveApplication> getLeaveApplicationsByManager(Long managerId) {
        return leaveApplicationRepository.findByManagerId(managerId);
    }

    public List<LeaveApplication> getLeaveApplicationsByToOfficer(Long officerId) {
        return leaveApplicationRepository.findByToOfficerId(officerId);
    }

    public List<LeaveApplication> getLeaveApplicationsByThruOfficer(Long officerId) {
        return leaveApplicationRepository.findByThruOfficer(officerId);
    }

    public List<LeaveApplication> getAllPendingApplications() {
        return leaveApplicationRepository.findByStatus(LeaveStatus.PENDING);
    }
    public List<LeaveApplication> getAllLeaveApplications() {
        return leaveApplicationRepository.findAll(); // No filter
    }

    public LeaveApplication updateLeaveStatus(Long leaveId, LeaveStatus newStatus) {
        LeaveApplication leaveApplication = leaveApplicationRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveApplicationNotFoundException("Leave application not found with id: " + leaveId));

        if (leaveApplication.getStatus() == LeaveStatus.APPROVED || leaveApplication.getStatus() == LeaveStatus.REJECTED) {
            throw new IllegalStateException("Leave application is already finalized.");
        }

        leaveApplication.setStatus(newStatus);
        return leaveApplicationRepository.save(leaveApplication);
    }
    
    public LeaveComputation computeLeaveComputation(LeaveApplication leaveApplication) {
        Optional<LeavePolicy> optionalPolicy = leavePolicyRepository
                .findByLeaveTypeAndApplicableToScale(
                        leaveApplication.getLeaveType(),
                        leaveApplication.getEmployee().getSalaryScale());

        if (optionalPolicy.isEmpty()) {
            optionalPolicy = leavePolicyRepository.findByLeaveTypeWithFallback(
                    leaveApplication.getLeaveType(),
                    leaveApplication.getEmployee().getSalaryScale());
        }

        int due = optionalPolicy.map(LeavePolicy::getMaxAllowedDaysPerYear).orElse(0);

        int currentYear = LocalDate.now().getYear();
        int taken = leaveApplicationRepository.sumDaysTakenThisYear(
                leaveApplication.getEmployee().getId(),
                leaveApplication.getLeaveType(),
                currentYear
        );

        return new LeaveComputation(due, taken);
    }


    public Map<LeaveType, Integer> getRemainingLeaveDays(Employee employee) {
        Map<LeaveType, Integer> remaining = new HashMap<>();

        for (LeaveType type : LeaveType.values()) {
            Optional<LeavePolicy> policyOpt = leavePolicyRepository.findByLeaveTypeAndApplicableToScale(type, employee.getSalaryScale());

            // fallback to any generic policy if specific one not found
            if (policyOpt.isEmpty()) {
            	policyOpt = leavePolicyRepository.findFirstByLeaveType(type);

            }

            int due = policyOpt.map(LeavePolicy::getMaxAllowedDaysPerYear).orElse(0);

            int taken = leaveApplicationRepository.sumDaysTakenThisYear(
                    employee.getId(),
                    type,
                    LocalDate.now().getYear()
            );

            remaining.put(type, Math.max(due - taken, 0));
        }

        return remaining;
    }
    
    public LeaveApplication getLeaveById(Long id) {
        return leaveApplicationRepository.findById(id)
                .orElseThrow(() -> new LeaveApplicationNotFoundException("Leave application not found with ID: " + id));
    }



}
