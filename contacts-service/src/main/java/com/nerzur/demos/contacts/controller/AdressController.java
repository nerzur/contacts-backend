package com.nerzur.demos.contacts.controller;

import com.nerzur.demos.contacts.entity.Address;
import com.nerzur.demos.contacts.service.AddressService;
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
@RequestMapping(value = "address")
public class AdressController {

    @Autowired
    AddressService addressService;

    @Operation(summary = "Get all Adress")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Found the Address list",
                    content = { @Content(mediaType = "application/json",
                            array = @ArraySchema(schema = @Schema(implementation = Address.class))) })
    })
    @GetMapping
    public ResponseEntity<List<Address>> listAllAddress() {
        log.info("listing all Addresses");
        List<Address> addressList = addressService.listAllAddress();
        log.info("detected " + addressList.size() + " Addresses.");
        return ResponseEntity.ok(addressList);
    }

    @Operation(summary = "Create a Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Address is created successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Address.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. This Address is already exist.)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/create")
    public ResponseEntity<Address> addAddress(@RequestBody Address address, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Address addressDb = addressService.createaddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressDb);
    }

    @Operation(summary = "Edit a Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Address is edited successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Address.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated Address isn´t exists)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/edit")
    public ResponseEntity<Address> editAddress(@RequestBody Address address, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Address addressDb = addressService.updateaddress(address);
        return ResponseEntity.status(HttpStatus.CREATED).body(addressDb);
    }

    @Operation(summary = "Delete a Address")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "The Address is deleted successfully",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Address.class)) }),
            @ApiResponse(responseCode = "400", description = "An error is occurred (ie. The indicated Address isn´t exists, The indicated Address cannot be eliminated, as it is in some delivery process, This Address can´t be deleted)",
                    content = { @Content(mediaType = "application/json",
                            schema = @Schema(implementation = ErrorMessage.class)) }),
    })
    @PostMapping(path = "/delete")
    public ResponseEntity<Address> deleteAddress(@RequestBody Address address, BindingResult result) {
        if (result.hasErrors()) {
            log.error("One or more errors has been occurred");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST, ExceptionsBuilder.formatMessage(result));
        }
        Address AddressDb = addressService.deleteaddress(address.getId());
        return ResponseEntity.status(HttpStatus.CREATED).body(AddressDb);
    }
}
