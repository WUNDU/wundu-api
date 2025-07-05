package ao.com.wundu.infra.presentation.service.user;

import ao.com.wundu.core.exception.ResourceNotFoundException;
import ao.com.wundu.core.usecases.user.FindRoleByEmail;
import ao.com.wundu.core.usecases.user.FindUserByEmail;
import ao.com.wundu.infra.persistence.dtos.UserResponse;
import ao.com.wundu.infra.persistence.mappers.UserMapper;
import ao.com.wundu.infra.presentation.entities.User;
import ao.com.wundu.infra.presentation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class FindUserByEmailImpl implements FindUserByEmail {

    @Autowired
    private UserRepository userRepository;

    @Override
    public User execute(String email) {

        return userRepository.findByEmail(email)
                .orElseThrow( () -> new ResourceNotFoundException(String.format("Usuário com Email=%s não encontrado", email)) );
    }
}
