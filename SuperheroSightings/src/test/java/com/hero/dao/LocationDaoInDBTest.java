package com.hero.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hero.models.Hero;
import com.hero.models.Location;
import com.hero.models.Organization;
import com.hero.models.Sighting;

@SpringBootTest
public class LocationDaoInDBTest {

	@Autowired
	HeroDao heroDao;
	
    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    SightingDao sightingDao;
	
	public LocationDaoInDBTest() {
	}
	
	@Test
	/**
	 * Test of addLocation method from locationDao
	 * 
	 */
	public void testAddLocation() {
        Location testLoc = new Location();
        testLoc.setName("testName");
        testLoc.setDescription("testDescription");
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("TX");
        testLoc.setZip("12345");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        
        testLoc = locationDao.addLocation(testLoc);
        Location fromDao = locationDao.getLocationById(testLoc.getLocationId());
        
        // ensure that our object was added
        assertEquals(testLoc, fromDao);
	}
	
	@Test
	/**
	 * Test of getLocations method from locationDao
	 * 
	 */
	public void testGetLocations() {
        Location testLoc = new Location();
        testLoc.setName("testName");
        testLoc.setDescription("testDescription");
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("TX");
        testLoc.setZip("12345");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        testLoc = locationDao.addLocation(testLoc);
        
        Location testLoc2 = new Location();
        testLoc2.setName("testName");
        testLoc2.setDescription("testDescription");
        testLoc2.setAddress("testAddress2");
        testLoc2.setCity("testCity2");
        testLoc2.setState("TX");
        testLoc2.setZip("98765");
        testLoc2.setLatitude(testLatitude);
        testLoc2.setLongitude(testLongitude);
        testLoc2 = locationDao.addLocation(testLoc2);
        
        List<Location> locs = locationDao.getLocations();
        
        // ensure that our list contains the objects we created
        assertTrue(locs.contains(testLoc));
        assertTrue(locs.contains(testLoc2));
	}
	
	@Test
	/**
	 * Test of updateLocation method from locationDao
	 * 
	 */
	public void testUpdateLocation() {
        Location testLoc = new Location();
        testLoc.setName("testName");
        testLoc.setDescription("testDescription");
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("TX");
        testLoc.setZip("12345");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        
        testLoc = locationDao.addLocation(testLoc);
        testLoc.setAddress("EDITED");
        locationDao.updateLocation(testLoc);
        Location edited = locationDao.getLocationById(testLoc.getLocationId());
        
        // ensure that the edit went through
        assertEquals("EDITED", edited.getAddress());
	}
	
	@Test
	/**
	 * 
	 * Test of deleteLocation method from locationDao
	 */
	public void testDeleteLocation() {
		Location testLoc = new Location();
        testLoc.setName("testName");
        testLoc.setDescription("testDescription");
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("TX");
        testLoc.setZip("12345");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        testLoc = locationDao.addLocation(testLoc);
        
        //create a sighting to go with this location to test the delete more thoroughly
        Sighting s = new Sighting();
        s.setName("testName");
        s.setDescription("testDescription");
        s.setDateOfSighting(LocalDate.EPOCH);
        s.setLocation(testLoc);
        s = sightingDao.addSighting(s);
        
        //create a hero with this sighting to check that bridge table delete
        Hero h = new Hero();
        h.setName("testHero");
        h.setDescription("testDesc");
        h.setSuperpower("testPowers");
        
        List<Sighting> sightings = new ArrayList<>();
        sightings.add(s);
        h.setSightings(sightings);
        
        Organization o = new Organization();
        o.setName("testOrg");
        o.setDescription("testDesc");
        o.setAddress("testAddress");
        o.setCity("testCity");
        o.setState("TX");
        o.setZip("12345");
        o.setContact("123-456-7890");
        o = orgDao.addOrganization(o);
        
        List<Organization> orgs = new ArrayList<>();
        orgs.add(o);
        h.setOrganizations(orgs);
        h = heroDao.addHero(h);
        locationDao.deleteLocation(testLoc.getLocationId());
        s = sightingDao.getSightingById(s.getSightingId());
        h = heroDao.getHeroById(h.getHeroId());
        
        //check that the hero_sighting bridge table entry is cleared
        assertTrue(h.getSightings().isEmpty());
        
        //this next line shows that the sighting s no longer exists in the sighting table. this is the middle step of the delete
        assertNull(s);
        
        //finally check that the location itself is null
        Location fromDao = locationDao.getLocationById(testLoc.getLocationId());
        assertNull(fromDao);
	}
}
