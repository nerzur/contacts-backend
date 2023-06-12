package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.ui.ContactRequest;

import java.util.List;

public interface BigContactService {

    public List<ContactRequest> listAllContacts();
    public ContactRequest findContactById(Long id);
    public ContactRequest createFullContactData(ContactRequest contactRequest);
    public ContactRequest deleteFullContactData(ContactRequest contactRequest);
    public ContactRequest editFullContactData(ContactRequest contactRequest);
}
