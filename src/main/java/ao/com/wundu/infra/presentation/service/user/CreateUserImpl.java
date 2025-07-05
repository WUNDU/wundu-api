package ao.com.wundu.infra.presentation.service.user;

import ao.com.wundu.core.usecases.user.CreateUserUseCase;
import ao.com.wundu.infra.persistence.dtos.UserRequest;
import ao.com.wundu.infra.persistence.dtos.UserResponse;
import ao.com.wundu.infra.persistence.mappers.UserMapper;
import ao.com.wundu.infra.presentation.entities.User;
import ao.com.wundu.infra.presentation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class CreateUserImpl implements CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Override
    public UserResponse execute(UserRequest request) {

        User user = UserMapper.toUser(request);

        user.setPassword(passwordEncoder.encode(user.getPassword()));

        user = userRepository.save(user);
        return UserMapper.toResponse(user);
    }
}
