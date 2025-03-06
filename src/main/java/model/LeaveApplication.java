package model;

//import java.util.Date;

import jakarta.persistence.*;
//import java.util.List;

@Entity
@Table(name = "LeaveApplication")
public class LeaveApplication {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String leaveType;
    
    @Temporal(TemporalType.DATE)
    private String startDate;
    
    @Temporal(TemporalType.DATE)
    private String endDate;
    private String reason;
    @Enumerated(EnumType.STRING)
    private LeaveStatus status= LeaveStatus.PENDING; // e.g., PENDING, APPROVED, REJECTED

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getLeaveType() {
		return leaveType;
	}

	public void setLeaveType(String leaveType) {
		this.leaveType = leaveType;
	}

	

	

	public String getStartDate() {
		return startDate;
	}

	public void setStartDate(String startDate) {
		this.startDate = startDate;
	}

	public String getEndDate() {
		return endDate;
	}

	public void setEndDate(String endDate) {
		this.endDate = endDate;
	}

	public String getReason() {
		return reason;
	}

	public void setReason(String reason) {
		this.reason = reason;
	}

	

	public LeaveStatus getStatus() {
		return status;
	}

	public void setStatus(LeaveStatus string) {
		this.status = string;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

	public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

    // Getters and Setters

    
    
}
