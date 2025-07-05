package ao.com.wundu.core.usecases.user;

import ao.com.wundu.infra.persistence.dtos.UserResponse;
import ao.com.wundu.infra.presentation.entities.User;

import java.util.Optional;

public interface FindUserByEmail {

    User execute(String email);
}
