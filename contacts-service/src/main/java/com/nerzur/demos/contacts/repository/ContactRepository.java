package com.nerzur.demos.contacts.repository;

import com.nerzur.demos.contacts.entity.Contact;
import jakarta.validation.constraints.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContactRepository extends JpaRepository<Contact, Long> {

    public List<Contact> findByFirstNameOrSecondName(@NotNull String firstName, @NotNull String secondName);
}
