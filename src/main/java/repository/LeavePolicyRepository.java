package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import model.LeavePolicy;
import model.LeaveType;
import model.SalaryScale;

@Repository
public interface LeavePolicyRepository extends JpaRepository<LeavePolicy, Long> {

    // Primary method: strict match on both type and scale
    Optional<LeavePolicy> findByLeaveTypeAndApplicableToScale(LeaveType leaveType, SalaryScale salaryScale);

    // Fallback to global or default policy (scale can be null)
    @Query("SELECT l FROM LeavePolicy l WHERE l.leaveType = :type AND (l.applicableToScale = :scale OR l.applicableToScale IS NULL)")
    Optional<LeavePolicy> findByLeaveTypeWithFallback(@Param("type") LeaveType type, @Param("scale") SalaryScale scale);
    Optional<LeavePolicy> findFirstByLeaveType(LeaveType leaveType);
}
