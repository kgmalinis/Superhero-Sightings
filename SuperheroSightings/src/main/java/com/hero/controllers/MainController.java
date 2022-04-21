package com.hero.controllers;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.hero.dao.LocationDao;
import com.hero.dao.SightingDao;
import com.hero.models.Location;
import com.hero.models.Sighting;

@Controller
public class MainController {
    @Autowired
    LocationDao locationDao;
    
    @Autowired
    SightingDao sightingDao;
    
    @GetMapping("/")
    /**
     * home page will display general info as well as the most recent sightings 
     */
    public String displaySightings(Model model) {
        List<Sighting> sightings = sightingDao.getMostRecent();
        List<Location> locations = locationDao.getLocations();
        model.addAttribute("sightings", sightings);
        model.addAttribute("locations", locations);
        
        return "index";
    }
}
