package org.example.ridesmart.Service;

import org.example.ridesmart.DTO.DriverDTO;
import org.example.ridesmart.DTO.SignUp;
import org.example.ridesmart.Entity.DriverProfile;
import org.example.ridesmart.Exception.UserAlreadyExistsException;
import org.example.ridesmart.Exception.UserNotFoundException;
import org.example.ridesmart.Repositary.DriverRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class DriverService {

    @Autowired
    private DriverRepo driverRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;


    public DriverProfile sign(SignUp sign) {
        if (driverRepo.existsByEmail(sign.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + sign.getEmail());
        }
        DriverProfile userDetails = new DriverProfile();
        userDetails.setEmail(sign.getEmail());
        userDetails.setPhoneNumber(sign.getPhoneNumber());
        userDetails.setPassword(passwordEncoder.encode(sign.getPassword()));
        return driverRepo.save(userDetails);
    }

    public DriverProfile add(DriverDTO userDTO) {
        DriverProfile user = driverRepo.findById(userDTO.getId())
                .orElseThrow(() -> new UserNotFoundException("Driver not found with ID: " + userDTO.getId()));

        user.setName(userDTO.getName());
        user.setLocation(userDTO.getLocation());
        user.setPhoneNumber(userDTO.getPhoneNumber());
        user.setLicenseNumber(userDTO.getLicenseNumber());
        user.setVehicleType(userDTO.getVehicleType());
        user.setCarPlateNumber(userDTO.getCarPlateNumber());
        user.setActive(userDTO.getActive());
        return driverRepo.save(user);
    }

}

