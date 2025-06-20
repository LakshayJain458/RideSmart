package org.example.ridesmart.Controller;

import jakarta.validation.Valid;
import org.example.ridesmart.DTO.DriverDTO;
import org.example.ridesmart.DTO.LoginDto;
import org.example.ridesmart.DTO.SignUp;
import org.example.ridesmart.Entity.DriverProfile;
import org.example.ridesmart.Service.DriverService;
import org.example.ridesmart.Service.JWT.JwtService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/Driver")
public class DriverController {

    @Autowired
    AuthenticationManager authenticationManager;

    @Autowired
    private JwtService jwtService;

    @Autowired
    private DriverService driverService;

    @PostMapping("/SignUp")
    public ResponseEntity<?> Signup(@Valid @RequestBody SignUp sign) {
        DriverProfile userDetails = driverService.sign(sign);
        if (userDetails != null) {
            return new ResponseEntity<>(userDetails, HttpStatus.OK);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "Something went wrong"), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );
        String token = jwtService.generateToken(loginDto.getEmail());
        Map<String, Object> response = new HashMap<>();
        response.put("status", "success");
        response.put("token", token);
        return ResponseEntity.ok(response);
    }

    @PostMapping("/profile")
    public ResponseEntity<?> addDetails(@Valid @RequestBody DriverDTO user) {
        DriverProfile u = driverService.add(user);
        if (u != null) {
            return new ResponseEntity<>(u, HttpStatus.CREATED);
        } else {
            return new ResponseEntity<>(Collections.singletonMap("error", "Something went wrong"), HttpStatus.NOT_ACCEPTABLE);
        }
    }
}
