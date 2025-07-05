package ao.com.wundu.infra.jwt;


import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;

public class JwtUserDetails extends User {

    private ao.com.wundu.infra.presentation.entities.User user;

    public JwtUserDetails(ao.com.wundu.infra.presentation.entities.User user) {
        super(user.getEmail(), user.getPassword(), AuthorityUtils.createAuthorityList(user.getRole().name()));
        this.user = user;
    }

    public String getUser() {
        return this.user.getId();
    }

    public String getRole() {
        return this.user.getRole().name();
    }
}
