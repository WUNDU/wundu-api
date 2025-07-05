package ao.com.wundu.infra.presentation.service.user;

import ao.com.wundu.core.usecases.user.CreateUserUseCase;
import ao.com.wundu.infra.persistence.dtos.UserRequest;
import ao.com.wundu.infra.persistence.dtos.UserResponse;
import ao.com.wundu.infra.persistence.mappers.UserMapper;
import ao.com.wundu.infra.presentation.entities.User;
import ao.com.wundu.infra.presentation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserServiceImpl implements CreateUserUseCase {

    @Autowired
    private UserRepository userRepository;


    @Override
    public UserResponse execute(UserRequest request) {

        User user = UserMapper.toUser(request);

        user = userRepository.save(user);
        return UserMapper.toResponse(user);
    }
}
