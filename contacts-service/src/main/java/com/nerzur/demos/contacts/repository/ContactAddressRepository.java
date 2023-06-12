package com.nerzur.demos.contacts.repository;

import com.nerzur.demos.contacts.entity.Address;
import com.nerzur.demos.contacts.entity.Contact;
import com.nerzur.demos.contacts.entity.ContactAddress;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactAddressRepository extends JpaRepository<ContactAddress, Long> {

    List<ContactAddress> findContactAddressByContact(Contact contact);
    List<ContactAddress> findContactAddressByAddress(Address address);
    List<ContactAddress> findContactAddressByContactAndAddress(Contact contact, Address address);
}
