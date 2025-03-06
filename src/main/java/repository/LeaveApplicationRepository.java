package repository;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.LeaveApplication;

import java.util.List;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {
	@Query("SELECT la FROM LeaveApplication la JOIN FETCH la.employee WHERE (:employeeId IS NULL OR la.employee.id = :employeeId)")
    List<LeaveApplication> findByEmployeeId(@Param("employeeId") Long employeeId);

    @Query("SELECT la FROM LeaveApplication la JOIN FETCH la.employee WHERE la.manager.id = :managerId")
    List<LeaveApplication> findByManagerId(@Param("managerId") Long managerId);
}