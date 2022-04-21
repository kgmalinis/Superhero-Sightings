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
public class OrganizationDaoInDBTest {

	@Autowired
	HeroDao heroDao;
	
    @Autowired
    LocationDao locationDao;

    @Autowired
    OrganizationDao orgDao;

    @Autowired
    SightingDao sightingDao;
	
	public OrganizationDaoInDBTest() {
	}
	
	@Test
	/**
	 * Test of addOrganization method from orgDao
	 * 
	 */
    public void testAddOrganization() {
        Organization testOrg = new Organization();
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("testCity");
        testOrg.setState("TX");
        testOrg.setZip("12345");
        testOrg.setContact("123-456-7890");
        testOrg = orgDao.addOrganization(testOrg);
        Organization fromDao = orgDao.getOrganizationById(testOrg.getOrgId());
        
        // ensure they are the same Organization object
        assertEquals(testOrg, fromDao);
    }

    /**
     * Test of getOrganizations method from orgDao
     */
    @Test
    public void testGetOrganizations() {
        Organization testOrg = new Organization();
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("testCity");
        testOrg.setState("TX");
        testOrg.setZip("12345");
        testOrg.setContact("123-456-7890");
        testOrg = orgDao.addOrganization(testOrg);
        Organization testOrg2 = new Organization();
        testOrg2.setName("testName2");
        testOrg2.setDescription("testDescription2");
        testOrg2.setAddress("testAddress2");
        testOrg2.setCity("testCity2");
        testOrg2.setState("TX");
        testOrg2.setZip("98765");
        testOrg2.setContact("987-654-3210");
        
        testOrg2 = orgDao.addOrganization(testOrg2);
        List<Organization> orgs = orgDao.getOrganizations();
        
        // ensure that our list contains the organizations we have added
        assertTrue(orgs.contains(testOrg));
        assertTrue(orgs.contains(testOrg2));
    }

    /**
     * Test of updateOrganization method from orgDao
     */
    @Test
    public void testUpdateOrganization() {
        Organization testOrg = new Organization();
        testOrg.setName("testName");
        testOrg.setDescription("testDescription");
        testOrg.setAddress("testAddress");
        testOrg.setCity("testCity");
        testOrg.setState("TX");
        testOrg.setZip("12345");
        testOrg.setContact("123-456-7890");
        testOrg = orgDao.addOrganization(testOrg);
        testOrg.setName("EDITED");
        orgDao.updateOrganization(testOrg);
        
        Organization editedOrg = orgDao.getOrganizationById(testOrg.getOrgId());
        
        // ensure that the update goes through
        assertEquals("EDITED", editedOrg.getName());
    }

    /**
     * Test of deleteOrganization method from orgDao
     */
    @Test
    public void testDeleteOrganization() {
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
        
        orgDao.deleteOrganization(testOrg.getOrgId());
        
        // test that we are deleting from Hero_Org bridge table
        testHero = heroDao.getHeroById(testHero.getHeroId());
        assertTrue(testHero.getOrganizations().isEmpty());
        
        //test that we are deleting from Organization table
        Organization fromDao = orgDao.getOrganizationById(testOrg.getOrgId());
        assertNull(fromDao);
    }
}
