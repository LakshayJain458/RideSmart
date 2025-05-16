package org.example.ridesmart.Service;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.ridesmart.DTO.RideBookingRequest;
import org.example.ridesmart.Entity.RideProfile;
import org.example.ridesmart.Enum.RideStatus;
import org.example.ridesmart.Repositary.RideRepo;
import org.example.ridesmart.Repositary.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;

@Service
public class RideService {

    @Autowired
    private RideRepo rideRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.maps.api.key}")
    private String apiKey;

    public RideProfile sendRequest(RideBookingRequest bookingRequest) {
        RideProfile profile = new RideProfile();

        String pickup = URLEncoder.encode(bookingRequest.getPickuplocation(), StandardCharsets.UTF_8);
        String drop = URLEncoder.encode(bookingRequest.getDropLocation(), StandardCharsets.UTF_8);
        String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
                pickup + "&destinations=" + drop + "&key=" + apiKey;

        ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
        JsonNode body = response.getBody();

        int distanceInMeters = body.at("/rows/0/elements/0/distance/value").asInt();
        int durationInSeconds = body.at("/rows/0/elements/0/duration/value").asInt();

        double km = distanceInMeters / 1000.0;
        long durationMinutes = durationInSeconds / 60;
        long cost = (long)(50 + (km * 10));

        profile.setPickuplocation(bookingRequest.getPickuplocation());
        profile.setDropLocation(bookingRequest.getDropLocation());
        profile.setKm((int) km);
        profile.setRideDuration(durationMinutes);
        profile.setCost(cost);
        profile.setRideTime(LocalDateTime.now());
        profile.setStatus(RideStatus.IN_PROGRESS);
        profile.setUser(userRepo.findById(bookingRequest.getUserId()).orElseThrow());

        return rideRepo.save(profile);
    }
}
