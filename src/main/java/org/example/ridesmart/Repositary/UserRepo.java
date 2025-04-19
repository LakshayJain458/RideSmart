package org.example.ridesmart.Repositary;

import org.example.ridesmart.Entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepo extends JpaRepository<UserDetails,Long> {
    UserDetails findByEmail(String email);

    boolean existsByEmail(String username);

    boolean existsById(Long id);
}
