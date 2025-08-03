package ao.com.wundu.usuario.service;

import ao.com.wundu.usuario.dto.UserRequest;
import ao.com.wundu.usuario.dto.UserResponse;
import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.usuario.enums.Role;

import java.util.List;

public interface UserService {

    UserResponse create(UserRequest request);
    Role findRoleByEmail(String email);
    User findByEmail(String email);
    UserResponse findById(String id);
    List<UserResponse> findAll();
}
