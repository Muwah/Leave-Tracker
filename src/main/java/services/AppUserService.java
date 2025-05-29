package services;

import model.AppUser;
import repository.AppUserRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AppUserService {

    @Autowired
    private AppUserRepository appUserRepository;

    public void assignRoleToUser(Long userId, String newRole) {
        AppUser user = appUserRepository.findById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found"));

        user.setRole(newRole.toUpperCase()); // Ensure role is uppercase
        appUserRepository.save(user);
    }
}
