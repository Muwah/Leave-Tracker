package model;

import jakarta.persistence.*;

@Entity
@Table(name = "leave_policy")
public class LeavePolicy {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private LeaveType leaveType;

    @Enumerated(EnumType.STRING)
    private SalaryScale applicableToScale;

    private boolean appliesToFemaleOnly;
    private boolean appliesToMaleOnly;
    private boolean forContractStaff;

    private int maxAllowedDaysPerYear;  // e.g., 36, 30, 24, 60
    private boolean isAccruable;         // e.g., annual leave is; paternity is not
    private boolean isCarryForwardAllowed;
    private boolean requiresMedicalReport;
    private boolean requiresMinisterApproval; // for leave carry forward or special cases

    @Column(length = 1000)
    private String accrualRule; // e.g., "Granted on Jan 1 for full-year staff"
    
    @Column(length = 1000)
    private String carryForwardRule; // e.g., "Must be approved before Dec 15"
    
    @Column(length = 1000)
    private String specialCondition; // e.g., "Must be pregnant 36-38 weeks for maternity leave"

    // === Getters and Setters ===

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LeaveType getLeaveType() {
        return leaveType;
    }

    public void setLeaveType(LeaveType leaveType) {
        this.leaveType = leaveType;
    }

    public SalaryScale getApplicableToScale() {
        return applicableToScale;
    }

    public void setApplicableToScale(SalaryScale applicableToScale) {
        this.applicableToScale = applicableToScale;
    }

    public boolean isAppliesToFemaleOnly() {
        return appliesToFemaleOnly;
    }

    public void setAppliesToFemaleOnly(boolean appliesToFemaleOnly) {
        this.appliesToFemaleOnly = appliesToFemaleOnly;
    }

    public boolean isAppliesToMaleOnly() {
        return appliesToMaleOnly;
    }

    public void setAppliesToMaleOnly(boolean appliesToMaleOnly) {
        this.appliesToMaleOnly = appliesToMaleOnly;
    }

    public boolean isForContractStaff() {
        return forContractStaff;
    }

    public void setForContractStaff(boolean forContractStaff) {
        this.forContractStaff = forContractStaff;
    }

    public int getMaxAllowedDaysPerYear() {
        return maxAllowedDaysPerYear;
    }

    public void setMaxAllowedDaysPerYear(int maxAllowedDaysPerYear) {
        this.maxAllowedDaysPerYear = maxAllowedDaysPerYear;
    }

    public boolean isAccruable() {
        return isAccruable;
    }

    public void setAccruable(boolean isAccruable) {
        this.isAccruable = isAccruable;
    }

    public boolean isCarryForwardAllowed() {
        return isCarryForwardAllowed;
    }

    public void setCarryForwardAllowed(boolean isCarryForwardAllowed) {
        this.isCarryForwardAllowed = isCarryForwardAllowed;
    }

    public boolean isRequiresMedicalReport() {
        return requiresMedicalReport;
    }

    public void setRequiresMedicalReport(boolean requiresMedicalReport) {
        this.requiresMedicalReport = requiresMedicalReport;
    }

    public boolean isRequiresMinisterApproval() {
        return requiresMinisterApproval;
    }

    public void setRequiresMinisterApproval(boolean requiresMinisterApproval) {
        this.requiresMinisterApproval = requiresMinisterApproval;
    }

    public String getAccrualRule() {
        return accrualRule;
    }

    public void setAccrualRule(String accrualRule) {
        this.accrualRule = accrualRule;
    }

    public String getCarryForwardRule() {
        return carryForwardRule;
    }

    public void setCarryForwardRule(String carryForwardRule) {
        this.carryForwardRule = carryForwardRule;
    }

    public String getSpecialCondition() {
        return specialCondition;
    }

    public void setSpecialCondition(String specialCondition) {
        this.specialCondition = specialCondition;
    }
}
