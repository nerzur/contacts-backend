package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.Address;
import com.nerzur.demos.contacts.entity.Contact;
import com.nerzur.demos.contacts.entity.ContactAddress;
import com.nerzur.demos.contacts.entity.PhoneNumber;
import com.nerzur.demos.contacts.repository.ContactAddressRepository;
import com.nerzur.demos.contacts.repository.ContactRepository;
import com.nerzur.demos.contacts.repository.PhoneNumberRepository;
import com.nerzur.demos.contacts.ui.ContactRequest;
import com.nerzur.demos.contacts.util.ExceptionsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class BigContactServiceImpl implements BigContactService {

    @Autowired
    private final ContactRepository contactRepository;
    @Autowired
    private final ContactAddressRepository contactAddressRepository;
    @Autowired
    private final PhoneNumberRepository phoneNumberRepository;
    private final BindingResult result = new BindException(new Exception(), "");

    @Override
    public List<ContactRequest> listAllContacts() {
        List<Contact> contactList = contactRepository.findAll();
        List<ContactRequest> contactRequestList = new ArrayList<>();
        contactList.forEach(contact -> {
            List<Address> contactAdressList = new ArrayList<>();

            contactAddressRepository.findContactAddressByContact(contact).forEach(contactAddress -> {
                contactAdressList.add(contactAddress.getAddress());
            });
            List<PhoneNumber> contactPhoneNumberList = phoneNumberRepository.findPhoneNumberByContact(contact);
            contactRequestList.add(ContactRequest.builder()
                    .contact(contact)
                    .addresses(contactAdressList)
                    .phoneNumbers(contactPhoneNumberList)
                    .build()
            );
        });
        return contactRequestList;
    }

    @Override
    public ContactRequest findContactById(Long id) {
        Contact contact = contactRepository.findById(id).orElse(null);
        if (null == contact) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn't exists.");
            return null;
        }
        List<Address> contactAddressesList = new ArrayList<>();
        contactAddressRepository.findContactAddressByContact(contact).forEach(contactAddress -> {
            contactAddressesList.add(contactAddress.getAddress());
        });
        return ContactRequest.builder()
                .contact(contact)
                .addresses(contactAddressesList)
                .phoneNumbers(phoneNumberRepository.findPhoneNumberByContact(contact))
                .build();
    }
}
