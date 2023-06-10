package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.Contact;
import com.nerzur.demos.contacts.repository.ContactRepository;
import com.nerzur.demos.contacts.util.ExceptionsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ContactServiceImpl implements ContactService {

    private final ContactRepository contactRepository;
    private final BindingResult result = new BindException(new Exception(), "");


    @Override
    public List<Contact> listAllContacts() {
        return contactRepository.findAll();
    }

    @Override
    public Contact findContactById(Long id) {
        return contactRepository.findById(id).orElse(null);
    }

    @Override
    public Contact createContact(Contact contact) {
        return contactRepository.save(contact);
    }

    @Override
    public Contact deleteContact(Long id) {
        Contact contactDb = findContactById(id);
        if (null == contactDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn´t exists.");
            return null;
        }
        contactRepository.delete(contactDb);
        return contactDb;
    }

    @Override
    public Contact updateContact(Contact contact) {
        Contact contactDb = findContactById(contact.getId());
        if (null == contactDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn´t exists.");
            return null;
        }
        contactDb.setFirstName(contactDb.getFirstName());
        contactDb.setSecondName(contactDb.getSecondName());
        contactDb.setBirthDate(contactDb.getBirthDate());
        contactDb.setPersonalPhoto(contactDb.getPersonalPhoto());
        return contactRepository.save(contactDb);
    }
}
