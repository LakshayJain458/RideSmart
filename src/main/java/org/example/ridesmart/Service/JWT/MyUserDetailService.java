package org.example.ridesmart.Service.JWT;

import org.example.ridesmart.Entity.DriverProfile;
import org.example.ridesmart.Repositary.DriverRepo;
import org.example.ridesmart.Repositary.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;
    @Autowired
    private DriverRepo driverRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        if (userRepository.existsByEmail(username)) {
            // Fetch the user from the repository
            org.example.ridesmart.Entity.UserDetails user = userRepository.findByEmail(username);

            // If user is found, return the user with the email and password (no roles)
            if (user == null) {
                throw new UsernameNotFoundException("User not found");
            }

            // Return UserDetails with no roles (since there's no role in your case)
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),       // Email as username
                    user.getPassword(),    // Password
                    new ArrayList<>()      // No authorities (empty list)
            );
        } else if (driverRepo.existsByEmail(username)) {
            org.example.ridesmart.Entity.DriverProfile driver = driverRepo.findByEmail(username);

            if (driver == null) {
                throw new UsernameNotFoundException("Driver not found");
            }

            return new org.springframework.security.core.userdetails.User(
                    driver.getEmail(),     // Assuming driver's email is in linked UserDetails
                    driver.getPassword(),  // Same
                    new ArrayList<>()                // No roles/authorities
            );
        }else {
            // If the user does not exist, throw an exception
            throw new UsernameNotFoundException("User not found");
        }
    }

}
