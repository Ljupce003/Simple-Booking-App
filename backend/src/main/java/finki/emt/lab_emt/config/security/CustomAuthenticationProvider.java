package finki.emt.lab_emt.config.security;

import finki.emt.lab_emt.model.exceptions.InvalidArgumentsException;
import finki.emt.lab_emt.model.exceptions.InvalidUsernameOrPasswordException;
import finki.emt.lab_emt.service.domain.UserService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {

    private final PasswordEncoder passwordEncoder;
    private final UserService userService;

    public CustomAuthenticationProvider(PasswordEncoder passwordEncoder, UserService userService) {
        this.passwordEncoder = passwordEncoder;
        this.userService = userService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {

        String username = authentication.getName();
        String password = authentication.getCredentials().toString();

        if(username.isEmpty() || password.isEmpty()){
            throw new InvalidArgumentsException();
        }

        UserDetails userDetails = this.userService.loadUserByUsername(username);

        if(!this.passwordEncoder.matches(password,userDetails.getPassword())){
            throw new InvalidUsernameOrPasswordException();
        }

        return new UsernamePasswordAuthenticationToken(userDetails,userDetails.getPassword(),userDetails.getAuthorities());

    }

    @Override
    public boolean supports(Class<?> aClass) {
        return aClass.equals(UsernamePasswordAuthenticationToken.class);
    }
}
