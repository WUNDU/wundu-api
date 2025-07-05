package ao.com.wundu.infra.presentation.repositories;

import ao.com.wundu.core.enums.Role;
import ao.com.wundu.infra.presentation.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u.role FROM User u WHERE u.email = :email")
    Role findRoleByEmail(String email);
}
