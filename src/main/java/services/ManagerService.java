package services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import model.Manager;
import repository.ManagerRepository;

@Service
public class ManagerService {
	@Autowired
    private ManagerRepository managerRepository;

    public Manager saveManager(Manager manager) {
        return managerRepository.save(manager);
    }

    public List<Manager> getAllManagers() {
        return managerRepository.findAll();
    }

    public Manager getManagerById(Long id) {
        return managerRepository.findById(id).orElse(null);
    }

}
