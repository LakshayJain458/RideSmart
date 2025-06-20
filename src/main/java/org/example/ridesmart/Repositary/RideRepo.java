package org.example.ridesmart.Repositary;

import org.example.ridesmart.Entity.RideProfile;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface RideRepo extends JpaRepository<RideProfile, Long> {

    List<RideProfile> findByUserIdOrderByRideTimeDesc(Long userId);

    List<RideProfile> findByDriverIdOrderByRideTimeDesc(Long driverId);

}
