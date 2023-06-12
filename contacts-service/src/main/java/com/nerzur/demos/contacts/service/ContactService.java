package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.Contact;
import jakarta.validation.constraints.NotNull;

import java.sql.Date;
import java.util.List;

public interface ContactService {

    public List<Contact> listAllContacts();
    public Contact findContactById(Long id);
    public Contact createContact(Contact contact);
    public Contact deleteContact(Long id);
    public Contact updateContact(Contact contact);
    public List<Contact> findByFirstNameOrSecondName(String firstName, String secondName);
    public List<Contact> findAllByBirthDateBetween(Date startDate, Date endDate);
}
