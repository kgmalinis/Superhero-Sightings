package com.hero.dao;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import com.hero.models.Location;
import com.hero.models.Sighting;

@SpringBootTest
public class SightingDaoInDBTest {

	@Autowired
	SightingDao sightingDao;
	
    @Autowired
    OrganizationDao orgDao;
	
    @Autowired
    LocationDao locationDao;

    @Autowired
    HeroDao heroDao;
    
	public SightingDaoInDBTest() {
	}
	
	@Test
	/**
	 * Test of addSighting method from sightingDao 
	 * 
	 */
	public void testAddSighting() {
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);
        
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
        testSighting.setLocation(testLoc);
        
        testSighting = sightingDao.addSighting(testSighting);
        Sighting fromDao = sightingDao.getSightingById(testSighting.getSightingId());
        
        // ensure that our objects were added
        assertEquals(testSighting, fromDao);
	}
	
	@Test
	/**
	 * Test of getSightings method from sightingDao
	 * 
	 */
	public void testGetSightings() {
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);

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
        testSighting.setLocation(testLoc);
        testSighting = sightingDao.addSighting(testSighting);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setName("testName2");
        testSighting2.setDescription("testDescription2");
        testSighting2.setDateOfSighting(LocalDate.EPOCH);
        testSighting2.setLocation(testLoc);
        testSighting2 = sightingDao.addSighting(testSighting2);
        
        List<Sighting> sightings = sightingDao.getSightings();
        
        // ensure that our list contains the sightings we created
        assertTrue(sightings.contains(testSighting));
        assertTrue(sightings.contains(testSighting2));
	}
	
	@Test
	/**
	 * Test of updateSighting method from sightingDao
	 * 
	 */
	public void testUpdateSighting() {
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);
        
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
        
        testSighting.setLocation(testLoc);
        
        testSighting = sightingDao.addSighting(testSighting);
        testSighting.setName("EDITED");
        sightingDao.updateSighting(testSighting);
        Sighting edited = sightingDao.getSightingById(testSighting.getSightingId());
        
        // ensure that the edit went through
        assertEquals("EDITED", edited.getName());
	}
	
	@Test
	/**
	 * Test of deleteSighting method from sightingDao
	 * 
	 */
	public void testDeleteSighting() {
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);
        
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
        testSighting.setLocation(testLoc);
        testSighting = sightingDao.addSighting(testSighting);
        sightingDao.deleteSighting(testSighting.getSightingId());
        Sighting fromDao = sightingDao.getSightingById(testSighting.getSightingId());
        
        // ensure that the object was deleted
        assertNull(fromDao);
	}
	
	@Test
	/**
	 * Test of getSightingsByDate method from sightingDao
	 * 
	 */
	public void testGetSightingsByDate() {
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);

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
        testSighting.setLocation(testLoc);
        testSighting = sightingDao.addSighting(testSighting);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setName("testName2");
        testSighting2.setDescription("testDescription2");
        testSighting2.setDateOfSighting(LocalDate.EPOCH);
        testSighting2.setLocation(testLoc);
        testSighting2 = sightingDao.addSighting(testSighting2);
        List<Sighting> sightingsForDate = sightingDao.getSightingsByDate(LocalDate.EPOCH);
        
        // ensure that our list contains the objects
        assertTrue(sightingsForDate.contains(testSighting));
        assertTrue(sightingsForDate.contains(testSighting2));
	}
	
	@Test
	/**
	 * Test of getMostRecent method from sightingDao
	 * 
	 */
	public void testGetMostRecent() {
		
        // create a test location for our sightings
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
        
        // add 10 sightings
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.parse("2000-01-01"));
        testSighting.setLocation(testLoc);
        testSighting = sightingDao.addSighting(testSighting);
        
        Sighting testSighting2 = new Sighting();
        testSighting2.setName("testName2");
        testSighting2.setDescription("testDescription2");
        testSighting2.setDateOfSighting(LocalDate.now());
        testSighting2.setLocation(testLoc);
        testSighting2 = sightingDao.addSighting(testSighting2);

        Sighting testSighting3 = new Sighting();
        testSighting3.setName("testName3");
        testSighting3.setDescription("testDescription3");
        testSighting3.setDateOfSighting(LocalDate.now());
        testSighting3.setLocation(testLoc);
        testSighting3 = sightingDao.addSighting(testSighting3);
        
        Sighting testSighting4 = new Sighting();
        testSighting4.setName("testName4");
        testSighting4.setDescription("testDescription4");
        testSighting4.setDateOfSighting(LocalDate.now());
        testSighting4.setLocation(testLoc);
        testSighting4 = sightingDao.addSighting(testSighting4);
        
        Sighting testSighting5 = new Sighting();
        testSighting5.setName("testName5");
        testSighting5.setDescription("testDescription5");
        testSighting5.setDateOfSighting(LocalDate.now());
        testSighting5.setLocation(testLoc);
        testSighting5 = sightingDao.addSighting(testSighting5);
       
        Sighting testSighting6 = new Sighting();
        testSighting6.setName("testName6");
        testSighting6.setDescription("testDescription6");
        testSighting6.setDateOfSighting(LocalDate.now());
        testSighting6.setLocation(testLoc);
        testSighting6 = sightingDao.addSighting(testSighting6);
        
        Sighting testSighting7 = new Sighting();
        testSighting7.setName("testName7");
        testSighting7.setDescription("testDescription7");
        testSighting7.setDateOfSighting(LocalDate.now());
        testSighting7.setLocation(testLoc);
        testSighting7 = sightingDao.addSighting(testSighting7);
        
        Sighting testSighting8 = new Sighting();
        testSighting8.setName("testName8");
        testSighting8.setDescription("testDescription8");
        testSighting8.setDateOfSighting(LocalDate.now());
        testSighting8.setLocation(testLoc);
        testSighting8 = sightingDao.addSighting(testSighting8);
        
        Sighting testSighting9 = new Sighting();
        testSighting9.setName("testName9");
        testSighting9.setDescription("testDescription9");
        testSighting9.setDateOfSighting(LocalDate.now());
        testSighting9.setLocation(testLoc);
        testSighting9 = sightingDao.addSighting(testSighting9);
          
        Sighting testSighting10 = new Sighting();
        testSighting10.setName("testName10");
        testSighting10.setDescription("testDescription10");
        testSighting10.setDateOfSighting(LocalDate.now());
        testSighting10.setLocation(testLoc);
        testSighting10 = sightingDao.addSighting(testSighting10);
        
        List<Sighting> mostRecent = sightingDao.getMostRecent();
        
        // ensure that we are retrieving the 10 sightings
        assertEquals(10, mostRecent.size());
	}
}
