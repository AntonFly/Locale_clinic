package com.clinic.impl;

import com.clinic.entities.Client;
import com.clinic.entities.User;
import com.clinic.repositories.ClientRepository;
import com.clinic.repositories.RoleRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.ClientService;
import com.clinic.services.PersonService;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ClientServiceImpl implements ClientService {

    private PersonService personService;

    private ClientRepository clientRepository;


    @Autowired
    public ClientServiceImpl(PersonService ps, ClientRepository cr){
        this.personService = ps;
        this.clientRepository = cr;
    }

    @Override
    public Client save(Client client) {
        client = clientRepository.save(client);
        clientRepository.flush();
        return client;
    }

    @Override
    public void delete(Client user) {
        clientRepository.delete(user);
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

}
