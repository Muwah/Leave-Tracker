package repository;

import model.LeaveApplication;
import model.LeaveStatus;
import model.LeaveType;
import model.SalaryScale;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.stereotype.Repository;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface LeaveApplicationRepository extends JpaRepository<LeaveApplication, Long> {

    // Find leave applications by employee ID
    @Query("SELECT la FROM LeaveApplication la JOIN FETCH la.employee e WHERE (:employeeId IS NULL OR e.id = :employeeId)")
    List<LeaveApplication> findByEmployeeId(@Param("employeeId") Long employeeId);

    // Find leave applications by manager ID
    @Query("SELECT la FROM LeaveApplication la JOIN FETCH la.manager m WHERE m.id = :managerId")
    List<LeaveApplication> findByManagerId(@Param("managerId") Long managerId);

    // Find leave applications routed to a specific TO officer
    @Query("SELECT la FROM LeaveApplication la JOIN FETCH la.toOfficer t WHERE t.id = :toOfficerId")
    List<LeaveApplication> findByToOfficerId(@Param("toOfficerId") Long toOfficerId);

    // Find leave applications routed through a specific THRU officer (either slot)
    @Query("SELECT la FROM LeaveApplication la WHERE la.thruOfficer1.id = :officerId OR la.thruOfficer2.id = :officerId")
    List<LeaveApplication> findByThruOfficer(@Param("officerId") Long officerId);

    // Find all pending leave applications
    List<LeaveApplication> findByStatus(LeaveStatus status);  // Cleaner alternative to custom query

    // Find all leave applications for a given leave type
    List<LeaveApplication> findByLeaveType(LeaveType leaveType);

    // Find leave applications by salary scale (entitlement filter)
    List<LeaveApplication> findBySalaryScale(SalaryScale salaryScale);

    // Find leave applications within a given date range (e.g., for reporting or conflict check)
    @Query("SELECT la FROM LeaveApplication la WHERE la.startDate <= :end AND la.endDate >= :start")
    List<LeaveApplication> findOverlappingLeave(@Param("start") LocalDate start, @Param("end") LocalDate end);
    
    @Query("SELECT COALESCE(SUM(la.daysApplied), 0) " +
    	       "FROM LeaveApplication la " +
    	       "WHERE la.employee.id = :employeeId " +
    	       "AND la.leaveType = :leaveType " +
    	       "AND la.status = 'APPROVED' " +
    	       "AND EXTRACT(YEAR FROM la.startDate) = :year")
    	int sumDaysTakenThisYear(@Param("employeeId") Long employeeId,
    	                         @Param("leaveType") LeaveType leaveType,
    	                         @Param("year") int year);
    
    @Query("SELECT l FROM LeaveApplication l WHERE l.employee.id = :employeeId AND " +
    	       "l.status != 'REJECTED' AND " +
    	       "l.startDate <= :endDate AND l.endDate >= :startDate")
    	List<LeaveApplication> findOverlappingLeaveForEmployee(@Param("employeeId") Long employeeId,
    	                                                       @Param("startDate") LocalDate startDate,
    	                                                       @Param("endDate") LocalDate endDate);


}
