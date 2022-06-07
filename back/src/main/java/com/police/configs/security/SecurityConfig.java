package com.police.configs.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.BeanIds;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.Authentication;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.oauth2.config.annotation.web.configuration.EnableOAuth2Client;
import org.springframework.security.web.authentication.AbstractAuthenticationProcessingFilter;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.CorsConfigurationSource;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Arrays;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class SecurityConfig extends WebSecurityConfigurerAdapter{

    private LogoutSuccessHandler logoutSuccessHandler;

    private AbstractAuthenticationProcessingFilter authenticationProcessingFilter;

    @Autowired
    public SecurityConfig(LogoutSuccessHandler lsh, @Lazy @Qualifier("openAmAuthFilter") AbstractAuthenticationProcessingFilter aupf){
        this.logoutSuccessHandler = lsh;
        this.authenticationProcessingFilter = aupf;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http.csrf().disable().cors().and()
                .addFilterAt(authenticationProcessingFilter, UsernamePasswordAuthenticationFilter.class)
                .authorizeRequests()
                .antMatchers("/login", "/logout", "/home", "/registration", "/rmq", "/init").permitAll()
                .antMatchers("/officer/info").hasAnyRole("OFFICER", "ADMIN", "DISPATCHER")
                .antMatchers("/hello_officer", "/map").hasAnyRole("USER", "OFFICER", "DISPATCHER", "ADMIN")
                .antMatchers("/officer/call_history", "/officer/active_call", "/officer/search", "/officer/message").hasAnyRole("OFFICER")
                .antMatchers("/dispatcher/create_call").hasAnyRole("DISPATCHER")
                .antMatchers("/admin/create_officer", "/admin/hm").hasAnyRole("ADMIN")
                .and().formLogin().loginPage("/login")
                .and().logout().logoutUrl("/logout").logoutSuccessHandler(logoutSuccessHandler);
    }

    @Bean
    CorsConfigurationSource corsConfigurationSource() {
        CorsConfiguration configuration = new CorsConfiguration();
        configuration.setAllowedOrigins(Arrays.asList("http://www.nypolicecw.com:7080"));
        configuration.setAllowedMethods(Arrays.asList("GET","POST"));
        UrlBasedCorsConfigurationSource source = new UrlBasedCorsConfigurationSource();
        source.registerCorsConfiguration("/**", configuration);
        return source;
    }

    @Bean
    @Override
    public AuthenticationManager authenticationManagerBean() throws Exception{
        return authenticationManager();
    }

    @Bean
    public BCryptPasswordEncoder PasswordEncoder(){
        return new BCryptPasswordEncoder();
    }

}