package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import exception.LeaveApplicationNotFoundException;
import model.LeaveApplication;
import model.LeaveStatus;
import repository.LeaveApplicationRepository;

@Service
public class LeaveApplicationService {
    @Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    /**
     * Applies for leave and sets the status to PENDING by default.
     *
     * @param leaveApplication The leave application to create.
     * @return The created leave application.
     */
    public LeaveApplication applyForLeave(LeaveApplication leaveApplication) {
        leaveApplication.setStatus(LeaveStatus.PENDING); // Ensure status is PENDING
        return leaveApplicationRepository.save(leaveApplication);
    }

    /**
     * Retrieves all leave applications for a specific employee.
     *
     * @param employeeId The ID of the employee.
     * @return A list of leave applications.
     */
    public List<LeaveApplication> getLeaveApplicationsByEmployee(Long employeeId) {
        return leaveApplicationRepository.findByEmployeeId(employeeId);
    }

    /**
     * Retrieves all leave applications for a specific manager.
     *
     * @param managerId The ID of the manager.
     * @return A list of leave applications.
     */
    public List<LeaveApplication> getLeaveApplicationsByManager(Long managerId) {
        return leaveApplicationRepository.findByManagerId(managerId);
    }

    /**
     * Updates the status of a leave application.
     *
     * @param leaveId The ID of the leave application to update.
     * @param status  The new status (APPROVED or REJECTED).
     * @return The updated leave application.
     * @throws LeaveApplicationNotFoundException If the leave application is not found.
     * @throws IllegalStateException            If the leave application is already in a final state.
     */
    public LeaveApplication updateLeaveStatus(Long leaveId, LeaveStatus status) {
        LeaveApplication leaveApplication = leaveApplicationRepository.findById(leaveId)
                .orElseThrow(() -> new LeaveApplicationNotFoundException("Leave application not found with id: " + leaveId));

        // Check if the leave application is already in a final state
        if (leaveApplication.getStatus() == LeaveStatus.APPROVED || leaveApplication.getStatus() == LeaveStatus.REJECTED) {
            throw new IllegalStateException("Leave application is already in a final state and cannot be modified.");
        }

        // Update the status
        leaveApplication.setStatus(status);
        return leaveApplicationRepository.save(leaveApplication);
    }
}