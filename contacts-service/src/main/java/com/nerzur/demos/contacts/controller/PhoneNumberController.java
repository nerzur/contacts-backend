package com.nerzur.demos.contacts.controller;

import com.nerzur.demos.contacts.entity.PhoneNumber;
import com.nerzur.demos.contacts.entity.PhoneNumber;
import com.nerzur.demos.contacts.service.PhoneNumberService;
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
@RequestMapping(value = "phoneNumber")
public class PhoneNumberController {

    @Autowired
    private PhoneNumberService phoneNumberService;

    @Operation(summary = "Get all Phone Numbers")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Phone Numbers list",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = PhoneNumber.class))) })
    })
    @GetMapping
    public ResponseEntity<List<PhoneNumber>> listAllPhoneNumber() {
        log.info("listing all Phone Numbers");
        List<PhoneNumber> phoneNumberList = phoneNumberService.listAllPhoneNumber();
        log.info("detected " + phoneNumberList.size() + " PhoneNumbers.");
        return ResponseEntity.ok(phoneNumberList);
    }

    @Operation(summary = "Find Phone Number by Id")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Phone numbers has been obtained successfully.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhoneNumber.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated Phone Number isn't exist.)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @GetMapping(path = "/{id}")
    public ResponseEntity<PhoneNumber> findPhoneNumbersById(@PathVariable(required = true) Long id) {
        PhoneNumber phoneNumberDb = phoneNumberService.findPhoneNumberById(id);
        return ResponseEntity.status(HttpStatus.OK).body(phoneNumberDb);
    }

    @Operation(summary = "Find Phone Numbers by contact")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Phone numbers has been obtained successfully.",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhoneNumber.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated Phone Number isn't exist.)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @GetMapping(path = "/findByPhoneNumber/{PhoneNumberId}")
    public ResponseEntity<List<PhoneNumber>> findPhoneNumbersByPhoneNumber(@PathVariable(required = true) Long PhoneNumberId) {
        List<PhoneNumber> phoneNumber = phoneNumberService.findPhoneNumbersByContact(PhoneNumberId);
        return ResponseEntity.status(HttpStatus.OK).body(phoneNumber);
    }

    @Operation(summary = "Create a PhoneNumber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The PhoneNumber is created",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhoneNumber.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated PhoneNumber is exists)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/create")
    public ResponseEntity<PhoneNumber> createPhoneNumber(@RequestBody PhoneNumber PhoneNumber, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        PhoneNumber PhoneNumberDb = phoneNumberService.createPhoneNumber(PhoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(PhoneNumberDb);
    }

    @Operation(summary = "Edit a PhoneNumber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The PhoneNumber is edited successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhoneNumber.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated PhoneNumber isn´t exists)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/edit")
    public ResponseEntity<PhoneNumber> editPhoneNumber(@RequestBody PhoneNumber PhoneNumber, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        PhoneNumber PhoneNumberDb = phoneNumberService.updatePhoneNumber(PhoneNumber);
        return ResponseEntity.status(HttpStatus.CREATED).body(PhoneNumberDb);
    }

    @Operation(summary = "Delete a PhoneNumber")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The PhoneNumber is deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = PhoneNumber.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated PhoneNumber isn´t exists, The indicated PhoneNumber cannot be eliminated, as it is in some delivery process, This PhoneNumber can´t be deleted)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/delete")
    public ResponseEntity<PhoneNumber> deletePhoneNumber(@RequestBody PhoneNumber PhoneNumber, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        PhoneNumber PhoneNumberDb = phoneNumberService.deletePhoneNumber(PhoneNumber.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(PhoneNumberDb);
    }
    
    
}
