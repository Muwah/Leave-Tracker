package model;

import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Manager")
public class Manager {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String department;

    @OneToMany(mappedBy = "manager", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<LeaveApplication> leaveApplications;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getDepartment() {
		return department;
	}

	public void setDepartment(String department) {
		this.department = department;
	}

	public List<LeaveApplication> getLeaveApplications() {
		return leaveApplications;
	}

	public void setLeaveApplications(List<LeaveApplication> leaveApplications) {
		this.leaveApplications = leaveApplications;
	}

	@Override
	public String toString() {
		return "Manager [id=" + id + ", name=" + name + ", department=" + department + ", leaveApplications="
				+ leaveApplications + ", getId()=" + getId() + ", getName()=" + getName() + ", getDepartment()="
				+ getDepartment() + ", getLeaveApplications()=" + getLeaveApplications() + "]";
	}

    // Getters and Setters
    
    

}
