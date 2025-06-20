package org.example.ridesmart.Service.JWT;

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
    private UserRepo userRepo;

    @Autowired
    private DriverRepo driverRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        var user = userRepo.findByEmail(username);
        if (user != null) {
            return new org.springframework.security.core.userdetails.User(
                    user.getEmail(),
                    user.getPassword(),
                    new ArrayList<>()
            );
        }
        var driver = driverRepo.findByEmail(username);
        if (driver != null) {
            return new org.springframework.security.core.userdetails.User(
                    driver.getEmail(),
                    driver.getPassword(),
                    new ArrayList<>()
            );
        }
        throw new UsernameNotFoundException("User or Driver not found with email: " + username);
    }


}
