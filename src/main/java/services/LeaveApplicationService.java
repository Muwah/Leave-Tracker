package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.LeaveApplication;
import model.LeaveStatus;
import repository.LeaveApplicationRepository;

@Service
public class LeaveApplicationService {
	@Autowired
    private LeaveApplicationRepository leaveApplicationRepository;

    public LeaveApplication applyForLeave(LeaveApplication leaveApplication) {
    	leaveApplication.setStatus(LeaveStatus.PENDING);
        return leaveApplicationRepository.save(leaveApplication);
    }

    public List<LeaveApplication> getLeaveApplicationsByEmployee(Long employeeId) {
        return leaveApplicationRepository.findByEmployeeId(employeeId);
    }

    public List<LeaveApplication> getLeaveApplicationsByManager(Long managerId) {
        return leaveApplicationRepository.findByManagerId(managerId);
    }

    public LeaveApplication updateLeaveStatus(Long leaveId, LeaveStatus status) {
        LeaveApplication leaveApplication = leaveApplicationRepository.findById(leaveId).orElse(null);
        if (leaveApplication != null) {
            leaveApplication.setStatus(status);
            return leaveApplicationRepository.save(leaveApplication);
        }
        return null;
    }

}
