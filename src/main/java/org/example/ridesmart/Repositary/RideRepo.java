package org.example.ridesmart.Repositary;

import org.example.ridesmart.Entity.RideProfile;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RideRepo extends JpaRepository<RideProfile,Long> {
}
