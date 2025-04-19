package org.example.ridesmart.Service.JWT;

import org.example.ridesmart.Repositary.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
@Service
public class MyUserDetailService implements UserDetailsService {

    @Autowired
    private UserRepo userRepository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // Check if the user exists by email
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
        } else {
            // If the user does not exist, throw an exception
            throw new UsernameNotFoundException("User not found");
        }
    }

}
