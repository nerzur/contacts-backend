package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.Contact;

import java.util.List;

public interface ContactService {

    public List<Contact> listAllContacts();
    public Contact findContactById(Long id);
    public Contact createContact(Contact contact);
    public Contact deleteContact(Long id);
    public Contact updateContact(Contact contact);
}
