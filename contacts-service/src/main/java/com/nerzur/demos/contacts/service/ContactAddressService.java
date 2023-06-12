package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.Address;
import com.nerzur.demos.contacts.entity.Contact;
import com.nerzur.demos.contacts.entity.ContactAddress;

import java.util.List;

public interface ContactAddressService {

    public List<ContactAddress> listAllContactAddresses();
    public ContactAddress findContactAddressById(Long id);
    public List<ContactAddress> findContactAddressByContact(Contact contact);
    public List<ContactAddress> findContactAddressByAddress(Address address);
    public ContactAddress createContactAddress(ContactAddress contactAddress);
    public ContactAddress deleteContactAddress(ContactAddress contactAddress);
    public ContactAddress updateContactAddress(ContactAddress contactAddress);
}
