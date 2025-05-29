package config;

import jakarta.annotation.PostConstruct;
import model.LeavePolicy;
import model.LeaveType;
import model.SalaryScale;
import repository.LeavePolicyRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
public class DataInitializer {

    @Autowired
    private LeavePolicyRepository leavePolicyRepository;

    @PostConstruct
    public void init() {
        for (LeaveType type : LeaveType.values()) {
            for (SalaryScale scale : SalaryScale.values()) {

                Optional<LeavePolicy> existingPolicy = leavePolicyRepository
                        .findByLeaveTypeAndApplicableToScale(type, scale);

                if (existingPolicy.isEmpty()) {
                    LeavePolicy policy = new LeavePolicy();
                    policy.setLeaveType(type);
                    policy.setApplicableToScale(scale);
                    policy.setMaxAllowedDaysPerYear(getDefaultDays(type));
                    policy.setAccruable(type == LeaveType.ANNUAL);
                    policy.setCarryForwardAllowed(type == LeaveType.ANNUAL);
                    policy.setRequiresMedicalReport(type == LeaveType.SICK);
                    policy.setRequiresMinisterApproval(type == LeaveType.SPECIAL);
                    policy.setAppliesToFemaleOnly(type == LeaveType.MATERNITY);
                    policy.setAppliesToMaleOnly(type == LeaveType.PATERNITY);
                    policy.setForContractStaff(scale == SalaryScale.CONTRACT);
                    policy.setAccrualRule(getAccrualRule(type));
                    policy.setCarryForwardRule("Must be approved before Dec 15.");
                    policy.setSpecialCondition(getSpecialCondition(type));

                    leavePolicyRepository.save(policy);
                }
            }
        }
    }

    private int getDefaultDays(LeaveType type) {
        return switch (type) {
            case ANNUAL -> 30;
            case SICK -> 90;
            case MATERNITY -> 60;
            case PATERNITY -> 4;
            case SPECIAL -> 30;
            case STUDY -> 60;
            case COMPASSIONATE -> 7;
            case LEAVE_WITHOUT_PAY -> 90;
        };
    }

    private String getAccrualRule(LeaveType type) {
        return switch (type) {
            case ANNUAL -> "Granted monthly (2.5 days)";
            case SICK -> "Granted with a medical certificate";
            case MATERNITY -> "Granted once every 2 years";
            case PATERNITY -> "Granted on proof of childbirth";
            case STUDY -> "Granted for approved academic programs";
            case COMPASSIONATE -> "Granted for close family bereavement";
            case SPECIAL -> "Granted upon official approval";
            case LEAVE_WITHOUT_PAY -> "Granted for approved unpaid time off";
        };
    }

    private String getSpecialCondition(LeaveType type) {
        return switch (type) {
            case MATERNITY -> "Must be female and expectant (36+ weeks)";
            case PATERNITY -> "Granted to married male staff only";
            case COMPASSIONATE -> "Applies to loss of close family member";
            case STUDY -> "Must attach admission or sponsorship letter";
            case SPECIAL -> "Justification and ministerial approval required";
            case LEAVE_WITHOUT_PAY -> "Must be justified and approved";
            case ANNUAL, SICK -> "";
        };
    }
}
