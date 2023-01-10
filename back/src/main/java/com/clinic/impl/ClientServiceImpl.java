package com.clinic.impl;

import com.clinic.dto.*;
import com.clinic.entities.*;
import com.clinic.exceptions.*;
import com.clinic.repositories.*;
import com.clinic.services.ClientService;
import com.clinic.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class ClientServiceImpl implements ClientService {

    private PersonService personService;

    private ClientRepository clientRepository;

    private PassportRepository passportRepository;

    private PersonRepository personRepository;

    private ModificationRepository modificationRepository;

    private ImplantRepository implantRepository;


    @Autowired
    public ClientServiceImpl(
            PersonService ps,
            ClientRepository cr,
            PassportRepository pr,
            PersonRepository personRepository,
            ModificationRepository mr,
            ImplantRepository im
    ){
        this.personService = ps;
        this.clientRepository = cr;
        this.passportRepository = pr;
        this.personRepository = personRepository;
        this.modificationRepository = mr;
        this.implantRepository = im;
    }

    @Override
    public Client createClient(SimpleClientRegistration clientData)
            throws PersonConflictException, ClientConflictException, PassportConflictException
    {
        Person person;
        Optional<Person> optionalPerson = personService.getPersonByPassportNum(clientData.getPerson().getPassport());
        if (optionalPerson.isPresent())
            person = optionalPerson.get();
        else
            person = personService.createPerson(clientData.getPerson());

        Client client = new Client();
        client.setPerson(person);
        client.setEmail(clientData.getEmail());
        client.setComment(clientData.getComment());

        Optional<Client> optionalClient = clientRepository.findByPersonId(person.getId());

        if (optionalClient.isPresent())
            if (optionalClient.get() != client)
                throw new ClientConflictException(person.getId());

        return clientRepository.save(client);
    }

    @Override
    public Client createClient(ExistingPersonClientRegistration clientData) throws PersonConflictException, ClientConflictException, PassportConflictException {
        Client client = new Client();
        client.setPerson(clientData.getPerson());
        client.setEmail(clientData.getEmail());
        client.setComment(clientData.getComment());

        Optional<Client> optionalClient = clientRepository.findByPersonId(clientData.getPerson().getId());

        if (optionalClient.isPresent())
            if (optionalClient.get() != client)
                throw new ClientConflictException(clientData.getPerson().getId());

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
            throws PassportNotFoundException, NoPersonToClientException
    {
        Passport passport = passportRepository.getPassportByPassport(passportNum)
                .orElseThrow(() -> new PassportNotFoundException((passportNum)));

        return clientRepository.findByPersonId(passport.getPerson(). getId())
                .orElseThrow(()-> new NoPersonToClientException(passport.getPerson().getId()));
    }

    @Override
    public Client getClient(Long clientId)
            throws ClientNotFoundException
    {
        return  clientRepository.findById(clientId)
            .orElseThrow(()->new ClientNotFoundException(clientId));
    }

    @Override
    public List<Client> getAllClients() {
        return clientRepository.findAll();
    }

    @Override
    public Client updateClient(SimpleClientRegistration clientInfo, Long clientId) {
        Client currentClient = clientRepository.getOne(clientId);

        currentClient.setEmail(clientInfo.getEmail());
        currentClient.setComment(clientInfo.getComment());

        Person currentPerson = personRepository.getOne(currentClient.getPerson().getId());

        if(currentPerson.getPassports().stream().noneMatch(item -> item.getPassport() == clientInfo.getPerson().getPassport()))
            passportRepository.save(new Passport(currentPerson,clientInfo.getPerson().getPassport()));

        currentPerson.setName(clientInfo.getPerson().getName());
        currentPerson.setSurname(clientInfo.getPerson().getSurname());
        currentPerson.setPatronymic(clientInfo.getPerson().getPatronymic());
        currentPerson.setDateOfBirth(clientInfo.getPerson().getDateOfBirth());

        personRepository.save(currentPerson);


        return clientRepository.save(currentClient);

    }

    @Override
    public Client addPreviousModifications(SimpleModificationAdd modificationAdd)
            throws ClientNotFoundException, ModificationNotFoundException
    {
        Client client = clientRepository.findById(modificationAdd.getClientId())
                .orElseThrow(()->new ClientNotFoundException(modificationAdd.getClientId()));
        Set<Modification> mods = new HashSet<>();

        for (long modId : modificationAdd.getModIds())
        {
            mods.add(modificationRepository.findById(modId).orElseThrow(()-> new ModificationNotFoundException(modId)));
        }

        client.setModifications(mods);

        return clientRepository.save(client);
    }

    @Override
    public Client addImplants(SimpleImplantsUpdate implantsUpdate) throws ClientNotFoundException {

        Client client = clientRepository.findById(implantsUpdate.getClientId())
                .orElseThrow(()->new ClientNotFoundException(implantsUpdate.getClientId()));

        Set<Implant> implants = new HashSet<>();
        for (SimpleImplant item : implantsUpdate.getImplants())
        {
            Implant implant = new Implant();
            implant.setImplantation_date(item.getImplantation_date());
            implant.setName(item.getName());
            implant.setDescription(item.getDescription());
            implant.setNumber(item.getNumber());
            implant.setId(item.getId());
            implants.add(implant);
        }

        implants = new HashSet<>(implantRepository.saveAll(implants));

        client.setImplants(implants);

        client = clientRepository.save(client);

        return client;
    }

    @Override
    public Boolean dropImplant(long implantId) throws ImplantNotFountException {
            implantRepository.deleteById(implantId);
            return true;
    }

}
