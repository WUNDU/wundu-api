package ao.com.wundu.infra.presentation.service.user;

import ao.com.wundu.core.usecases.user.ListUserUseCase;
import ao.com.wundu.infra.persistence.dtos.UserResponse;
import ao.com.wundu.infra.persistence.mappers.UserMapper;
import ao.com.wundu.infra.presentation.entities.User;
import ao.com.wundu.infra.presentation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ListUserImpl implements ListUserUseCase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public List<UserResponse> execute() {

        List<User> users = userRepository.findAll();

        return UserMapper.toList(users);

    }
}
