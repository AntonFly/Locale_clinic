package com.clinic.impl;

import com.clinic.dto.ExistingPersonClientRegistration;
import com.clinic.dto.SimpleClientRegistration;
import com.clinic.entities.Client;
import com.clinic.entities.Passport;
import com.clinic.entities.Person;
import com.clinic.exceptions.ClientConflictException;
import com.clinic.exceptions.ClientNotFoundException;
import com.clinic.exceptions.PassportConflictException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.repositories.ClientRepository;
import com.clinic.repositories.PassportRepository;
import com.clinic.services.ClientService;
import com.clinic.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ClientServiceImpl implements ClientService {

    private PersonService personService;

    private ClientRepository clientRepository;

    private PassportRepository passportRepository;


    @Autowired
    public ClientServiceImpl(PersonService ps, ClientRepository cr, PassportRepository pr){
        this.personService = ps;
        this.clientRepository = cr;
        this.passportRepository = pr;
    }

    @Override
    public Client save(SimpleClientRegistration clientData)
            throws PersonConflictException, ClientConflictException, PassportConflictException {
        Person person = personService.save(clientData.getPerson());

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
    public Client save(ExistingPersonClientRegistration clientData) throws PersonConflictException, ClientConflictException, PassportConflictException {
        Client client = new Client();
        client.setPerson(clientData.getPerson());
        client.setEmail(clientData.getEmail());
        client.setComment(clientData.getComment());

        Optional<Client> optionalClient = clientRepository.findByPersonId(clientData.getPerson().getId());

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
    public Client getClientByPassport(Long passportNum)
            throws ClientNotFoundException
    {
        Passport passport = passportRepository.getPassportByPassport(passportNum).orElseThrow(()->
                new ClientNotFoundException(
                        "There is no client associated with " +
                                (passportNum == null ? "empty" : passportNum.toString()) +
                                " passport number"));

        Optional<Client> client = clientRepository.findByPersonId(passport.getPerson().getId());

        return client.orElseThrow(()-> new ClientNotFoundException(
                "An error occured while getting client by passort"));

    }
    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

}
