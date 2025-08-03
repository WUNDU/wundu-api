package ao.com.wundu.usuario.repository;

import ao.com.wundu.usuario.enums.Role;
import ao.com.wundu.usuario.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u.role FROM User u WHERE u.email = :email")
    Role findRoleByEmail(String email);
}
