package ao.com.wundu.infra.jwt;

import ao.com.wundu.core.enums.Role;
import ao.com.wundu.core.usecases.user.FindRoleByEmail;
import ao.com.wundu.core.usecases.user.FindUserByEmail;
import ao.com.wundu.infra.presentation.entities.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    private FindUserByEmail findUserByEmail;
    private FindRoleByEmail findRoleByEmail;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = findUserByEmail.execute(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String email) {
        Role role = findRoleByEmail.execute(email);
        return JwtUtils.createToken(email, role.name().substring("Role_".length()));
    }
}
