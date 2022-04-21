package com.hero.models;

import java.util.Objects;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import java.math.BigDecimal;

public class Location {
	// a Location has an id, name, description, address, latitude and longitude
    private int locationId;
    
    @NotBlank(message = "Name cannot be empty.")
    @Size(max = 30, message = "Name cannot be more than 30 characters.")
    private String name;
    
    @NotBlank(message = "Description cannot be empty.")
    @Size(max = 200, message = "Description cannot be more than 200 characters.")
    private String description;
    
    @NotBlank(message = "Address cannot be empty.")
    @Size(max = 50, message = "Address cannot be more than 50 characters.")
    private String address;
    
    @NotBlank(message = "City cannot be empty.")
    @Size(max = 30, message = "City cannot be more than 30 characters.")
    private String city;
    
    @NotBlank(message = "State cannot be empty.")
    @Size(max = 2, message = "State cannot be more than 2 characters. (e.g., TX, OK, etc.).")
    private String state;
    
    @Size(max = 5, message = "ZIP code cannot be more than 5 characters.")
    private String zip;
    
    @NotNull(message = "Latitude cannot be empty.")
    private BigDecimal latitude;
    
    @NotNull(message = "Longitude cannot be empty.")
    private BigDecimal longitude;

	public int getLocationId() {
		return locationId;
	}
	
	public void setLocationId(int locationId) {
		this.locationId = locationId;
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
	
	public BigDecimal getLatitude() {
		return latitude;
	}
	
	public void setLatitude(BigDecimal latitude) {
		this.latitude = latitude;
	}
	
    public BigDecimal getLongitude() {
		return longitude;
	}

	public void setLongitude(BigDecimal longitude) {
		this.longitude = longitude;
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
        
        final Location other = (Location) obj;
        if (this.locationId != other.locationId) {
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
        
        if (!Objects.equals(this.city, other.city)) {
            return false;
        }
        
        if (!Objects.equals(this.state, other.state)) {
            return false;
        }
        
        if (!Objects.equals(this.zip, other.zip)) {
            return false;
        }
        
        if (!Objects.equals(this.latitude, other.latitude)) {
            return false;
        }
        
        if (!Objects.equals(this.longitude, other.longitude)) {
            return false;
        }
        
        return true;
    }
}
