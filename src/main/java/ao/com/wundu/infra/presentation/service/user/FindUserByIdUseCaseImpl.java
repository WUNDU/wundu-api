package ao.com.wundu.infra.presentation.service.user;

import ao.com.wundu.core.exception.ResourceNotFoundException;
import ao.com.wundu.core.usecases.user.FindUserByIdUseCase;
import ao.com.wundu.infra.persistence.dtos.UserResponse;
import ao.com.wundu.infra.persistence.mappers.UserMapper;
import ao.com.wundu.infra.presentation.entities.User;
import ao.com.wundu.infra.presentation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class FindUserByIdUseCaseImpl implements FindUserByIdUseCase {

    @Autowired
    private UserRepository userRepository;

    @Override
    public UserResponse execute(String id) {

        User user = userRepository.findById(id)
                .orElseThrow( () -> new ResourceNotFoundException(String.format("Usuário com id=%s não encontrado", id)) );

        return UserMapper.toResponse(user);
    }
}
