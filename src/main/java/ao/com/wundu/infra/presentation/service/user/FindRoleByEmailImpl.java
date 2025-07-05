package ao.com.wundu.infra.presentation.service.user;

import ao.com.wundu.core.enums.Role;
import ao.com.wundu.core.usecases.user.FindRoleByEmail;
import ao.com.wundu.infra.persistence.dtos.UserResponse;
import ao.com.wundu.infra.presentation.entities.User;
import ao.com.wundu.infra.presentation.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;

public class FindRoleByEmailImpl implements FindRoleByEmail {

    @Autowired
    private UserRepository userRepository;

    @Override
    public Role execute(String email) {
        return userRepository.findRoleByEmail(email);
    }
}
