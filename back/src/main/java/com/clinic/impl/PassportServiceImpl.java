package com.clinic.impl;

import com.clinic.entities.Passport;
import com.clinic.entities.Person;
import com.clinic.exceptions.PassportConflictException;
import com.clinic.exceptions.PersonConflictException;
import com.clinic.repositories.PassportRepository;
import com.clinic.services.PassportService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
@Service
public class PassportServiceImpl implements PassportService {

    private PassportRepository passportRepository;


    @Autowired
    public PassportServiceImpl(PassportRepository pr){
        this.passportRepository = pr;
    }

    @Override
    public Passport save(Person person, long passportNum) throws PassportConflictException {
        Passport passport =  new Passport(person, passportNum);

        Optional<Passport> optionalPassport = passportRepository.getPassportByPassport(passport.getPassport());
        if (optionalPassport.isPresent())
            if (optionalPassport.get().getPerson().getId() != person.getId())
                throw new PassportConflictException(passportNum);
            else
                return optionalPassport.get();

        passport = passportRepository.saveAndFlush(passport);

        return  passport;
    }


    @Override
    public void delete(Passport person) {

    }

    @Override
    public Optional<Passport> getPassportById(Long passportNum) {return passportRepository.findById(passportNum);}


    @Override
    public List<Passport> getAllPassport() {return passportRepository.findAll();}

    @Override
    public  List<Passport>  findAllByPerson(Person person) { return passportRepository.findAllByPerson(person); }
}
