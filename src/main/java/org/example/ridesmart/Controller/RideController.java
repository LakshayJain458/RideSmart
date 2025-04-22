package org.example.ridesmart.Controller;

import org.example.ridesmart.DTO.RideBookingRequest;
import org.example.ridesmart.Entity.DriverProfile;
import org.example.ridesmart.Entity.RideProfile;
import org.example.ridesmart.Entity.UserDetails;
import org.example.ridesmart.Enum.RideStatus;
import org.example.ridesmart.Repositary.DriverRepo;
import org.example.ridesmart.Repositary.UserRepo;
import org.example.ridesmart.Service.RideService;
import org.example.ridesmart.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/Ride")
public class RideController {

    @Autowired
    private RideService rideService;

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private DriverRepo driverRepo;


    @PostMapping("/book")
    public ResponseEntity<?> bookRide(@RequestBody RideBookingRequest bookingRequest) {
        UserDetails user = userRepo.findById(bookingRequest.getUserId()).orElseThrow(() -> new RuntimeException("User not found"));
        DriverProfile driver = driverRepo.findById(bookingRequest.getDriverId()).orElseThrow(() -> new RuntimeException("Driver not found"));

        // Create the ride object
        RideProfile ride = new RideProfile();
        ride.setRider(user);
        ride.setDriver(driver);
        ride.setPickupLocation(bookingRequest.getPickupLocation());
        ride.setDropoffLocation(bookingRequest.getDropoffLocation());
        ride.setStatus(RideStatus.REQUESTED); // Initial status

        // Save the ride to the database
        RideProfile createdRide = rideService.createRide(ride);

        return new ResponseEntity<>(createdRide, HttpStatus.CREATED);
    }
}


