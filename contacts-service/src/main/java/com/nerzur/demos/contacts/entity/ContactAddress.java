package com.nerzur.demos.contacts.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table (name = "tbl_contact_address")
@AutoConfiguration
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
public class ContactAddress implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @EmbeddedId
    ContactAdrressesPk contactAdrressesPk;

    @JoinColumn(name = "contactId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Contact contact;

    @JoinColumn(name = "addressId", referencedColumnName = "id", insertable = false, updatable = false)
    @ManyToOne(optional = false)
    private Address address;
}
