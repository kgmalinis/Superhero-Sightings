package com.hero.dao;

import java.util.List;

import com.hero.models.Organization;

public interface OrganizationDao {
	
	/**
	 * 
	 * 
	 * @param org
	 * @return
	 */
	public Organization addOrganization(Organization org);
	
	/**
	 * 
	 * 
	 * @param orgId
	 * @return
	 */
	public Organization getOrganizationById(int orgId);
	
	/**
	 * 
	 * 
	 * @param orgId
	 * @return
	 */
	public List<Organization> getOrganizations(int orgId);
	
	/**
	 * 
	 * 
	 * @param org
	 */
	public void updateOrganization(Organization org);
	
	/**
	 * 
	 * 
	 * @param id
	 */
	public void deleteOrganization(int id);
	
	/**
	 * 
	 * 
	 * @return
	 */
	List<Organization> getOrganizations();
}
