package com.vote.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.vote.Entity.Location;
import com.vote.Service.LocationService;

@RestController
@RequestMapping("/api")
public class LocationController {


	@Autowired
    private LocationService locationService;

    @GetMapping("/distance")
    public double calculateDistance(
            @RequestParam double lat1,
            @RequestParam double lon1,
            @RequestParam double lat2,
            @RequestParam double lon2) {

        Location location1 = new Location(lat1, lon1);
        Location location2 = new Location(lat2, lon2);
        System.out.println("lat1----"+lat1);
        System.out.println("lon1----"+lon1);
        System.out.println("lat2----"+lat2);
        System.out.println("lon2----"+lon2);

        return locationService.calculateDistance(location1, location2);
    }
    
}
