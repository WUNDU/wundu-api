package ao.com.wundu.core.usecases.user;

import ao.com.wundu.core.enums.Role;
import ao.com.wundu.infra.persistence.dtos.UserResponse;

public interface FindRoleByEmail {

    Role execute(String email);
}
