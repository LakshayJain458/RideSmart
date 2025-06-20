package org.example.ridesmart.Controller;

import jakarta.validation.Valid;
import org.example.ridesmart.DTO.RideBookingRequest;
import org.example.ridesmart.Entity.RideProfile;
import org.example.ridesmart.Service.RideService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Collections;

@RestController
@RequestMapping("/Ride")
public class RideController {

    @Autowired
    private RideService rideService;

    @PostMapping("/book")
    public ResponseEntity<?> bookRide(@Valid @RequestBody RideBookingRequest bookingRequest) {
        RideProfile profile = rideService.sendRequest(bookingRequest);
        if (profile != null) {
            return new ResponseEntity<>(profile, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/assign/{rideId}")
    public ResponseEntity<?> assignDriver(@PathVariable Long rideId) {
        RideProfile updatedRide = rideService.assignDriverToRide(rideId);
        return ResponseEntity.ok(updatedRide);
    }

    @PostMapping("/accept")
    public ResponseEntity<?> acceptRide(@RequestParam Long rideId, @RequestParam Long driverId) {
        RideProfile ride = rideService.acceptRide(rideId, driverId);
        return ResponseEntity.ok(ride);
    }

    @PostMapping("/cancel")
    public ResponseEntity<?> cancelRide(@RequestParam Long rideId, @RequestParam Long driverId) {
        RideProfile ride = rideService.cancelRide(rideId, driverId);
        return ResponseEntity.ok(ride);
    }

    @GetMapping("/user/history/{userId}")
    public ResponseEntity<?> userHistory(@PathVariable Long userId) {
        return ResponseEntity.ok(rideService.getUserHistory(userId));
    }

    @GetMapping("/driver/history/{driverId}")
    public ResponseEntity<?> driverHistory(@PathVariable Long driverId) {
        return ResponseEntity.ok(rideService.getDriverHistory(driverId));
    }
}


