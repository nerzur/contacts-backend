package com.nerzur.demos.contacts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.io.Serial;
import java.io.Serializable;

@Entity
@Table(name = "tbl_phoneNumber")
@AutoConfiguration
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Data
public class PhoneNumber implements Serializable {

    @Serial
    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    private String phoneNumber;

    @JoinColumn(name = "contact_id", referencedColumnName = "id")
    @ManyToOne(optional = false)
    private Contact contact;
}
