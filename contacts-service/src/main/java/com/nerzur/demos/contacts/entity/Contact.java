package com.nerzur.demos.contacts.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfiguration;

import java.io.Serializable;
import java.sql.Date;

@Entity
@Table(name = "tbl_contact")
@AutoConfiguration
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Contact implements Serializable {

    private static final Long serialVersionUID = 1L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Column(length = 256)
    String firstName;

    @NotNull
    @Column(length = 256)
    String secondName;

    @NotNull
    @Temporal(TemporalType.DATE)
    Date birthDate;

    @Column(length = 200000)
    String personalPhoto;
}
