package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.Address;
import com.nerzur.demos.contacts.entity.Contact;
import com.nerzur.demos.contacts.entity.ContactAddress;
import com.nerzur.demos.contacts.repository.AddressRepository;
import com.nerzur.demos.contacts.repository.ContactAddressRepository;
import com.nerzur.demos.contacts.repository.ContactRepository;
import com.nerzur.demos.contacts.util.ExceptionsBuilder;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.List;

@Service
public class ContactAddressServiceImpl implements ContactAddressService {

    @Autowired
    AddressRepository addressRepository;
    @Autowired
    ContactRepository contactRepository;
    @Autowired
    ContactAddressRepository contactAddressRepository;
    private final BindingResult result = new BindException(new Exception(), "");

    @Override
    public List<ContactAddress> listAllContactAddresses() {
        return contactAddressRepository.findAll();
    }

    @Override
    public ContactAddress findContactAddressById(Long id) {
        return contactAddressRepository.findById(id).orElse(null);
    }

    @Override
    public List<ContactAddress> findContactAddressByContact(Contact contact) {
        Contact contactDb = contactRepository.findById(contact.getId()).orElse(null);
        if (null == contactDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn't exists.");
            return null;
        }
        return contactAddressRepository.findContactAddressByContact(contactDb);
    }

    @Override
    public List<ContactAddress> findContactAddressByAddress(Address address) {
        Address addressDb = addressRepository.findById(address.getId()).orElse(null);
        if (null == addressDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated Address isn't exists.");
            return null;
        }
        return contactAddressRepository.findContactAddressByAddress(addressDb);
    }

    @Override
    public ContactAddress createContactAddress(ContactAddress contactAddress) {
        Contact contactDb = contactRepository.findById(contactAddress.getContact().getId()).orElse(null);
        if (null == contactDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn't exists.");
            return null;
        }
        Address addressDb = addressRepository.findAddressByAddress(contactAddress.getAddress().getAddress());
        if (null != addressDb) {
            if (null != contactAddressRepository.findContactAddressByContactAndAddress(contactDb, addressDb)) {
                ExceptionsBuilder.launchException(result, this.getClass().getName(), "This entry is already exist.");
                return null;
            } else {
                addressDb = addressRepository.save(contactAddress.getAddress());
            }
        }
        return contactAddressRepository.save(ContactAddress.builder()
                .contact(contactDb)
                .address(addressDb)
                .build());
    }

    @Override
    public ContactAddress deleteContactAddress(ContactAddress contactAddress) {
        Contact contactDb = contactRepository.findById(contactAddress.getContact().getId()).orElse(null);
        if (null == contactDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn't exists.");
            return null;
        }
        Address addressDb = addressRepository.findAddressByAddress(contactAddress.getAddress().getAddress());
        if (null == addressDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated address isn't exists.");
            return null;
        }
        ContactAddress contactAddressDb = ContactAddress.builder()
                .contact(contactDb)
                .address(addressDb)
                .build();
        contactAddressRepository.delete(contactAddressDb);
        return contactAddressDb;
    }

    @Override
    public ContactAddress updateContactAddress(ContactAddress contactAddress) {
        Contact contactDb = contactRepository.findById(contactAddress.getContact().getId()).orElse(null);
        if (null == contactDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn't exists.");
            return null;
        }
        Address addressDb = addressRepository.findAddressByAddress(contactAddress.getAddress().getAddress());
        if (null == addressDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated address isn't exists.");
            return null;
        }
        ContactAddress contactAddressDb = ContactAddress.builder()
                .contact(contactDb)
                .address(addressDb)
                .build();
        if (null != contactAddressRepository.findContactAddressByContactAndAddress(contactDb, addressDb)) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "This entry is already exist.");
            return null;
        }
        contactAddressRepository.delete(contactAddressDb);
        return contactAddressDb;
    }
}
