package repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import model.AppUser;
import org.springframework.stereotype.Repository;


@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Long>{
	Optional<AppUser> findByUsername(String username);

}
