package org.example.ridesmart.Service;

import com.fasterxml.jackson.databind.JsonNode;
import org.example.ridesmart.DTO.RideBookingRequest;
import org.example.ridesmart.Entity.DriverProfile;
import org.example.ridesmart.Entity.RideProfile;
import org.example.ridesmart.Enum.RideStatus;
import org.example.ridesmart.Repositary.DriverRepo;
import org.example.ridesmart.Repositary.RideRepo;
import org.example.ridesmart.Repositary.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.time.LocalDateTime;
import java.util.List;
import java.util.logging.Logger;

@Service
public class RideService {

    @Autowired
    private RideRepo rideRepo;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private RestTemplate restTemplate;

    @Value("${google.maps.api.key}")
    private String apiKey;

    @Value("${ride.baseFare}")
    private long baseFare;

    @Value("${ride.perKmFare}")
    private long perKmFare;

    public RideProfile sendRequest(RideBookingRequest bookingRequest) {
        try {
            String pickup = URLEncoder.encode(bookingRequest.getPickuplocation(), StandardCharsets.UTF_8);
            String drop = URLEncoder.encode(bookingRequest.getDropLocation(), StandardCharsets.UTF_8);
            String url = "https://maps.googleapis.com/maps/api/distancematrix/json?origins=" +
                    pickup + "&destinations=" + drop + "&key=" + apiKey;

            ResponseEntity<JsonNode> response = restTemplate.getForEntity(url, JsonNode.class);
            JsonNode body = response.getBody();

            if (body == null) {
                throw new RuntimeException("Empty response from Distance Matrix API");
            }

            String apiStatus = body.path("status").asText();
            if (!"OK".equals(apiStatus)) {
                throw new RuntimeException("Distance Matrix API error: " + apiStatus);
            }

            String elementStatus = body.at("/rows/0/elements/0/status").asText();
            if (!"OK".equals(elementStatus)) {
                throw new RuntimeException("No valid route found: " + elementStatus);
            }

            int distanceInMeters = body.at("/rows/0/elements/0/distance/value").asInt();
            int durationInSeconds = body.at("/rows/0/elements/0/duration/value").asInt();

            double km = distanceInMeters / 1000.0;
            long durationMinutes = durationInSeconds / 60;
            BigDecimal cost = BigDecimal.valueOf(baseFare).add(BigDecimal.valueOf(km * perKmFare));

            RideProfile profile = new RideProfile();
            profile.setPickupLocation(bookingRequest.getPickuplocation());
            profile.setDropLocation(bookingRequest.getDropLocation());
            profile.setKm(km);
            profile.setRideDuration(durationMinutes);
            profile.setCost(cost);
            profile.setRideTime(LocalDateTime.now());
            profile.setStatus(RideStatus.REQUESTED);
            profile.setUser(userRepo.findById(bookingRequest.getUserId())
                    .orElseThrow(() -> new RuntimeException("User not found with ID: " + bookingRequest.getUserId())));

            System.out.println("Google API response: " + body.toPrettyString());

            return rideRepo.save(profile);

        } catch (Exception e) {
            throw new RuntimeException("Ride booking failed: " + e.getMessage(), e);
        }
    }

    public RideProfile assignDriverToRide(Long rideId) {
        RideProfile ride = rideRepo.findById(rideId)
                .orElseThrow(() -> new RuntimeException("Ride not found with ID: " + rideId));

        if (!ride.getStatus().equals(RideStatus.REQUESTED)) {
            throw new RuntimeException("Ride is not in REQUESTED state");
        }

        DriverProfile driver = driverRepo.findFirstByActiveTrue()
                .orElseThrow(() -> new RuntimeException("No active drivers available"));

        ride.setDriver(driver);
        ride.setStatus(RideStatus.ACCEPTED);
        return rideRepo.save(ride);
    }

    public RideProfile acceptRide(Long rideId, Long driverId) {
        RideProfile ride = rideRepo.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));

        if (!RideStatus.REQUESTED.equals(ride.getStatus())) {
            throw new RuntimeException("Ride is not available for acceptance");
        }

        DriverProfile driver = driverRepo.findById(driverId)
                .orElseThrow(() -> new RuntimeException("Driver not found"));

        ride.setDriver(driver);
        ride.setStatus(RideStatus.ACCEPTED);
        return rideRepo.save(ride);
    }

    public RideProfile cancelRide(Long rideId, Long driverId) {
        RideProfile ride = rideRepo.findById(rideId).orElseThrow(() -> new RuntimeException("Ride not found"));
        if (!RideStatus.ACCEPTED.equals(ride.getStatus()) || !ride.getDriver().getId().equals(driverId)) {
            throw new RuntimeException("You cannot cancel this ride");
        }
        ride.setStatus(RideStatus.CANCELLED);
        return rideRepo.save(ride);
    }

    public List<RideProfile> getUserHistory(Long userId) {

        return rideRepo.findByUserIdOrderByRideTimeDesc(userId);
    }

    public List<RideProfile> getDriverHistory(Long driverId) {
        return rideRepo.findByDriverIdOrderByRideTimeDesc(driverId);
    }
}
