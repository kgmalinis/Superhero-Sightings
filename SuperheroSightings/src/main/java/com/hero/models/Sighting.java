package com.hero.models;

import java.time.LocalDate;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

import org.springframework.format.annotation.DateTimeFormat;

public class Sighting {
	private int sightingId;
	
    @NotBlank(message = "Sighting name cannot be empty.")
    @Size(max = 50, message = "Name must be less than 50 characters.")
	private String name;
    
    @NotBlank(message = "Description cannot be empty.")
    @Size(max = 200, message = "Description must be less than 200 characters.")
	private String description;
    
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @NotNull(message = "Date cannot be empty.")
	private LocalDate dateOfSighting;
	
    private Location location;

	public int getSightingId() {
		return sightingId;
	}
	
	public void setSightingId(int sightingId) {
		this.sightingId = sightingId;
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
	
	public LocalDate getDateOfSighting() {
		return dateOfSighting;
	}
	
	public void setDateOfSighting(LocalDate dateOfSighting) {
		this.dateOfSighting = dateOfSighting;
	}
	
	public Location getLocation() {
		return location;
	}

	public void setLocation(Location location) {
		this.location = location;
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
        
        final Sighting other = (Sighting) obj;
       
        if (this.sightingId != other.sightingId) {
            return false;
        }
       
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        
        if (!Objects.equals(this.dateOfSighting, other.dateOfSighting)) {
            return false;
        }
        
        if (!Objects.equals(this.location, other.location)) {
            return false;
        }
        
        return true;
    }
}
