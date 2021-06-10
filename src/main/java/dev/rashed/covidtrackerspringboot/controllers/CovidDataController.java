package dev.rashed.covidtrackerspringboot.controllers;

import dev.rashed.covidtrackerspringboot.models.LocationStats;
import dev.rashed.covidtrackerspringboot.services.CovidDataService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.List;

@Controller
public class CovidDataController {

    @Autowired
    CovidDataService covidDataService;

    @GetMapping("/")
    public String home(Model model){
        List<LocationStats> locationStatsList = covidDataService.getAllLocationStats();
        int totalActiveCase = locationStatsList.stream().mapToInt(LocationStats::getActive).sum();
        model.addAttribute("locationStats",locationStatsList);
        model.addAttribute("totalActiveCase",totalActiveCase);
        return "home";
    }
}
