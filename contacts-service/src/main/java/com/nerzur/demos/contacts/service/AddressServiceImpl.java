package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.Address;
import com.nerzur.demos.contacts.entity.Contact;
import com.nerzur.demos.contacts.repository.AddressRepository;
import com.nerzur.demos.contacts.util.ExceptionsBuilder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindException;
import org.springframework.validation.BindingResult;

import java.util.List;

@RequiredArgsConstructor
@Service
public class AddressServiceImpl implements AddressService {

    private final AddressRepository addressRepository;
    private final BindingResult result = new BindException(new Exception(), "");


    @Override
    public List<Address> listAllAddress() {
        return addressRepository.findAll();
    }

    @Override
    public Address findAddressById(Long id) {
        return addressRepository.findById(id).orElse(null);
    }

    @Override
    public boolean findIfAddressExists(String address) {
        return addressRepository.findAddressByAddress(address) != null;
    }

    @Override
    public Address createaddress(Address address) {
        if (findIfAddressExists(address.getAddress())) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated address is exists.");
            return null;
        }
        return addressRepository.save(address);
    }

    @Override
    public Address deleteaddress(Long id) {
        Address addressDb = findAddressById(id);
        if (null == addressDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated address isn´t exists.");
            return null;
        }
        addressRepository.delete(addressDb);
        return addressDb;
    }

    @Override
    public Address updateaddress(Address address) {
        Address addressDb = findAddressById(address.getId());
        if (null == addressDb) {
            ExceptionsBuilder.launchException(result, this.getClass().getName(), "The indicated address isn´t exists.");
            return null;
        }
        addressDb.setAddress(address.getAddress());
        return addressRepository.save(addressDb);
    }
}
