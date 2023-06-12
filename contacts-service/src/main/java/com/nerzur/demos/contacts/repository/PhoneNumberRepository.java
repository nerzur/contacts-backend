package com.nerzur.demos.contacts.repository;

import com.nerzur.demos.contacts.entity.Contact;
import com.nerzur.demos.contacts.entity.PhoneNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PhoneNumberRepository extends JpaRepository<PhoneNumber, Long> {

    public PhoneNumber findPhoneNumberByPhoneNumber(String phoneNumber);
    public List<PhoneNumber> findPhoneNumberByContact(Contact contact);
}
