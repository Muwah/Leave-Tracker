package repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import model.Manager;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
}