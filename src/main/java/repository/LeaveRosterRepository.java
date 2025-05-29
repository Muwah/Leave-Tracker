package repository;

import model.Employee;
import model.LeaveRoster;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRosterRepository extends JpaRepository<LeaveRoster, Long> {
    List<LeaveRoster> findByEmployee(Employee employee);
}
