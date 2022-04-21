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
import com.hero.models.Organization;

@Repository
public class OrganizationDaoInDB implements OrganizationDao {
	
	@Autowired
	JdbcTemplate jdbc;

	@Override
	@Transactional
	public Organization addOrganization(Organization org) {
		final String ADD_ORGANIZATION = "INSERT INTO Organization(name, description, address, city, state, zip, contact) VALUES(?,?,?,?,?,?,?)";
		jdbc.update(ADD_ORGANIZATION, 
				org.getName(),
				org.getDescription(),
				org.getAddress(),
				org.getCity(),
				org.getState(),
				org.getZip(),
				org.getContact());
		
		int newOrgId = jdbc.queryForObject("SELECT LAST_INSERT_ID()", Integer.class);
		org.setOrgId(newOrgId);
		return org;
	}

	@Override
	public Organization getOrganizationById(int orgId) {
		try {
			final String SELECT_GAME_BY_ID = "SELECT * From Organization WHERE orgId = ?";
			return jdbc.queryForObject(SELECT_GAME_BY_ID, new OrganizationMapper(), orgId);
		} catch(DataAccessException ex) {
			return null;
		}
	}

	@Override
	public List<Organization> getOrganizations() {
		final String SELECT_ORGANIZATIONS = "SELECT * FROM Organization";
		return jdbc.query(SELECT_ORGANIZATIONS, new OrganizationMapper());
	}

	@Override
	public void updateOrganization(Organization org) {
		final String UPDATE_ORG = "UPDATE Organization SET name = ?, description = ?, address = ? , city = ?, state = ?, zip = ?, contact = ? WHERE orgId = ?";
		jdbc.update(UPDATE_ORG, 
				org.getName(),
				org.getDescription(), 
				org.getAddress(),
				org.getCity(),
				org.getState(),
				org.getZip(),
				org.getContact(),
				org.getOrgId());
	}
	
	@Override
	@Transactional
	public void deleteOrganization(int orgId) {
		
		// first delete from Hero_Org linking table
		final String DELETE_FROM_HERO_ORG = "DELETE Hero_Org.* FROM Hero_Org "
                + "JOIN Organization ON Hero_Org.orgId = Organization.orgId WHERE Hero_Org.orgId = ?";
		jdbc.update(DELETE_FROM_HERO_ORG, orgId);
		
		// then, delete from Organization table
		final String DELETE_FROM_ORGANIZATION = "DELETE FROM Organization WHERE orgId = ?";
		jdbc.update(DELETE_FROM_ORGANIZATION, orgId);
	}
	
	// OrganizationMapper allows the database data to turn into an Organization object
	public static final class OrganizationMapper implements RowMapper<Organization> {

		@Override
		public Organization mapRow(ResultSet rs, int rowNum) throws SQLException {
			Organization org = new Organization();
			org.setOrgId(rs.getInt("orgId"));
			org.setName(rs.getString("name"));
			org.setDescription(rs.getString("description"));
			org.setAddress(rs.getString("address"));
			org.setCity(rs.getString("city"));
			org.setState(rs.getString("state"));
			org.setZip(rs.getString("zip"));
			org.setContact(rs.getString("contact"));
			return org;
		}
	}

	@Override
	public List<Organization> getOrganizations(int orgId) {
		// TODO Auto-generated method stub
		return null;
	}
}
