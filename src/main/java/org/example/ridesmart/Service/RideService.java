package org.example.ridesmart.Service;

import org.example.ridesmart.Entity.RideProfile;
import org.example.ridesmart.Repositary.RideRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class RideService {

    @Autowired
    private RideRepo rideRepo;
        public RideProfile createRide(RideProfile ride) {
            // Logic to save the ride in the database
            return rideRepo.save(ride);
        }
    }

