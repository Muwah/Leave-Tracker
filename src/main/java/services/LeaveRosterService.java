package services;

import model.Employee;
import model.LeaveRoster;
import repository.LeaveRosterRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
public class LeaveRosterService {

    @Autowired
    private LeaveRosterRepository leaveRosterRepository;

    public void saveRoster(Employee employee, List<LocalDate> leaveDates, String leaveType) {
        for (LocalDate date : leaveDates) {
            if (date.getDayOfWeek().getValue() < 6) { // Exclude Saturday(6) and Sunday(7)
                LeaveRoster roster = new LeaveRoster();
                roster.setEmployee(employee);
                roster.setLeaveDate(date);
                roster.setLeaveType(leaveType);
                roster.setConfirmed(false); // Draft by default
                leaveRosterRepository.save(roster);
            }
        }
    }

    public List<LeaveRoster> getAllRosters() {
        return leaveRosterRepository.findAll();
    }

    public List<LeaveRoster> getRosterForEmployee(Employee employee) {
        return leaveRosterRepository.findByEmployee(employee);
    }
}
