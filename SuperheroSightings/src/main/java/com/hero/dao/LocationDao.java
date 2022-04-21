package com.hero.dao;

import java.util.List;

import com.hero.models.Location;

public interface LocationDao {
	
	/**
	 * 
	 * 
	 * @param location
	 * @return
	 */
	public Location addLocation(Location location);
	
	/**
	 * 
	 * 
	 * @param locationId
	 * @return
	 */
	public Location getLocationById(int locationId);
	
	/**
	 * 
	 * 
	 * @return
	 */
	public List<Location> getLocations();
	
	/**
	 * 
	 * 
	 * @param location
	 */
	public void updateLocation(Location location);
	
	/**
	 * 
	 * 
	 * @param id
	 */
	public void deleteLocation(int id);
	
	/**
	 * 
	 * 
	 * @param heroId
	 * @return
	 */
	public List<Location> getLocationsByHeroId(int heroId);
}
