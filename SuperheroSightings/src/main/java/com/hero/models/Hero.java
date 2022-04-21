package com.hero.models;

import java.util.List;
import java.util.Objects;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class Hero {
	
	// a Hero has an id, name, description, and superpower
	private int heroId;
	
    @NotBlank(message = "Hero name cannot be empty.")
    @Size(max = 30, message = "Name must be less than 30 characters.")
	private String name;
	
    @NotBlank(message = "Description cannot be empty.")
	@Size(max = 200, message = "Description must be less than 200 characters.")
	private String description;
	
    @NotBlank(message = "Superpowers cannot be empty.")
    @Size(max = 100, message = "Superpowers must be less than 100 characters.")
	private String superpower;
	private List<Organization> organizations;
	private List<Sighting> sightings;
	
	public int getHeroId() {
		return heroId;
	}
	
	public void setHeroId(int heroId) {
		this.heroId = heroId;
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
	
	public String getSuperpower() {
		return superpower;
	}
	
	public void setSuperpower(String superpower) {
		this.superpower = superpower;
	}
	
	public List<Organization> getOrganizations() {
		return organizations;
	}

	public void setOrganizations(List<Organization> organizations) {
		this.organizations = organizations;
	}

	public List<Sighting> getSightings() {
		return sightings;
	}

	public void setSightings(List<Sighting> sightings) {
		this.sightings = sightings;
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
        
        final Hero other = (Hero) obj;
        if (this.heroId != other.heroId) {
            return false;
        }
        
        if (!Objects.equals(this.name, other.name)) {
            return false;
        }
        
        if (!Objects.equals(this.description, other.description)) {
            return false;
        }
        
        if (!Objects.equals(this.superpower, other.superpower)) {
            return false;
        }
        
        if (!Objects.equals(this.organizations, other.organizations)) {
            return false;
        }
        
        if (!Objects.equals(this.sightings, other.sightings)) {
            return false;
        }
        return true;
    }
}
