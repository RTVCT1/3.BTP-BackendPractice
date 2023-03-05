package com.btp.backend.services;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.btp.backend.entities.Vendor;

public interface IVendorPersistence extends JpaRepository<Vendor,String>{
	
	List<Vendor> findByCompanyName(String companyName);
	
	@Query(nativeQuery=true, value="select * from public.vendor where lower(GST_NO) like %?1%")
	List<Vendor> lookVendorByGST(String GSTNo);
	
}
