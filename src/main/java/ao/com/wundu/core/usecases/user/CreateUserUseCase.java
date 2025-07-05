package ao.com.wundu.core.usecases.user;

import ao.com.wundu.infra.persistence.dtos.UserRequest;
import ao.com.wundu.infra.persistence.dtos.UserResponse;

public interface CreateUserUseCase {

    UserResponse execute(UserRequest request);
}
