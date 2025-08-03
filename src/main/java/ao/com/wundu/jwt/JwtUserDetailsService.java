package ao.com.wundu.jwt;

import ao.com.wundu.usuario.enums.Role;
import ao.com.wundu.usuario.entity.User;
import ao.com.wundu.usuario.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class JwtUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User user = userService.findByEmail(username);
        return new JwtUserDetails(user);
    }

    public JwtToken getTokenAuthenticated(String email) {
        Role role = userService.findRoleByEmail(email);
        return JwtUtils.createToken(email, role.name().substring("Role_".length()));
    }
}
