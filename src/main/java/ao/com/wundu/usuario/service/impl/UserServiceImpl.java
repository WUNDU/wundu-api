package ao.com.wundu.usuario.service.impl;

import ao.com.wundu.exception.BusinessValidationException;
import ao.com.wundu.exception.ResourceNotFoundException;
import ao.com.wundu.usuario.dto.UserRequest;
import ao.com.wundu.usuario.dto.UserResponse;
import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.usuario.enums.PlanType;
import ao.com.wundu.usuario.enums.Role;
import ao.com.wundu.usuario.mapper.UserMapper;
import ao.com.wundu.usuario.repository.UserRepository;
import ao.com.wundu.usuario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.sql.Timestamp;
import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserResponse create(UserRequest request) {

        if (userRepository.existsByEmail(request.email())) {
            throw new BusinessValidationException("Já existe um usuário cadastrado com este e-mail");
        }

        if (userRepository.existsByPhoneNumber(request.phoneNumber())) {
            throw new BusinessValidationException("Já existe um usuário cadastrado com este telefone");
        }

        User user = userMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);
        return userMapper.toResponse(user);
    }

    @Override
    public Role findRoleByEmail(String email) {
        return userRepository.findRoleByEmail(email);
    }

    @Override
    public User findByEmail(String email) {
        return userRepository.findByEmail(email)
                .orElseThrow( () -> new ResourceNotFoundException(String.format("Usuário com Email=%s não encontrado", email)) );
    }

    @Override
    public UserResponse findById(String id) {
        User user = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(String.format("Usuário com id=%s não encontrado", id)) );

        return userMapper.toResponse(user);
    }

    @Override
    public Page<UserResponse> findAll(PlanType plan, Boolean isActive, Timestamp createdAt, Pageable pageable) {
        Page<User> users = userRepository.searchUsers(plan, isActive, createdAt, pageable);

        return users.map(userMapper::toResponse);
    }
}
