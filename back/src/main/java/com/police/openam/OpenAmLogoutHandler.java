package com.police.openam;

import com.police.configs.openam.OpenAmProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Optional;

@Component
public class OpenAmLogoutHandler implements LogoutSuccessHandler {
    @Autowired
    private OpenAmProperties properties;

    @Override
    public void onLogoutSuccess(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Authentication authentication) throws IOException, ServletException {
        Optional<Cookie> iPDPCookie = Arrays.stream(httpServletRequest.getCookies())
                .filter(a -> a.getName().equals("iPlanetDirectoryPro")).findFirst();
        if (iPDPCookie.isPresent()) {
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("iPlanetDirectoryPro", iPDPCookie.get().getValue());
            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Accept-API-Version", "resource=2.1");

            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            try {
                restTemplate.exchange(properties.getLogout(), HttpMethod.POST, httpEntity, String.class);
            }
            catch (HttpClientErrorException e){
                if (!(e.getStatusCode() == HttpStatus.UNAUTHORIZED))
                    httpServletResponse.setStatus(e.getStatusCode().value());


            }
        }
        httpServletResponse.sendRedirect(properties.getHome());
    }
}
