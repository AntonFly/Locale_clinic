package com.police.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.police.configs.openam.OpenAmProperties;
import com.police.entities.User;
import com.police.openam.RegistrationInfo;
import com.police.openam.SimpleUserRegistration;
import com.police.repositories.RoleRepository;
import com.police.repositories.UserRepository;
import com.police.services.UserService;
import com.police.validators.UserRegistrationValidator;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.BindingResult;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    private UserRepository userRepository;
    private RoleRepository roleRepository;
    private UserRegistrationValidator userRegistrationValidator;
    private final OpenAmProperties properties;


    @Autowired
    public UserServiceImpl(UserRepository ur, RoleRepository rr, UserRegistrationValidator uv, OpenAmProperties oap){
        this.userRepository = ur;
        this.roleRepository = rr;
        this.userRegistrationValidator = uv;
        this.properties = oap;
    }

    @Override
    public User getUserByUsername(String username) {
        return userRepository.findByUsername(username);
    }

    @Override
    public User save(User user) {
        user = userRepository.save(user);
        userRepository.flush();
        return user;
    }

    @Override
    public void delete(User user) {
        userRepository.delete(user);
    }

    @Override
    public boolean userExists(String username) {
        return userRepository.findByUsername(username) != null;
    }

    @Override
    public User setRole(User user, String roleName) {
        user.setRole(roleRepository.findByName(roleName));
        return user;
    }

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public SimpleUserRegistration registerUser(SimpleUserRegistration userData) {

        BindingResult bindingResult  = new BeanPropertyBindingResult(userData, "userData");
        userRegistrationValidator.validate(userData, bindingResult);
        if (bindingResult.hasErrors())
            return null;

        User user = new User();
        user.setUsername(userData.getUsername());

        HttpEntity infoEntity = new HttpEntity(new HttpHeaders());
        RestTemplate infoTemplate = new RestTemplate();
        ResponseEntity<RegistrationInfo> infoResponse = null;

        try{
            infoResponse = infoTemplate.exchange(properties.getRegistration().getInfo(), HttpMethod.GET, infoEntity, RegistrationInfo.class);
            if (!infoResponse.hasBody() || !infoResponse.getBody().getType().equals("userDetails")) {
                throw new HttpClientErrorException(HttpStatus.BAD_REQUEST);
            }

            HttpHeaders dataHeaders = new HttpHeaders();
            dataHeaders.add("Content-Type", "application/json");
            dataHeaders.add("Accept-API-Version", "resource=1.0");

            ObjectMapper objectMapper = new ObjectMapper();
            String postData = "{ \"input\":{\"user\":" + objectMapper.writeValueAsString(userData) + "}}";

            HttpEntity<String> dataEntity = new HttpEntity<>(postData, dataHeaders);
            RestTemplate dataTemplate = new RestTemplate();
            ResponseEntity<RegistrationInfo> dataResponse = null;

            dataResponse = dataTemplate.exchange(properties.getRegistration().getData(), HttpMethod.POST, dataEntity, RegistrationInfo.class);
            if(!dataResponse.hasBody() || !dataResponse.getBody().getStatus().isSuccess())
                throw new HttpClientErrorException((HttpStatus.BAD_REQUEST));
            setRole(user, "USER");
            if (userRepository.findByUsername(user.getUsername()) == null)
                userRepository.save(user);
        }
        catch (HttpClientErrorException | JsonProcessingException | NullPointerException e){
            return null;
        }

        return userData;

    }
}
