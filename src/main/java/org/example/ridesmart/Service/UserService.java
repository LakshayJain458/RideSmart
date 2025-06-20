package org.example.ridesmart.Service;

import org.example.ridesmart.DTO.SignUp;
import org.example.ridesmart.DTO.UserDTO;
import org.example.ridesmart.Entity.UserDetails;
import org.example.ridesmart.Exception.UserAlreadyExistsException;
import org.example.ridesmart.Exception.UserNotFoundException;
import org.example.ridesmart.Repositary.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public UserDetails sign(SignUp sign) {
        if (userRepo.existsByEmail(sign.getEmail())) {
            throw new UserAlreadyExistsException("User already exists with email: " + sign.getEmail());
        }
        UserDetails userDetails = new UserDetails();
        userDetails.setEmail(sign.getEmail());
        userDetails.setPhoneNumber(sign.getPhoneNumber());
        userDetails.setPassword(passwordEncoder.encode(sign.getPassword()));
        return userRepo.save(userDetails);
    }

    public UserDetails add(UserDTO userDTO) {
        UserDetails user = userRepo.findById(userDTO.getId())
                .orElseThrow(() -> new UserNotFoundException("User not found with ID: " + userDTO.getId()));
        user.setName(user.getName());
        return userRepo.save(user);
    }
}
