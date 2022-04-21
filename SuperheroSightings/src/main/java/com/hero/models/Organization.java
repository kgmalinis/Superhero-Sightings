package com.hero.models;

import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Organization {
	// an Organization has an id, name, description, address, and contact info
	private int orgId;
	
	@NotBlank(message = "Organization name cannot be empty.")
	@Size(max = 50, message = "Name must be less than 50 characters.")
	private String name;
	
	@NotBlank(message = "Description cannot be empty.")
	@Size(max = 200, message = "Description must be less than 200 characters.")
	private String description;
	
	@NotBlank(message = "Address cannot be empty.")
	@Size(max = 50, message = "Address must be less than 50 characters.")
	private String address;
	
	@NotBlank(message = "City cannot be empty.")
	@Size(max = 30, message = "City must be less than 30 characters.")
    private String city;
	
	@NotBlank(message = "State cannot be empty.")
    @Size(max = 2, message = "State cannot be more than 2 characters. Please use the standard postal abbreviations (e.g., MN, WI, etc.).")
    private String state;
   
	@NotBlank(message = "Zip code cannot be empty.")
    @Size(max = 5, message = "Zip code cannot be more than 5 characters. Please use a standard 5 digit ZIP code, or leave this field blank.")
    private String zip;
    
	@NotBlank(message = "Contact number cannot be blank.")
    @Size(max = 12, message = "Contact number cannot be more than 12 characters.")
	private String contact;

	public int getOrgId() {
		return orgId;
	}
	
	public void setOrgId(int orgId) {
		this.orgId = orgId;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getDescription() {
		return description;
	}
	
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getAddress() {
		return address;
	}
	
	public void setAddress(String address) {
		this.address = address;
	}
	
	public String getCity() {
		return city;
	}

	public void setCity(String city) {
		this.city = city;
	}

	public String getState() {
		return state;
	}

	public void setState(String state) {
		this.state = state;
	}
	
	public String getZip() {
		return zip;
	}

	public void setZip(String zip) {
		this.zip = zip;
	}
	
	public String getContact() {
		return contact;
	}
	
	public void setContact(String contact) {
		this.contact = contact;
	}
	
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        
        if (obj == null) {
            return false;
        }
        
        if (getClass() != obj.getClass()) {
            return false;
        }
        
        final Organization other = (Organization) obj;
        if (this.orgId != other.orgId) {
            return false;
        }
        
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        
        if (!Objects.equals(this.address, other.address)) {
            return false;
        }
        
        if (!Objects.equals(this.contact, other.contact)) {
            return false;
        }
        
        return true;
    }
}
