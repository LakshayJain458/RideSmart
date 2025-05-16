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

    @PostMapping("/book")
    public ResponseEntity<?> bookRide(@RequestBody RideBookingRequest bookingRequest) {
       RideProfile profile=rideService.sendRequest(bookingRequest);
    if(profile!=null)
    {
        return new ResponseEntity<>(profile,HttpStatus.OK);
    }
    else {
        return new ResponseEntity<>("Something wents wrong",HttpStatus.NOT_ACCEPTABLE);
    }
    }
}


