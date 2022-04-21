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
import com.hero.models.Location;

@Controller
public class LocationController {
	
	@Autowired
	HeroDao heroDao;
	
	@Autowired
	LocationDao locationDao;
	
	@Autowired
	OrganizationDao orgDao;
	
	@Autowired
	SightingDao sightingDao;
	
    @GetMapping("locations")
    public String displayLocations(Model model) {
        List<Location> locs = locationDao.getLocations();
        
        model.addAttribute("locations", locs);
        Location blankLocation = new Location();
        model.addAttribute("location", blankLocation);
        return "locations";
    }

    @PostMapping("addLocation")
    public String addLocation(@Valid Location location, BindingResult result, Model model) {
        if (result.hasErrors()) {
            List<Location> locs = locationDao.getLocations();
            model.addAttribute("locations", locs);
            return "locations";
        }
        
        locationDao.addLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("deleteLocation")
    public String deleteLocation(HttpServletRequest request) {
        int id = Integer.parseInt(request.getParameter("id"));
        
        locationDao.deleteLocation(id);
        return "redirect:/locations";
    }

    @GetMapping("editLocation")
    public String editLocation(HttpServletRequest request, Model model) {
        int id = Integer.parseInt(request.getParameter("id"));
        Location location = locationDao.getLocationById(id);
        
        model.addAttribute("location", location);
        return "editLocation";
    }

    @PostMapping("editLocation")
    public String performEditLocation(@Valid Location location, BindingResult result) {
        if (result.hasErrors()) {
            return "editLocation";
        }
        
        locationDao.updateLocation(location);
        return "redirect:/locations";
    }

    @GetMapping("locationDetail")
    public String courseDetail(Integer id, Model model) {
        Location location = locationDao.getLocationById(id);
        model.addAttribute("location", location);
        
        List<Hero> heroesForLocation = heroDao.getHeroesByLocation(id);
        model.addAttribute("heroes", heroesForLocation);
        return "locationDetail";
    }
}
