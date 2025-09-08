package ao.com.wundu.usuario.service;

import ao.com.wundu.usuario.dto.UserRequest;
import ao.com.wundu.usuario.dto.UserResponse;
import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.usuario.enums.PlanType;
import ao.com.wundu.usuario.enums.Role;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.sql.Timestamp;
import java.util.List;

public interface UserService {

    UserResponse create(UserRequest request);
    Role findRoleByEmail(String email);
    User findByEmail(String email);
    UserResponse findById(String id);
    Page<UserResponse> findAll(PlanType plan, Boolean isActive, Timestamp createdAt, Pageable pageable);
}
