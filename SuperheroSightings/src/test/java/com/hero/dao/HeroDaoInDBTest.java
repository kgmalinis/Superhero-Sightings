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
public class HeroDaoInDBTest {
	
	@Autowired
	HeroDao heroDao;
	
    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    SightingDao sightingDao;
	
	public HeroDaoInDBTest() {
	}
	
	@Test
	/**
	 * Test of addHero method from heroDao
	 * 
	 */
	public void testAddHero() {
        Hero testHero = new Hero();
        testHero.setName("testName");
        testHero.setDescription("testDescription");
        testHero.setSuperpower("testPower");

        Organization testOrg = new Organization();
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("Test City");
        testOrg.setState("TX");
        testOrg.setContact("123-456-7890");
        orgDao.addOrganization(testOrg);

        List<Organization> orgs = orgDao.getOrganizations();
        testHero.setOrganizations(orgs);

        Sighting testSighting = new Sighting();
        testSighting.setName("testSighting");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);

        Location testLoc = new Location();
        testLoc.setName("testLocation");
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
        sightingDao.addSighting(testSighting);
        List<Sighting> sightings = sightingDao.getSightings();
        testHero.setSightings(sightings);

        testHero = heroDao.addHero(testHero);
        Hero fromDao = heroDao.getHeroById(testHero.getHeroId());
        
        // ensure that the object was added
        assertEquals(testHero, fromDao);
	}
	
	/**
     * Test of getHeroes method from heroDao
     */
    @Test
    public void testGetHeroes() {
        Hero testHero = new Hero();
        testHero.setHeroId(testHero.getHeroId());
        testHero.setName("testName");
        testHero.setDescription("testDescription");
        testHero.setSuperpower("testPowers");

        Organization testOrg = new Organization();
        testOrg.setOrgId(testOrg.getOrgId());
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("testCity");
        testOrg.setState("TX");
        testOrg.setZip("98765");
        testOrg.setContact("123-456-7890");
        orgDao.addOrganization(testOrg);

        List<Organization> orgs = orgDao.getOrganizations();
        testHero.setOrganizations(orgs);

        Sighting testSighting = new Sighting();
        testSighting.setSightingId(testSighting.getSightingId());
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);

        Location testLoc = new Location();
        testLoc.setLocationId(testLoc.getLocationId());
        testLoc.setName("testName");
        testLoc.setDescription("testDescription");
        testLoc.setAddress("testAddress");
        testLoc.setCity("testCity");
        testLoc.setState("TX");
        testLoc.setZip("98765");
        BigDecimal testLatitude = new BigDecimal("45.000000");
        BigDecimal testLongitude = new BigDecimal("100.000000");
        testLoc.setLatitude(testLatitude);
        testLoc.setLongitude(testLongitude);
        testLoc = locationDao.addLocation(testLoc);

        testSighting.setLocation(testLoc);
        sightingDao.addSighting(testSighting);
        List<Sighting> sightings = sightingDao.getSightings();
        testHero.setSightings(sightings);

        testHero = heroDao.addHero(testHero);
        Hero testHero2 = new Hero();

        testHero2.setName("testName2");
        testHero2.setDescription("testDescription2");
        testHero2.setSuperpower("testSuperpowers2");
        testHero2.setOrganizations(orgs);
        testHero2.setSightings(sightings);

        testHero2 = heroDao.addHero(testHero2);
        List<Hero> heroes = heroDao.getHeroes();

        // ensure that our list contains the objects we added
        assertTrue(heroes.contains(testHero));
        assertTrue(heroes.contains(testHero2));
    }
    
    /**
     * Test of updateHero method from heroDao
     */
    @Test
    public void testUpdateHero() {
        Hero testHero = new Hero();
        testHero.setName("testName");
        testHero.setDescription("testDescription");
        testHero.setSuperpower("testPowers");

        Organization testOrg = new Organization();
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("testCity");
        testOrg.setState("TX");
        testOrg.setZip("98765");
        testOrg.setContact("123-456-7890");
        testOrg = orgDao.addOrganization(testOrg);

        List<Organization> orgs = new ArrayList<>();
        orgs.add(testOrg);
        testHero.setOrganizations(orgs);

        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);

        Location testLoc = new Location();
        testLoc.setName("testName");
        testLoc.setDescription("testDesc");
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

        List<Sighting> sightings = new ArrayList<>();
        sightings.add(testSighting);
        testHero.setSightings(sightings);
        testHero = heroDao.addHero(testHero);

        testHero.setName("EDITED");
        Location testLoc2 = new Location();
        testLoc2.setName("testName2");
        testLoc2.setDescription("testDesc2");
        testLoc2.setAddress("testAddress2");
        testLoc2.setCity("testCity2");
        testLoc2.setState("TX");
        testLoc2.setZip("12345");
        testLoc2.setLatitude(testLatitude);
        testLoc2.setLongitude(testLongitude);
        testLoc2 = locationDao.addLocation(testLoc2);

        Sighting testSighting2 = new Sighting();
        testSighting2.setName("testName2");
        testSighting2.setDescription("testDescription2");
        testSighting2.setDateOfSighting(LocalDate.EPOCH);
        testSighting2.setLocation(testLoc2);
        testSighting2 = sightingDao.addSighting(testSighting2);
        sightings.add(testSighting2);
        testHero.setSightings(sightings);

        Organization testOrg2 = new Organization();
        testOrg2.setName("testName2");
        testOrg2.setDescription("testDescription2");
        testOrg2.setAddress("testAddress2");
        testOrg2.setCity("testCity2");
        testOrg2.setState("TX");
        testOrg2.setZip("12345");
        testOrg2.setContact("123-456-7890");
        testOrg2 = orgDao.addOrganization(testOrg2);

        orgs.add(testOrg2);
        testHero.setOrganizations(orgs);
        heroDao.updateHero(testHero);
        Hero edited = heroDao.getHeroById(testHero.getHeroId());
        
        // ensure that the edit went through
        assertEquals("EDITED", edited.getName());
    }
    
    @Test
    /**
     * Test of deleteHero method from heroDao
     * 
     */
    public void testDeleteHero() {
    	Hero testHero = new Hero();
        testHero.setName("testName");
        testHero.setDescription("testDescription");
        testHero.setSuperpower("testPowers");

        Organization testOrg = new Organization();
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("testCity");
        testOrg.setState("TX");
        testOrg.setZip("12345");
        testOrg.setContact("123-456-7890");
        orgDao.addOrganization(testOrg);

        List<Organization> orgs = orgDao.getOrganizations();
        testHero.setOrganizations(orgs);
        Sighting testSighting = new Sighting();
        testSighting.setName("testName");
        testSighting.setDescription("testDescription");
        testSighting.setDateOfSighting(LocalDate.EPOCH);

        Location testLoc = new Location();
        testLoc.setName("testName");
        testLoc.setDescription("testDesc");
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

        sightingDao.addSighting(testSighting);

        List<Sighting> sightings = sightingDao.getSightings();
        testHero.setSightings(sightings);
        testHero = heroDao.addHero(testHero);
        heroDao.deleteHero(testHero.getHeroId());
        Hero fromDao = heroDao.getHeroById(testHero.getHeroId());
        
        // ensure that the object was deleted
        assertNull(fromDao);
    }
}
