package com.police.openam;

import com.police.entities.User;
import com.police.services.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.provisioning.UserDetailsManager;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Component
public class OpenAmAuthProvider implements AuthenticationProvider {

    private UserService userService;

    @Autowired
    public OpenAmAuthProvider(UserService us){
        this.userService = us;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
       if (!userService.userExists((String) authentication.getPrincipal())) {
            User user = new User();
            user.setUsername((String) authentication.getPrincipal());
            userService.setRole(user, "USER");
            userService.save(user);
        }
        User user = userService.getUserByUsername((String) authentication.getPrincipal());
        OpenAmAuthToken token = new OpenAmAuthToken(user.getUsername(),
                Arrays.asList(new SimpleGrantedAuthority("ROLE_"+user.getRole().getName())));
        token.setAuthenticated(true);
        token.setDetails(user.getUsername());
        return token;
}

    @Override
    public boolean supports(Class<?> authentication) {
        return OpenAmAuthToken.class.isAssignableFrom(authentication);
    }

}
