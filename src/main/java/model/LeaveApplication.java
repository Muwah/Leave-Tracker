package model;

import jakarta.persistence.*;
import java.time.LocalDate;

import DTO.LeaveComputation;

@Entity
@Table(name = "leave_application")
public class LeaveApplication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    // Dropdowns from form
    @ManyToOne
    @JoinColumn(name = "to_officer_id")
    private Employee toOfficer;

    @ManyToOne
    @JoinColumn(name = "thru1_officer_id")
    private Employee thruOfficer1;

    @ManyToOne
    @JoinColumn(name = "thru2_officer_id")
    private Employee thruOfficer2;

    // Applicant
    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @ManyToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    // Updated leave type (enum)
    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    // Salary scale of the applicant at the time of application
    @Enumerated(EnumType.STRING)
    private SalaryScale salaryScale;

    private int daysApplied;

    private LocalDate startDate;

    private LocalDate endDate;

    @Column(length = 1000)
    private String reason;

    @Enumerated(EnumType.STRING)
    private LeaveStatus status = LeaveStatus.PENDING;

    @Column(length = 500)
    private String contactInfo;

    private String signature;

    private LocalDate applicationDate;
    
    @Transient
    private LeaveComputation computation;
    
 // === GETTERS & SETTERS ===


    public LeaveComputation getComputation() { return computation; }
    public void setComputation(LeaveComputation computation) { this.computation = computation; }


    
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Employee getToOfficer() {
        return toOfficer;
    }

    public void setToOfficer(Employee toOfficer) {
        this.toOfficer = toOfficer;
    }

    public Employee getThruOfficer1() {
        return thruOfficer1;
    }

    public void setThruOfficer1(Employee thruOfficer1) {
        this.thruOfficer1 = thruOfficer1;
    }

    public Employee getThruOfficer2() {
        return thruOfficer2;
    }

    public void setThruOfficer2(Employee thruOfficer2) {
        this.thruOfficer2 = thruOfficer2;
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

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public SalaryScale getSalaryScale() {
        return salaryScale;
    }

    public void setSalaryScale(SalaryScale salaryScale) {
        this.salaryScale = salaryScale;
    }

    public int getDaysApplied() {
        return daysApplied;
    }

    public void setDaysApplied(int daysApplied) {
        this.daysApplied = daysApplied;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
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

    public void setStatus(LeaveStatus status) {
        this.status = status;
    }

    public String getContactInfo() {
        return contactInfo;
    }

    public void setContactInfo(String contactInfo) {
        this.contactInfo = contactInfo;
    }

    public String getSignature() {
        return signature;
    }

    public void setSignature(String signature) {
        this.signature = signature;
    }

    public LocalDate getApplicationDate() {
        return applicationDate;
    }

    public void setApplicationDate(LocalDate applicationDate) {
        this.applicationDate = applicationDate;
    }
}
