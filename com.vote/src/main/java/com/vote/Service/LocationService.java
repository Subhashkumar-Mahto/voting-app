package com.vote.Service;

import org.springframework.stereotype.Service;

import com.vote.Entity.Location;
@Service
public class LocationService {

	private static final double EARTH_RADIUS = 6371; // Earth's radius in kilometers

    public double calculateDistance(Location location1, Location location2) {
    	Location location11 = new Location(19.0330, 73.0297);  // Navi Mumbai
    	Location location21 = new Location(19.0760, 72.8777);  // Mumbai 
        double lat1 = Math.toRadians(location11.getLatitude());
        double lon1 = Math.toRadians(location11.getLongitude());
        double lat2 = Math.toRadians(location21.getLatitude());
        double lon2 = Math.toRadians(location21.getLongitude());

        double dLat = lat2 - lat1;
        double dLon = lon2 - lon1;

        System.out.println("In service: "+lat1);
        System.out.println("In service: "+lon1);
        System.out.println("In service: "+lat2);
        System.out.println("In service: "+lon2);
        double a = Math.sin(dLat / 2) * Math.sin(dLat / 2) +
                   Math.cos(lat1) * Math.cos(lat2) *
                   Math.sin(dLon / 2) * Math.sin(dLon / 2);

        double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1 - a));

        return EARTH_RADIUS * c; // Distance in kilometers
    }
}
