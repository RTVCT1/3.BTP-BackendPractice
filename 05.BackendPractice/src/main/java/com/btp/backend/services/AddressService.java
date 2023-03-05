package com.btp.backend.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.btp.backend.entities.Address;

@Component
public class AddressService {
	
	@Autowired
	IAddressPersistence address;
	
	public List<Address> getAddresses(){
		return address.findAll();
	}
	
	public Address createAddress(Address addressObj) {
		return address.save(addressObj);
	}
}
