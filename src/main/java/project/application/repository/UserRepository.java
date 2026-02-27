package project.application.repository;

import org.apache.catalina.User;
import org.springframework.data.jpa.repository.JpaRepository;
import project.application.entities.Users;

import java.util.Optional;

public interface UserRepository extends JpaRepository<Users, Long> {
    Optional<Users> findByName(String name);
}
