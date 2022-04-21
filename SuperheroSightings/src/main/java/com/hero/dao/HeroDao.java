package com.hero.dao;

import java.util.List;

import com.hero.models.Hero;
import com.hero.models.Location;
import com.hero.models.Organization;

public interface HeroDao {
	
	/**
	 * adds a Hero object to our database
	 * 
	 * @param hero
	 * @return
	 */
	public Hero addHero(Hero hero);
	
	/**
	 * retrieves a Hero specified by an id
	 * 
	 * @param heroId - the Hero ID of the Hero whose information we want to retrieve
	 * @return the Hero associated with the affiliated id
	 */
	public Hero getHeroById(int heroId);
	
	/**
	 * retrieves a list of Heroes in our database
	 * 
	 * @return a list of Heroes whose information is represented in the database
	 */
	public List<Hero> getHeroes();
	
	/**
	 * updates a hero in the database
	 * 
	 * @param hero - the Hero object we want to update
	 */
	public void updateHero(Hero hero);
	
	/**
	 * deletes a Hero specified by the hero's ID
	 * 
	 * @param heroId - the Hero id of the Hero we want to delete
	 */
	public void deleteHero(int heroId);
	
	/**
	 * retrieves a list of Heroes at a particular location
	 * 
	 * @param locationId - the id of the Location we want to view
	 * @return a list of Heroes seen at that Location
	 */
	public List<Hero> getHeroesByLocation(int locationId);
	
	/**
	 * retrieves a list of Locations for a particular Hero specified by their id
	 * 
	 * @param heroId - the id of the Hero we want to view
	 * @return a list of Locations for that Hero
	 */
	public List<Location> getLocationsForHero(int heroId);
	
	/**
	 * retrieves a list of Heroes for a particular organization specified by an id
	 * 
	 * @param orgId - the id of the Organization we want to view
	 * @return a list of Heroes for that given organization
	 */
	public List<Hero> getHeroesFromOrganization(int orgId);
	
	/**
	 * retrieves a list of Organizations for a particular Hero object
	 * @param hero - the Hero object we want to view
	 * @return a list of Organizations for that Hero
	 */
	public List<Organization> getOrganizationsForHero(Hero hero);
	
	/**
	 * retrieves a list of Heroes for a particular sighting specified by an id
	 * 
	 * @param sightingId - the id of the Sighting we want to view
	 * @return a list of Heroes affiliated with that Sighting
	 */
	public List<Hero> getHeroesFromSighting(int sightingId);
}
