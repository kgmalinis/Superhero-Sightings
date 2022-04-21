package com.hero.dao;

import java.time.LocalDate;
import java.util.List;

import com.hero.models.Sighting;

public interface SightingDao {
	
	/**
	 * 
	 * 
	 * @param sighting
	 * @return
	 */
	public Sighting addSighting(Sighting sighting);
	
	/**
	 * 
	 * 
	 * @param sightingId
	 * @return
	 */
    public Sighting getSightingById(int sightingId);
    
    /**
     * 
     * 
     * @return
     */
    public List<Sighting> getSightings();
    
    /**
     * 
     * 
     * @param sighting
     */
    public void updateSighting(Sighting sighting);
    
    /**
     * 
     * 
     * @param id
     */
    public void deleteSighting(int id);
    
    /**
     * 
     * 
     * @param date
     * @return
     */
    public List<Sighting> getSightingsByDate(LocalDate date);
    
    /**
     * 
     * 
     * @return
     */
    public List<Sighting> getMostRecent();
}
