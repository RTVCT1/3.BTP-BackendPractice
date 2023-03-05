package com.btp.backend.controller;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.btp.backend.services.VendorService;
import com.btp.backend.entities.Vendor;

@RestController
public class VendorController {

	@Autowired
	VendorService vendorService;
	
	@RequestMapping("/vendor")
	public List<Vendor> getVendors(){
		return vendorService.readAllVendors();
	}
	
	@PostMapping("/vendor")
	public Vendor createVendor(@RequestBody Vendor vendorObj) {
		return vendorService.createVendor(vendorObj);
	}
	
	@RequestMapping(method=RequestMethod.PUT,value="/vendor")
	public Vendor updateVendor(@RequestBody Vendor vendorObj) {
		return vendorService.updateVendor(vendorObj);
	}
	
	@RequestMapping(method=RequestMethod.DELETE,value="/vendor/{id}")
	public String removeVendor(@PathVariable("id") String vendorId) {
		return vendorService.deleteVendor(vendorId);
	}
	
	@RequestMapping("/vendor/{vendorId}")
	public Vendor getVendorById(@PathVariable("vendorId") String Id) {
		Optional<Vendor> myVendor = vendorService.getSingleVendor(Id);
		if(!myVendor.isPresent()) {
			return new Vendor("","","","","","","","",null);
		}
		return myVendor.get();
	}
//	localhost:8080/vendor/search?company=RaviTeja
	@RequestMapping("/vendor/search")
	public List<Vendor> searchByCompany(@RequestParam String companyName){
		return vendorService.searchByCompanyName(companyName);
	}
	
	@RequestMapping("/vendor/lookup/{gstNo}")
	public List<Vendor> searchByGST(@PathVariable("gstNo") String gstNo){
		return vendorService.lookupVendorByGST(gstNo);
	}
}
