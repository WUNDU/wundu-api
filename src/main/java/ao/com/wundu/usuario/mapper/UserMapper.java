package ao.com.wundu.usuario.mapper;

import ao.com.wundu.usuario.dto.UserRequest;
import ao.com.wundu.usuario.dto.UserResponse;
import ao.com.wundu.usuario.entity.User;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;


@Component
public class UserMapper {


    public User toUser(UserRequest request) {

        User user = new User();
        user.setName(request.name());
        user.setEmail(request.email());
        user.setPassword(request.password());
        user.setPhoneNumber(request.phoneNumber());

        return user;
    }

    public UserResponse toResponse(User user) {

        return new UserResponse(
                user.getId(),
                user.getName(),
                user.getEmail(),
                user.getPhoneNumber(),
                user.getRole().name(),
                user.getPlanType().name(),
                user.getCreatedAt(),
                user.getUpdatedAt(),
                user.getActive(),
                user.getPlanStart(),
                user.getPlanEnd(),
                user.getTrial()
        );
    }

    public List<UserResponse> toList(List<User> users) {

        return users.stream()
                .map( user -> toResponse(user) )
                .collect(Collectors.toList());
    }
}
