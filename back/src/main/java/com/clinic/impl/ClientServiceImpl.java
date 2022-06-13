package com.clinic.impl;

import com.clinic.dto.SimpleClientRegistration;
import com.clinic.dto.SimplePersonRegistration;
import com.clinic.entities.Client;
import com.clinic.entities.Person;
import com.clinic.entities.User;
import com.clinic.exceptions.ClientConflictException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.repositories.ClientRepository;
import com.clinic.repositories.RoleRepository;
import com.clinic.repositories.UserRepository;
import com.clinic.services.ClientService;
import com.clinic.services.PersonService;
import com.clinic.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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
    public Client save(SimpleClientRegistration clientData)
            throws PersonConflictException, ClientConflictException {
        Person person = personService.save(clientData.getPersonData());

        Client client = new Client();
        client.setPerson(person);
        client.setEmail(clientData.getEmail());
        client.setComment(clientData.getComment());

        Optional<Client> optionalClient = clientRepository.findByPersonId(person.getId());

        if (optionalClient.isPresent())
            if (optionalClient.get() != client)
                throw new ClientConflictException(
                        "There is already client associated with a given person");

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
