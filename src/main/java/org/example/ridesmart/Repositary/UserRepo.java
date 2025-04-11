package org.example.ridesmart.Repositary;

import org.example.ridesmart.Entity.UserDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface UserRepo extends JpaRepository<UserDetails,Integer> {
    UserDetails findByEmail(String email);
}
