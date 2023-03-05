package com.btp.backend.services;

import org.springframework.data.jpa.repository.JpaRepository;

import com.btp.backend.entities.Address;

public interface IAddressPersistence extends JpaRepository<Address,String>{

}
