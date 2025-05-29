package model;
import jakarta.persistence.*;
import java.util.List;

@Entity
@Table(name = "Employee")
public class Employee {
	@Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
	

    private String name;
    private String department;
    private String designation;
    private String email;

    @OneToMany(mappedBy = "employee", cascade = CascadeType.ALL,fetch = FetchType.LAZY)
    private List<LeaveApplication> leaveApplications;
    
    @Enumerated(EnumType.STRING)
    private SalaryScale salaryScale;

    

	public SalaryScale getSalaryScale() {
		return salaryScale;
	}

	public void setSalaryScale(SalaryScale salaryScale) {
		this.salaryScale = salaryScale;
	}

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

	public String getDesignation() {
		return designation;
	}

	public void setDesignation(String designation) {
		this.designation = designation;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public List<LeaveApplication> getLeaveApplications() {
		return leaveApplications;
	}

	public void setLeaveApplications(List<LeaveApplication> leaveApplications) {
		this.leaveApplications = leaveApplications;
	}

	@Override
	public String toString() {
		return "Employee [id=" + id + ", name=" + name + ", department=" + department + ", designation=" + designation
				+ ", email=" + email + ", leaveApplications=" + leaveApplications + ", getId()=" + getId()
				+ ", getName()=" + getName() + ", getDepartment()=" + getDepartment() + ", getDesignation()="
				+ getDesignation() + ", getEmail()=" + getEmail() + ", getLeaveApplications()=" + getLeaveApplications()
				+ "]";
	}
    
    

}
