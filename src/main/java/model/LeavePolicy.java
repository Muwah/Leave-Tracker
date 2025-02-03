package model;

import jakarta.persistence.*;
//import java.util.List;

@Entity
@Table(name = "LeavePolicy")
public class LeavePolicy {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String leaveType;
    private int maxAllowedLeaves;
    private String accrualRules;
    private String carryForwardRules;
    
 // Getters and Setters
    
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
	public int getMaxAllowedLeaves() {
		return maxAllowedLeaves;
	}
	public void setMaxAllowedLeaves(int maxAllowedLeaves) {
		this.maxAllowedLeaves = maxAllowedLeaves;
	}
	public String getAccrualRules() {
		return accrualRules;
	}
	public void setAccrualRules(String accrualRules) {
		this.accrualRules = accrualRules;
	}
	public String getCarryForwardRules() {
		return carryForwardRules;
	}
	public void setCarryForwardRules(String carryForwardRules) {
		this.carryForwardRules = carryForwardRules;
	}

    
    

}
