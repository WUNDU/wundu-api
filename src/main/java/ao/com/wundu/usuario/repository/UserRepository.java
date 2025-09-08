package ao.com.wundu.usuario.repository;

import ao.com.wundu.usuario.enums.PlanType;
import ao.com.wundu.usuario.enums.Role;
import ao.com.wundu.usuario.entity.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.sql.Timestamp;
import java.util.Optional;

public interface UserRepository extends JpaRepository<User, String> {

    Optional<User> findByEmail(String email);

    @Query("SELECT u.role FROM User u WHERE u.email = :email")
    Role findRoleByEmail(String email);

    boolean existsByEmail(String email);
    boolean existsByPhoneNumber(String phoneNumber);

    @Query("""
            SELECT u from User u
            WHERE (:plan IS NULL OR u.planType = :plan)
            AND (:isActive IS NULL OR u.isActive = :isActive)
            AND (:createdAt IS NULL OR u.createdAt >= :createdAt)
            """)
    Page<User> searchUsers(
            @Param("plan") PlanType plan,
            @Param("isActive") Boolean isActive,
            @Param("createdAt") Timestamp createdAt,
            Pageable pageable
    );
}
