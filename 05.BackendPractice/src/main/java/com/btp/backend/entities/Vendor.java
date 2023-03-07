package com.btp.backend.entities;

import java.util.ArrayList;
import java.util.List;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;
import javax.persistence.Table;

@Entity
@Table(name="VENDOR")
public class Vendor {

	@Id
	@Column(nullable=false,name="ID")
	@GeneratedValue(generator="uuid2")
	@GenericGenerator(name="uuid2",strategy="org.hibernate.id.UUIDGenerator")
	private String id;
	
	@Column(nullable=false,name="COMPANY_NAME")
	private String companyName;
	
	@Column(nullable=false,name="FIRST_NAME")
	private String firstName;
	
	@Column(nullable=false,name="LAST_NAME")
	private String lastName;
	
	@Column(nullable=false,name="WEBSITE")
	private String website;
	
	@Column(nullable=false,name="EMAIL_ID")
	private String email;
	
	@Column(nullable=false,name="STATUS")
	private String status;
	
	@Column(nullable=false,name="GST_NO")
	private String gstNo;
	
	@OneToMany(fetch = FetchType.LAZY, cascade=CascadeType.ALL)
	@JoinColumn(name="vendor",referencedColumnName="ID")
	private List<Address> addresses= new ArrayList<Address>();
	
	public Vendor() {
		
	}

	public Vendor(String id, String companyName, String firstName, String lastName, String website, String email,
			String status, String gstNo, List<Address> addresses) {
		super();
		this.id = id;
		this.companyName = companyName;
		this.firstName = firstName;
		this.lastName = lastName;
		this.website = website;
		this.email = email;
		this.status = status;
		this.gstNo = gstNo;
		this.addresses = addresses;
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public String getCompanyName() {
		return companyName;
	}

	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getWebsite() {
		return website;
	}

	public void setWebsite(String website) {
		this.website = website;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getStatus() {
		return status;
	}

	public void setStatus(String status) {
		this.status = status;
	}

	public String getGstNo() {
		return gstNo;
	}

	public void setGstNo(String gstNo) {
		this.gstNo = gstNo;
	}

	public List<Address> getAddresses() {
		return addresses;
	}

	public void setAddresses(List<Address> addresses) {
		this.addresses = addresses;
	}
	
}
