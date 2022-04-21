package com.hero.controllers;

import java.time.LocalDate;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import com.hero.dao.HeroDao;
import com.hero.dao.LocationDao;
import com.hero.dao.OrganizationDao;
import com.hero.dao.SightingDao;
import com.hero.models.Hero;
import com.hero.models.Location;
import com.hero.models.Sighting;

@Controller
public class SightingController {

	@Autowired
	HeroDao heroDao;
	
	@Autowired
	LocationDao locationDao;
	
	@Autowired
	OrganizationDao orgDao;
	
	@Autowired
	SightingDao sightingDao;
	
	LocalDate dateOfSighting;
	
    @GetMapping("sightings")
    public String displaySightings(Model model) {
    	
        List<Sighting> sightings = sightingDao.getSightings();
        List<Location> locations = locationDao.getLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        
        Sighting blankSighting = new Sighting();
        model.addAttribute("sighting", blankSighting);
        return "sightings";
    }

    @PostMapping("addSighting")
    public String addSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) { 

        String locationId = request.getParameter("locationId");
        Location location = locationDao.getLocationById(Integer.parseInt(locationId));
        
        if (location != null) {
            sighting.setLocation(location);
        } else {
            FieldError error = new FieldError("sighting", "location", "Please select a location.");
            result.addError(error);
        }
        
        if (result.hasErrors()) {
            model.addAttribute("sightings", sightingDao.getSightings());
            model.addAttribute("locations", locationDao.getLocations());
            return "sightings";
        }
        sightingDao.addSighting(sighting);
        
        return "redirect:/sightings";
    }

    @GetMapping("deleteSighting")
    public String deleteSighting(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        sightingDao.deleteSighting(id);

        return "redirect:/sightings";
    }

    @GetMapping("editSighting")
    public String editSighting(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Sighting sighting = sightingDao.getSightingById(id);
        
        List<Location> locations = locationDao.getLocations();
        model.addAttribute("sighting", sighting);
        model.addAttribute("locations", locations);
        
        return "editSighting";
    }

    @PostMapping("editSighting")
    public String performEditSighting(@Valid Sighting sighting, BindingResult result, HttpServletRequest request, Model model) {
        String locationId = request.getParameter("locationId");
        Location location = locationDao.getLocationById(Integer.parseInt(locationId));
        
        if (location != null) {
            sighting.setLocation(location);
        } else {
            FieldError error = new FieldError("sighting", "location", "Please select a location.");
            result.addError(error);
        }
        
        if (result.hasErrors()) {
            model.addAttribute("sightings", sightingDao.getSightings());
            model.addAttribute("locations", locationDao.getLocations());
            return "editsighting";
        }
        
        sightingDao.updateSighting(sighting);
        return "redirect:/sightings";
    }

    @GetMapping("sightingDetails")
    public String sightingDetail(Integer id, Model model) {
        Sighting sighting = sightingDao.getSightingById(id);
        model.addAttribute("sighting", sighting);
        
        List<Hero> heroes = heroDao.getHeroesFromSighting(id);
        model.addAttribute("heroes", heroes);
        
        return "sightingDetails";
    }

    @PostMapping("findSightingsByDate")
    public String findSightingsByDate(HttpServletRequest request) {
        LocalDate dateInput = LocalDate.parse(request.getParameter("dateOfSighting"));
        dateOfSighting = dateInput;
        
        return "redirect:/sightingsForDate";
    }

    @GetMapping("sightingsForDate")
    public String sightingsForDate(Model model) {
        model.addAttribute("dateOfSighting", dateOfSighting);
        
        List<Sighting> sightingsForDate = sightingDao.getSightingsByDate(dateOfSighting);
        model.addAttribute("sightings", sightingsForDate);
        
        return "sightingsForDate";
    }
}
