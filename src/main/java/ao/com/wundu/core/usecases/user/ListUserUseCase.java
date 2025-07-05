package ao.com.wundu.core.usecases.user;

import ao.com.wundu.infra.persistence.dtos.UserResponse;

import java.util.List;

public interface ListUserUseCase {

    List<UserResponse> execute();
}
