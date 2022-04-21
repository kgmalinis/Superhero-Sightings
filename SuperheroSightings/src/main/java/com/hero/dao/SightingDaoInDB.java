package com.hero.dao;

import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.hero.dao.LocationDaoInDB.LocationMapper;
import com.hero.models.Location;
import com.hero.models.Sighting;

@Repository
public class SightingDaoInDB implements SightingDao {
	
	@Autowired
	JdbcTemplate jdbc;

	@Override
	@Transactional
	public Sighting addSighting(Sighting sighting) {
		final String ADD_SIGHTING = "INSERT INTO Sighting(name, description, dateOfSighting, locationId) VALUES(?,?,?,?)";
		jdbc.update(ADD_SIGHTING, 
				sighting.getName(),
				sighting.getDescription(),
				Date.valueOf(sighting.getDateOfSighting()),
				sighting.getLocation().getLocationId());
		
		int newSightingId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		sighting.setSightingId(newSightingId);
		return sighting;
	}
	
    private Location addLocationToSighting(Sighting sighting) {
        final String ADD_LOCATION_TO_SIGHTING = "SELECT Location.* FROM Location "
                + "JOIN Sighting ON Location.locationId = Sighting.locationId WHERE Sighting.sightingId = ?";
        return jdbc.queryForObject(ADD_LOCATION_TO_SIGHTING, new LocationMapper(), sighting.getSightingId());
    }

	@Override
	public Sighting getSightingById(int sightingId) {
        try {
            final String SELECT_SIGHTING_BY_ID = "SELECT * FROM Sighting WHERE sightingId = ?";
            Sighting s = jdbc.queryForObject(SELECT_SIGHTING_BY_ID, new SightingMapper(), sightingId);
            
            s.setLocation(addLocationToSighting(s));
            return s;
        } catch (DataAccessException ex) {
            return null;
        }
	}

	@Override
	public List<Sighting> getSightings() {
        final String SELECT_ALL_SIGHTINGS = "SELECT * FROM Sighting";
        List<Sighting> sightings = jdbc.query(SELECT_ALL_SIGHTINGS, new SightingMapper());
        
        for (Sighting s : sightings) {
            s.setLocation(addLocationToSighting(s));
        }
        return sightings;
	}

	@Override
	public void updateSighting(Sighting sighting) {
		final String UPDATE_SIGHTING = "UPDATE Sighting SET name = ?, description = ?, dateOfSighting = ? , locationId = ? WHERE sightingId = ?";
		jdbc.update(UPDATE_SIGHTING, 
				sighting.getName(),
				sighting.getDescription(), 
				Date.valueOf(sighting.getDateOfSighting()),
                sighting.getLocation().getLocationId(),
                sighting.getSightingId());
	}

	@Override
	@Transactional
	public void deleteSighting(int sightingId) {
		
		// first, delete from Hero_Sighting linking table
		final String DELETE_FROM_HERO_SIGHTING = "DELETE Hero_Sighting.* FROM Hero_Sighting "
                + "JOIN Sighting ON Hero_Sighting.sightingId = Sighting.sightingId WHERE Sighting.sightingId = ?";
		jdbc.update(DELETE_FROM_HERO_SIGHTING, sightingId);
		
		// finally, delete from Sighting table
		final String DELETE_FROM_SIGHTING = "DELETE FROM Sighting WHERE sightingId = ?";
		jdbc.update(DELETE_FROM_SIGHTING, sightingId);
	}

	@Override
	public List<Sighting> getSightingsByDate(LocalDate date) {
        final String SELECT_SIGHTINGS_BY_DATE = "SELECT * FROM Sighting WHERE dateOfSighting = ?";
        List<Sighting> sightings = jdbc.query(SELECT_SIGHTINGS_BY_DATE, new SightingMapper(), date);
        
        for (Sighting s : sightings) {
            s.setLocation(addLocationToSighting(s));
        }
        return sightings;
	}

	@Override
	public List<Sighting> getMostRecent() {
        final String SELECT_MOST_RECENT = "SELECT * FROM Sighting ORDER BY dateOfSighting DESC LIMIT 10";
        
        List<Sighting> sightings = jdbc.query(SELECT_MOST_RECENT, new SightingMapper());
        
        for (Sighting s : sightings) {
            s.setLocation(addLocationToSighting(s));
        }
        return sightings;
	}

	// SightingMapper allows the database data to turn into an Organization object
	public static final class SightingMapper implements RowMapper<Sighting> {

		@Override
		public Sighting mapRow(ResultSet rs, int rowNum) throws SQLException {
			Sighting sighting = new Sighting();
			sighting.setSightingId(rs.getInt("sightingId"));
			sighting.setName(rs.getString("name"));
			sighting.setDescription(rs.getString("description"));
			sighting.setDateOfSighting(rs.getDate("dateOfSighting").toLocalDate());
			return sighting;
		}
	}
}
