package ao.com.wundu.core.usecases.user;

import ao.com.wundu.infra.persistence.dtos.UserResponse;

public interface FindUserByIdUseCase {

    UserResponse execute(String id);
}
