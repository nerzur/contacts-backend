package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.Contact;
import com.nerzur.demos.contacts.entity.PhoneNumber;
import com.nerzur.demos.contacts.repository.ContactRepository;
import com.nerzur.demos.contacts.repository.PhoneNumberRepository;
import com.nerzur.demos.contacts.util.ExceptionsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class PhoneNumberServiceImpl implements PhoneNumberService {

    private final PhoneNumberRepository phoneNumberRepository;
    private final ContactRepository contactRepository;
    private final BindingResult result = new BindException(new Exception(), "");


    @Override
    public List<PhoneNumber> listAllPhoneNumber() {
        return phoneNumberRepository.findAll();
    }

    @Override
    public PhoneNumber findPhoneNumberById(Long id) {
        return phoneNumberRepository.findById(id).orElse(null);
    }

    @Override
    public List<PhoneNumber> findPhoneNumbersByContact(Long contactId) {
        Contact contactDb = contactRepository.findById(contactId).orElse(null);
        if(null == contactDb){
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn´t exists.");
            return null;
        }
        return phoneNumberRepository.findPhoneNumberByContact(contactDb);
    }


    @Override
    public boolean findIfPhoneNumberExists(String phoneNumber) {
        return phoneNumberRepository.findPhoneNumberByPhoneNumber(phoneNumber) != null;
    }

    @Override
    public PhoneNumber createPhoneNumber(PhoneNumber phoneNumber) {
        if (findIfPhoneNumberExists(phoneNumber.getPhoneNumber())) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated phoneNumber is exists.");
            return null;
        }
        return phoneNumberRepository.save(phoneNumber);
    }

    @Override
    public PhoneNumber deletePhoneNumber(Long id) {
        PhoneNumber phoneNumberDb = findPhoneNumberById(id);
        if (null == phoneNumberDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn´t exists.");
            return null;
        }
        phoneNumberRepository.delete(phoneNumberDb);
        return phoneNumberDb;
    }

    @Override
    public PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber) {
        PhoneNumber phoneNumberDb = findPhoneNumberById(phoneNumber.getId());
        if (null == phoneNumberDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn´t exists.");
            return null;
        }
        phoneNumberDb.setPhoneNumber(phoneNumber.getPhoneNumber());
        return phoneNumberRepository.save(phoneNumber);
    }
}
