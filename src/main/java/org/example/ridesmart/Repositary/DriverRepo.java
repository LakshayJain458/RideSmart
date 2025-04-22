package org.example.ridesmart.Repositary;

import org.example.ridesmart.Entity.DriverProfile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface DriverRepo extends JpaRepository<DriverProfile,Long> {
    boolean existsByEmail(String email);

    DriverProfile findByEmail(String email);
}
