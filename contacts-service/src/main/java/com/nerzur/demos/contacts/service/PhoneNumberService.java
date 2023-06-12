package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.Contact;
import com.nerzur.demos.contacts.entity.PhoneNumber;

import java.util.List;

public interface PhoneNumberService {

    public List<PhoneNumber> listAllPhoneNumber();

    public PhoneNumber findPhoneNumberById(Long id);

    public List<PhoneNumber> findPhoneNumbersByContact(Long contactId);

    public boolean findIfPhoneNumberExists(String phoneNumber);

    public PhoneNumber createPhoneNumber(PhoneNumber phoneNumber);

    public PhoneNumber deletePhoneNumber(Long id);

    public PhoneNumber updatePhoneNumber(PhoneNumber phoneNumber);
}
