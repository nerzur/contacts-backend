package com.nerzur.demos.contacts.controller;

import com.nerzur.demos.contacts.entity.Address;
import com.nerzur.demos.contacts.entity.Contact;
import com.nerzur.demos.contacts.service.BigContactService;
import com.nerzur.demos.contacts.service.ContactService;
import com.nerzur.demos.contacts.ui.ContactRequest;
import com.nerzur.demos.contacts.util.ErrorMessage;
import com.nerzur.demos.contacts.util.ExceptionsBuilder;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;

@Slf4j
@RestController
@RequestMapping(value = "contact")
public class ContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    BigContactService bigContactService;

    @Operation(summary = "Get all Contacts")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Contacts list",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Contact.class))) })
    })
    @GetMapping
    public ResponseEntity<List<Contact>> listAllContact() {
        log.info("listing all Contact");
        List<Contact> contactList = contactService.listAllContacts();
        log.info("detected " + contactList.size() + " contacts.");
        return ResponseEntity.ok(contactList);
    }

    @Operation(summary = "Get all Contacts Data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the All Contacts list",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Contact.class))) })
    })
    @GetMapping(path = "/allData")
    public ResponseEntity<List<ContactRequest>> listAllContactData() {
        log.info("listing all Contact Data");
        List<ContactRequest> contactList = bigContactService.listAllContacts();
        log.info("detected " + contactList.size() + " contacts.");
        return ResponseEntity.ok(contactList);
    }

    @Operation(summary = "Create a Contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Contact is created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. This Contact is already exist.)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/create")
    public ResponseEntity<Contact> addContact(@RequestBody Contact contact, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Contact contactDb = contactService.createContact(contact);
        return ResponseEntity.status(HttpStatus.OK).body(contactDb);
    }

    @Operation(summary = "Edit a Contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Contact is edited successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated Contact isn´t exists)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/edit")
    public ResponseEntity<Contact> editContact(@RequestBody Contact Contact, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Contact ContactDb = contactService.updateContact(Contact);
        return ResponseEntity.status(HttpStatus.OK).body(ContactDb);
    }

    @Operation(summary = "Delete a Contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Contact is deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated Contact isn´t exists, The indicated Contact cannot be eliminated, as it is in some delivery process, This Contact can´t be deleted)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/delete")
    public ResponseEntity<Contact> deleteContact(@RequestBody Contact Contact, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Contact ContactDb = contactService.deleteContact(Contact.getId());
        return ResponseEntity.status(HttpStatus.OK).body(ContactDb);
    }

    @Operation(summary = "Get all contact data by Contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "All data of Contact",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated Contact isn´t exists)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @GetMapping(path = "/findAllDataByContact/{contactId}")
    public ResponseEntity<ContactRequest> getAllContactData(@PathVariable Long contactId) {
        ContactRequest contactRequestDb = bigContactService.findContactById(contactId);
        return ResponseEntity.status(HttpStatus.OK).body(contactRequestDb);
    }

    @Operation(summary = "Create a Contact with All data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Contact is created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. This Contact is already exist.)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/createFullContact")
    public ResponseEntity<ContactRequest> addContactWithAllData(@RequestBody ContactRequest contactRequest, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        ContactRequest contactRequestDb = bigContactService.createFullContactData(contactRequest);
        return ResponseEntity.status(HttpStatus.OK).body(contactRequestDb);
    }

    @Operation(summary = "Delete a Contact with All data")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Contact is created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Contact.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. This Contact isn't exists.)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/deleteFullContact")
    public ResponseEntity<ContactRequest> deleteFullContact(@RequestBody ContactRequest contactRequest, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        ContactRequest contactRequestDb = bigContactService.deleteFullContactData(contactRequest);
        return ResponseEntity.status(HttpStatus.OK).body(contactRequestDb);
    }
}
