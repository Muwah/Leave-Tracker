package model;
import jakarta.persistence.*;

@Entity
public class AppUser {
	@Id 
	@GeneratedValue
    private Long id;
	
	@Column(unique = true)
    private String username;
    private String password;
    private String role; // e.g., EMPLOYEE, ADMIN

    @OneToOne
    private Employee employee;

	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public Employee getEmployee() {
		return employee;
	}

	public void setEmployee(Employee employee) {
		this.employee = employee;
	}

    // Getters and Setters
    

}
