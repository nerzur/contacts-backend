package com.nerzur.demos.contacts.service;

import com.nerzur.demos.contacts.entity.Address;

import java.util.List;

public interface AddressService {

    public List<Address> listAllAddress();
    public Address findAddressById(Long id);
    public boolean findIfAddressExists(String address);
    public Address createaddress(Address address);
    public Address deleteaddress(Long id);
    public Address updateaddress(Address address);
}
