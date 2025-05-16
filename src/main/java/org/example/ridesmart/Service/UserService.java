package org.example.ridesmart.Service;

import org.example.ridesmart.DTO.LoginDto;
import org.example.ridesmart.DTO.SignUp;
import org.example.ridesmart.DTO.UserDTO;
import org.example.ridesmart.Entity.UserDetails;
import org.example.ridesmart.Exception.UserAlreadyExistsException;
import org.example.ridesmart.Exception.UserNotFoundException;
import org.example.ridesmart.Repositary.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    private UserRepo userRepo;

    public UserDetails sign(SignUp sign) {
        boolean check = userRepo.existsByEmail(sign.getEmail());

        if (!check) {
            UserDetails userDetails = new UserDetails();
            userDetails.setEmail(sign.getEmail());
            userDetails.setPhoneNumber(sign.getPhoneNumber());
            userDetails.setPassword(new BCryptPasswordEncoder().encode(sign.getPassword()));
            return userRepo.save(userDetails);
        } else {
            throw new UserAlreadyExistsException("User already exists with email: " + sign.getEmail());
        }
    }

    public UserDetails log(LoginDto loginDto) {
        UserDetails userDetails = userRepo.findByEmail(loginDto.getEmail());

        if (userDetails == null) {
            throw new UserNotFoundException("User not found with email: " + loginDto.getEmail());
        } else {
            return userDetails;
        }
    }

    public UserDetails add(UserDTO userDTO) {
        UserDetails user = new UserDetails();
        if(userRepo.existsById(userDTO.getId()))
        {
            user.setName(userDTO.getName());
            user.setId(userDTO.getId());
            return userRepo.save(user);
        }
        else
        {
            return null;
        }
    }
}
