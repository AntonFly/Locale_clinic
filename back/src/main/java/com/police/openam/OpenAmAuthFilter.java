package com.police.openam;

import com.police.configs.openam.OpenAmProperties;
import lombok.Data;
import org.codehaus.jackson.annotate.JsonProperty;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.savedrequest.HttpSessionRequestCache;
import org.springframework.security.web.savedrequest.SavedRequest;
import org.springframework.stereotype.Component;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import javax.annotation.PostConstruct;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;
import java.util.Map;
import java.util.Optional;

@Component
public class OpenAmAuthFilter extends AbstractAuthenticationProcessingFilter {

    @Autowired
    private OpenAmProperties properties;

    @Override
    @Autowired
    public void setAuthenticationManager(AuthenticationManager authenticationManager) {
        super.setAuthenticationManager(authenticationManager);
    }

    @Autowired
    OpenAmAuthFilter() {
        super("/login");
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws AuthenticationException, IOException, ServletException {
        httpServletResponse.setHeader("Access-Content-Allow-Origin", "*");
        SavedRequest savedRequest = new HttpSessionRequestCache().getRequest(httpServletRequest, httpServletResponse);
        String redirectUrl = savedRequest == null ? properties.getHome() : savedRequest.getRedirectUrl();
        if (httpServletRequest.getCookies() == null){
            httpServletResponse.sendRedirect(properties.getLogin()); //+ "&goto=" + redirectUrl);
            return null;
        }
        Optional<Cookie> iPDPCookie = Arrays.stream(httpServletRequest.getCookies())
                .filter(a -> a.getName().equals("iPlanetDirectoryPro")).findFirst();


        if (!iPDPCookie.isPresent()) {
            httpServletResponse.sendRedirect(properties.getLogin()); //+ "&goto=" + redirectUrl);
            return null;
        }
        else {

            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add("Cookie","iPlanetDirectoryPro=" + iPDPCookie.get().getValue());
            httpHeaders.add("Content-Type", "application/json");
            httpHeaders.add("Accept-API-Version", "resource=2.1");

            HttpEntity httpEntity = new HttpEntity(httpHeaders);
            RestTemplate restTemplate = new RestTemplate();
            ResponseEntity<OpenAmAttributesResponse> attributesResponse = null;
            try {
                attributesResponse = restTemplate.exchange(properties.getAttributes(), HttpMethod.POST, httpEntity, OpenAmAttributesResponse.class);
            } catch (HttpClientErrorException e) {
                if (e.getStatusCode().equals(HttpStatus.UNAUTHORIZED)) {
                    httpServletResponse.sendRedirect(properties.getLogin() + "&goto=" + redirectUrl);
                    return null;
                } else {
                    httpServletResponse.sendRedirect(properties.getHome());
                }
            }
            if (attributesResponse != null && attributesResponse.hasBody()){

                String username;
                if (!attributesResponse.getBody().properties.get("am.protected.firstName").equals("")){
                    username = attributesResponse.getBody().properties.get("am.protected.firstName") + "_"
                            + attributesResponse.getBody().properties.get("am.protected.lastName");
                }
                else {
                    username = attributesResponse.getBody().username;
                }
                if (username != null){
                    OpenAmAuthToken token = new OpenAmAuthToken(username);
                    return this.getAuthenticationManager().authenticate(token);
                }
            }

            throw new UsernameNotFoundException("This username is a lie");
        }

    }

    @Data
    public static class OpenAmAttributesResponse{
        private String username;
        private Map<String, String> properties;
    }
}
