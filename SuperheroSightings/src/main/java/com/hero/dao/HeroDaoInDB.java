package com.hero.dao;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hero.dao.LocationDaoInDB.LocationMapper;
import com.hero.dao.OrganizationDaoInDB.OrganizationMapper;
import com.hero.dao.SightingDaoInDB.SightingMapper;
import com.hero.models.Hero;
import com.hero.models.Location;
import com.hero.models.Organization;
import com.hero.models.Sighting;

@Repository
public class HeroDaoInDB implements HeroDao {

	@Autowired
	JdbcTemplate jdbc;
	
	@Override
	@Transactional
	public Hero addHero(Hero hero) {
		final String ADD_HERO = "INSERT INTO Hero(name, description, superpower) VALUES(?,?,?)";
		jdbc.update(ADD_HERO, 
				hero.getName(),
				hero.getDescription(),
				hero.getSuperpower());
		
		int newHeroId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		hero.setHeroId(newHeroId);
		
        insertHeroOrganization(hero);
        insertHeroSighting(hero);
        
		return hero;
	}

	@Override
	public Hero getHeroById(int heroId) {
		try {
			final String SELECT_HERO_BY_ID = "SELECT * From Hero WHERE heroId = ?";
            Hero hero = jdbc.queryForObject(SELECT_HERO_BY_ID, new HeroMapper(), heroId);
            hero.setOrganizations(getOrganizationsForHero(hero));
            hero.setSightings(getSightingsForHero(hero));
            return hero;
		} catch(DataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Hero> getHeroes() {
		final String SELECT_HEROES = "SELECT * FROM Hero";
		
        List<Hero> heroes = jdbc.query(SELECT_HEROES, new HeroMapper());
        for (Hero h : heroes) {
            h.setOrganizations(getOrganizationsForHero(h));
            h.setSightings(getSightingsForHero(h));
        }
        return heroes;
	}

	@Override
	public void updateHero(Hero hero) {
		final String UPDATE_HERO = "UPDATE Hero SET name = ?, description = ?, superpower = ? WHERE heroId = ?";
		jdbc.update(UPDATE_HERO, 
				hero.getName(),
				hero.getDescription(), 
				hero.getSuperpower(),
				hero.getHeroId());
		
        final String DELETE_HERO_ORGANIZATION = "DELETE FROM Hero_Org WHERE heroId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION, hero.getHeroId());
        insertHeroOrganization(hero);
        
        final String DELETE_HERO_SIGHTING = "DELETE FROM Hero_Sighting WHERE heroId = ?";
        jdbc.update(DELETE_HERO_SIGHTING, hero.getHeroId());
        insertHeroSighting(hero);
	}

	@Override
	@Transactional
	public void deleteHero(int heroId) {
        
        //delete from Hero_Org
        final String DELETE_HERO_ORGANIZATION_BY_HERO = "DELETE Hero_Org.* FROM Hero_Org "
                + "JOIN Hero ON Hero_Org.heroId = Hero.heroId WHERE Hero.heroId = ?";
        jdbc.update(DELETE_HERO_ORGANIZATION_BY_HERO, heroId);
        
        //delete from hero_sighting
        final String DELETE_HERO_SIGHTING_BY_HERO = "DELETE Hero_Sighting.* FROM Hero_Sighting "
                + "JOIN Hero ON Hero_Sighting.heroId = Hero.heroId WHERE Hero.heroId = ?";
        jdbc.update(DELETE_HERO_SIGHTING_BY_HERO, heroId);
        
        //delete from hero
        final String DELETE_HERO = "DELETE FROM Hero WHERE heroId = ?";
        jdbc.update(DELETE_HERO, heroId);
	}
	
	@Override
	public List<Hero> getHeroesByLocation(int locationId) {
        final String GET_HEROES_BY_LOCATION = "SELECT Hero.* FROM Hero "
                + "JOIN Hero_Sighting ON Hero.heroId = Hero_Sighting.heroId "
                + "JOIN Sighting ON Hero_Sighting.sightingId = Sighting.sightingId "
                + "JOIN Location ON Sighting.locationId = Location.locationId WHERE Location.locationId = ?";
        
        List<Hero> heroes = jdbc.query(GET_HEROES_BY_LOCATION, new HeroMapper(), locationId);
        for (Hero h : heroes) {
            h.setOrganizations(getOrganizationsForHero(h));
            h.setSightings(getSightingsForHero(h));
        }
        return heroes;
	}
	
	private List<Sighting> getSightingsForHero(Hero hero) {
        final String SELECT_SIGHTINGS_FOR_HERO = "SELECT * FROM Sighting "
                + "JOIN Hero_Sighting ON Sighting.sightingId = Hero_Sighting.sightingId WHERE Hero_Sighting.heroId = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_FOR_HERO, new SightingMapper(), hero.getHeroId());
        for (Sighting s : sightings) {
            s.setLocation(getLocationForHeroSighting(s));
        }
        return sightings;
	}
	
    private Location getLocationForHeroSighting(Sighting s) {
        final String SELECT_LOCATION_FOR_HERO_SIGHTING = "SELECT * FROM Location "
                + "JOIN Sighting ON Sighting.locationId = Location.locationId WHERE Sighting.sightingId = ?";
        return jdbc.queryForObject(SELECT_LOCATION_FOR_HERO_SIGHTING, new LocationMapper(), s.getSightingId());
    }

	@Override
	public List<Location> getLocationsForHero(int heroId) {
        final String GET_LOCATIONS_FOR_HERO = "SELECT Location.* FROM Location "
                + "JOIN Sighting ON Sighting.locationId = Location.locationId "
                + "JOIN Hero_Sighting ON Hero_Sighting.sightingId = Sighting.sightingId "
                + "JOIN Hero ON Hero_Sighting.heroId = Hero.heroId WHERE Hero.heroId = ?";
        return jdbc.query(GET_LOCATIONS_FOR_HERO, new LocationMapper(), heroId);
	}

	@Override
	public List<Hero> getHeroesFromOrganization(int orgId) {
        final String GET_HEROES_FROM_ORG = "SELECT Hero.* FROM Hero "
                + "JOIN Hero_Org ON Hero.heroId = Hero_Org.heroId "
                + "JOIN Organization ON Hero_Org.orgId = Organization.orgId WHERE Organization.orgId = ?";
        List<Hero> heroes = jdbc.query(GET_HEROES_FROM_ORG, new HeroMapper(), orgId);
        
        for (Hero h : heroes) {
            h.setOrganizations(getOrganizationsForHero(h));
            h.setSightings(getSightingsForHero(h));
        }
        return heroes;
	}

	@Override
	public List<Organization> getOrganizationsForHero(Hero hero) {
        final String SELECT_ORGANIZATIONS_FOR_HERO = "SELECT * FROM Organization "
                + "JOIN Hero_Org ON Organization.orgId = Hero_Org.orgId WHERE Hero_Org.heroId = ?";
        return jdbc.query(SELECT_ORGANIZATIONS_FOR_HERO, new OrganizationMapper(), hero.getHeroId());
	}

	@Override
	public List<Hero> getHeroesFromSighting(int sightingId) {
        final String SELECT_ALL_HEROES_FOR_SIGHTING = "SELECT * FROM Hero "
                + "JOIN Hero_Sighting ON Hero.heroId = Hero_Sighting.heroId WHERE Hero_Sighting.sightingId = ?";
        
        List<Hero> heroes = jdbc.query(SELECT_ALL_HEROES_FOR_SIGHTING, new HeroMapper(), sightingId);
       
        for (Hero h : heroes) {
            h.setOrganizations(getOrganizationsForHero(h));
            h.setSightings(getSightingsForHero(h));
        }
        return heroes;
	}
	
    private void insertHeroSighting(Hero hero) {
        final String INSERT_HERO_SIGHTING = "INSERT INTO hero_sighting"
                + "(heroId, sightingId) VALUES(?,?)";
        for (Sighting s : hero.getSightings()) {
            jdbc.update(INSERT_HERO_SIGHTING, hero.getHeroId(), s.getSightingId());
        }
    }
    
    private void insertHeroOrganization(Hero hero) {
        final String INSERT_HERO_ORGANIZATION = "INSERT INTO Hero_Org"
                + "(heroId, orgId) VALUES(?,?)";
        for (Organization o : hero.getOrganizations()) {
            jdbc.update(INSERT_HERO_ORGANIZATION, hero.getHeroId(), o.getOrgId());
        }
    }

	// HeroMapper allows the database data to turn into a Hero object
	public static final class HeroMapper implements RowMapper<Hero> {

		@Override
		public Hero mapRow(ResultSet rs, int rowNum) throws SQLException {
			Hero hero = new Hero();
			hero.setHeroId(rs.getInt("heroId"));
			hero.setName(rs.getString("name"));
			hero.setDescription(rs.getString("description"));
			hero.setSuperpower(rs.getString("superpower"));
			return hero;
		}
	}
}
