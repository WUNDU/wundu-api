package ao.com.wundu.infra.persistence.mappers;

import ao.com.wundu.infra.persistence.dtos.UserRequest;
import ao.com.wundu.infra.persistence.dtos.UserResponse;
import ao.com.wundu.infra.presentation.entities.User;

import java.util.List;
import java.util.stream.Collectors;

public class UserMapper {


    public static User toUser(UserRequest request) {

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setPhoneNumber(request.phoneNumber());

        return user;
    }

    public static UserResponse toResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().name(),
                user.getActive()
        );
    }

    public static List<UserResponse> toList(List<User> users) {

        return users.stream()
                .map( user -> toResponse(user) )
                .collect(Collectors.toList());
    }
}
