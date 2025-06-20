package org.example.ridesmart.Repositary;

import org.example.ridesmart.Entity.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DriverRepo extends JpaRepository<DriverProfile, Long> {
    boolean existsByEmail(String email);

    DriverProfile findByEmail(String email);

    Optional<DriverProfile> findFirstByActiveTrue();

}
