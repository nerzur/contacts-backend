package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.*;
import com.nerzur.demos.contacts.repository.AddressRepository;
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
    private final AddressRepository addressRepository;
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

    @Override
    public ContactRequest createFullContactData(ContactRequest contactRequest) {
        Contact contactDb = contactRepository.save(Contact.builder()
                .firstName(contactRequest.getContact().getFirstName())
                .secondName(contactRequest.getContact().getSecondName())
                .birthDate(contactRequest.getContact().getBirthDate())
                .personalPhoto(contactRequest.getContact().getPersonalPhoto())
                .build());
        List<Address> addressList = new ArrayList<>();
        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        contactRequest.getAddresses().forEach(address -> {
            Address addressDb = addressRepository.findAddressByAddress(address.getAddress());
            if (null == addressDb)
                addressDb = addressRepository.save(Address.builder().address(address.getAddress()).build());
            addressList.add(addressDb);
            contactAddressRepository.save(ContactAddress.builder()
                    .contactAdrressesPk(ContactAdrressesPk.builder()
                            .contactId(contactDb.getId())
                            .addressId(addressDb.getId())
                            .build())
                    .contact(contactDb)
                    .address(addressDb)
                    .build());
        });
        contactRequest.getPhoneNumbers().forEach(phoneNumber -> {
            PhoneNumber phoneNumberDb = new PhoneNumber();
            phoneNumberDb.setContact(contactDb);
            phoneNumberDb.setPhoneNumber(phoneNumber.getPhoneNumber());
            System.out.println(phoneNumberDb.toString());
            if (null == phoneNumberRepository.findPhoneNumberByPhoneNumber(phoneNumber.getPhoneNumber())) {
                phoneNumberDb = phoneNumberRepository.save(phoneNumberDb);
                phoneNumberList.add(phoneNumberDb);
            }
        });

        return findContactById(contactDb.getId());
    }

    @Override
    public ContactRequest deleteFullContactData(ContactRequest contactRequest) {
        Contact contactDb = contactRepository.findById(contactRequest.getContact().getId()).orElse(null);
        if (null == contactDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn't exists.");
            return null;
        }
        contactRequest.getAddresses().stream().map(address -> addressRepository.findAddressByAddress(address.getAddress())).map(addressDb -> contactAddressRepository.findContactAddressByContactAndAddress(contactDb, addressDb)).forEach(contactAddressRepository::deleteAll);
        contactRequest.getPhoneNumbers().forEach(phoneNumber -> {
            PhoneNumber phoneNumberDb = phoneNumberRepository.findPhoneNumberByPhoneNumber(phoneNumber.getPhoneNumber());
            phoneNumberRepository.delete(phoneNumber);
        });
        contactRepository.delete(contactDb);
        return contactRequest;
    }

    @Override
    public ContactRequest editFullContactData(ContactRequest contactRequest) {
        ContactRequest contactRequestDb = findContactById(contactRequest.getContact().getId());
        if (null == contactRequestDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated contact isn't exists.");
            return null;
        }
        contactRequestDb.getAddresses().stream().map(address -> addressRepository.findAddressByAddress(address.getAddress())).map(addressDb -> contactAddressRepository.findContactAddressByContactAndAddress(contactRequestDb.getContact(), addressDb)).forEach(contactAddressRepository::deleteAll);
        contactRequestDb.getPhoneNumbers().forEach(phoneNumber -> {
            PhoneNumber phoneNumberDb = phoneNumberRepository.findPhoneNumberByPhoneNumber(phoneNumber.getPhoneNumber());
            phoneNumberRepository.delete(phoneNumber);
        });

        Contact contact = contactRequestDb.getContact();
        contact.setFirstName(contactRequest.getContact().getFirstName());
        contact.setSecondName(contactRequest.getContact().getSecondName());
        contact.setBirthDate(contactRequest.getContact().getBirthDate());
        contact.setPersonalPhoto(contactRequest.getContact().getPersonalPhoto());
        Contact contactDb = contactRepository.save(contact);

        List<Address> addressList = new ArrayList<>();
        List<PhoneNumber> phoneNumberList = new ArrayList<>();
        contactRequest.getAddresses().forEach(address -> {
            Address addressDb = addressRepository.findAddressByAddress(address.getAddress());
            if (null == addressDb)
                addressDb = addressRepository.save(Address.builder().address(address.getAddress()).build());
            addressList.add(addressDb);
            contactAddressRepository.save(ContactAddress.builder()
                    .contactAdrressesPk(ContactAdrressesPk.builder()
                            .contactId(contactDb.getId())
                            .addressId(addressDb.getId())
                            .build())
                    .contact(contactDb)
                    .address(addressDb)
                    .build());
        });
        contactRequest.getPhoneNumbers().forEach(phoneNumber -> {
            PhoneNumber phoneNumberDb = new PhoneNumber();
            phoneNumberDb.setContact(contactDb);
            phoneNumberDb.setPhoneNumber(phoneNumber.getPhoneNumber());
            System.out.println(phoneNumberDb.toString());
            if (null == phoneNumberRepository.findPhoneNumberByPhoneNumber(phoneNumber.getPhoneNumber())) {
                phoneNumberDb = phoneNumberRepository.save(phoneNumberDb);
                phoneNumberList.add(phoneNumberDb);
            }
        });

        return findContactById(contactDb.getId());
    }
}
