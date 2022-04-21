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
import com.hero.models.Location;

@Repository
public class LocationDaoInDB implements LocationDao {
	
	@Autowired
	JdbcTemplate jdbc;

	@Override
	@Transactional
	public Location addLocation(Location location) {
		final String INSERT_LOCATION = "INSERT INTO Location(name, description, address, city, state, zip, latitude, longitude) VALUES(?,?,?,?,?,?,?,?)";
		jdbc.update(INSERT_LOCATION,
				location.getName(),
				location.getDescription(),
				location.getAddress(),
				location.getCity(),
				location.getState(),
				location.getZip(),
				location.getLatitude(),
				location.getLongitude());
		
		int newLocationId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		location.setLocationId(newLocationId);
		return location;
	}

	@Override
	public Location getLocationById(int locationId) {
		try {
			final String SELECT_GAME_BY_ID = "SELECT * From Location WHERE locationId = ?";
			return jdbc.queryForObject(SELECT_GAME_BY_ID, new LocationMapper(), locationId);
		} catch(DataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Location> getLocations() {
		final String SELECT_LOCATIONS = "SELECT * FROM Location";
		return jdbc.query(SELECT_LOCATIONS, new LocationMapper());
	}

	@Override
	public void updateLocation(Location location) {
		final String UPDATE_LOCATION = "UPDATE Location SET name = ?, description = ?, address = ? , city = ?, state = ?, zip = ?, latitude = ?, longitude = ? WHERE locationId = ?";
		jdbc.update(UPDATE_LOCATION, 
				location.getName(),
				location.getDescription(), 
				location.getAddress(),
				location.getCity(),
				location.getState(),
				location.getZip(),
				location.getLatitude(),
				location.getLongitude(),
				location.getLocationId());
	}

	@Override
	@Transactional
	public void deleteLocation(int locationId) {
		
        // delete from Hero_Sighting
        final String DELETE_HERO_SIGHTING_BY_SIGHTING = "DELETE Hero_Sighting.* FROM Hero_Sighting "
                + "JOIN Sighting ON Hero_Sighting.sightingId = Sighting.sightingId WHERE Sighting.locationId = ?";
        jdbc.update(DELETE_HERO_SIGHTING_BY_SIGHTING, locationId);
        
        // delete from Sighting
        final String DELETE_SIGHTING_BY_LOCATION = "DELETE FROM Sighting WHERE locationId = ?";
        jdbc.update(DELETE_SIGHTING_BY_LOCATION, locationId);
        
        // delete from Location
        final String DELETE_LOCATION = "DELETE FROM Location WHERE locationId = ?";
        jdbc.update(DELETE_LOCATION, locationId);
	}
	
	@Override
	public List<Location> getLocationsByHeroId(int heroId) {
        final String SELECT_LOCATIONS_BY_HERO = "SELECT * FROM Location "
                + "JOIN Sighting ON Sighting.locationId = Location.locationId "
                + "JOIN Hero_Sighting ON Sighting.sightingId = Hero_Sighting.sightingId WHERE heroId = ?";
        return jdbc.query(SELECT_LOCATIONS_BY_HERO, new LocationMapper(), heroId);
	}
	
	// LocationMapper allows the database data to turn into a Location object
	public static final class LocationMapper implements RowMapper<Location> {

		@Override
		public Location mapRow(ResultSet rs, int rowNum) throws SQLException {
			Location location = new Location();
			location.setLocationId(rs.getInt("locationId"));
			location.setName(rs.getString("name"));
			location.setDescription(rs.getString("description"));
			location.setAddress(rs.getString("address"));
			location.setCity(rs.getString("city"));
			location.setState(rs.getString("state"));
			location.setZip(rs.getString("zip"));
			location.setLatitude(rs.getBigDecimal("latitude"));
			location.setLongitude(rs.getBigDecimal("longitude"));
			return location;
		}
	}
}
