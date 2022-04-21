package com.hero.controllers;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hero.dao.HeroDao;
import com.hero.dao.LocationDao;
import com.hero.dao.OrganizationDao;
import com.hero.dao.SightingDao;
import com.hero.models.Hero;
import com.hero.models.Organization;

@Controller
public class OrganizationController {

	@Autowired
	HeroDao heroDao;
	
	@Autowired
	LocationDao locationDao;
	
	@Autowired
	OrganizationDao orgDao;
	
	@Autowired
	SightingDao sightingDao;
	
	@GetMapping("organizations")
    public String displayOrganizations(Model model) {
        List<Organization> orgs = orgDao.getOrganizations();
        model.addAttribute("organizations", orgs);
        
        Organization blankOrg = new Organization();
        model.addAttribute("organization", blankOrg);
        return "organizations";
    }

    @PostMapping("addOrganization")
    public String addOrganization(@Valid Organization organization, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Organization> orgs = orgDao.getOrganizations();
            model.addAttribute("organizations", orgs);
            return "organizations";
        }
        
        orgDao.addOrganization(organization);
        return "redirect:/organizations";
    }

    @GetMapping("deleteOrganization")
    public String deleteOrganization(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        orgDao.deleteOrganization(id);
        return "redirect:/organizations";
    }

    @GetMapping("editOrganization")
    public String editOrganization(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Organization org = orgDao.getOrganizationById(id);
        model.addAttribute("organization", org);
        
        return "editOrganization";
    }

    @PostMapping("editOrganization")
    public String performEditOrganization(@Valid Organization org, BindingResult result) {
        if (result.hasErrors()) {
            return "editOrganization";
        }
        
        orgDao.updateOrganization(org);
        return "redirect:/organizations";
    }

    @GetMapping("organizationDetails")
    public String organizationDetail(Integer id, Model model) {
        Organization org = orgDao.getOrganizationById(id);
        model.addAttribute("organization", org);
        
        List<Hero> heroesForOrg = heroDao.getHeroesFromOrganization(id);
        model.addAttribute("heroes", heroesForOrg);
        return "organizationDetails";
    }
}
