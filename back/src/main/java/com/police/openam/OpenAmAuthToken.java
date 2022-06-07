package com.police.openam;

import org.springframework.security.authentication.AbstractAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;

import java.util.Collection;

public class OpenAmAuthToken extends AbstractAuthenticationToken {
    private String username;

    public OpenAmAuthToken(String username){
        super(null);
        this.username = username;
    }

    public OpenAmAuthToken(String username, Collection<? extends GrantedAuthority> authorities){

        super(authorities);
        this.username = username;

    }

    @Override
    public Object getCredentials() {
        return "";
    }

    @Override
    public Object getPrincipal() {
        return username;
    }
}
