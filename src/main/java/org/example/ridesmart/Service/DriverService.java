package org.example.ridesmart.Service;

import org.example.ridesmart.DTO.DriverDTO;
import org.example.ridesmart.DTO.LoginDto;
import org.example.ridesmart.DTO.SignUp;
import org.example.ridesmart.DTO.UserDTO;
import org.example.ridesmart.Entity.DriverProfile;
import org.example.ridesmart.Entity.UserDetails;
import org.example.ridesmart.Exception.UserAlreadyExistsException;
import org.example.ridesmart.Exception.UserNotFoundException;
import org.example.ridesmart.Repositary.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepo driverRepo;

    public DriverProfile sign(SignUp sign) {
        boolean check = driverRepo.existsByEmail(sign.getEmail());

        if (!check) {
            DriverProfile userDetails = new DriverProfile();
            userDetails.setEmail(sign.getEmail());
            userDetails.setPhoneNumber(sign.getPhoneNumber());
            userDetails.setPassword(new BCryptPasswordEncoder().encode(sign.getPassword()));
            return driverRepo.save(userDetails);
        } else {
            throw new UserAlreadyExistsException("User already exists with email: " + sign.getEmail());
        }
    }

    public DriverProfile add(DriverDTO userDTO) {
        DriverProfile user = new DriverProfile();
        if (driverRepo.existsById(userDTO.getId())) {
            user.setName(userDTO.getName());
            user.setLocation(userDTO.getLocation());
            user.setId(userDTO.getId());
            return driverRepo.save(user);
        } else {
            return null;
        }
    }
}
