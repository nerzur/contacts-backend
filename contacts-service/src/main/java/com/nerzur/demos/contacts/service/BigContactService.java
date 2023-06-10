package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.ui.ContactRequest;

import java.util.List;

public interface BigContactService {

    public List<ContactRequest> listAllContacts();

    public ContactRequest findContactById(Long id);
}
