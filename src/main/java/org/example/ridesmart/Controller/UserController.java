package org.example.ridesmart.Controller;

import org.example.ridesmart.DTO.LoginDto;
import org.example.ridesmart.DTO.SignUp;
import org.example.ridesmart.DTO.UserDTO;
import org.example.ridesmart.Entity.UserDetails;
import org.example.ridesmart.Service.JWT.JwtService;
import org.example.ridesmart.Service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Collections;

@RestController
@RequestMapping("/User")
public class UserController {
    @Autowired
    AuthenticationManager authenticationManager;
    @Autowired
    private JwtService jwtService;

        @Autowired
        private UserService userService;
        @PostMapping("/SignUp")
        public ResponseEntity<?> Signup(@RequestBody SignUp sign)
        {
            UserDetails userDetails=userService.sign(sign);
            if(userDetails!=null)
            {
                return new ResponseEntity<>(userDetails,HttpStatus.OK);
            }
            else {
                return new ResponseEntity<>("Something went wrong",HttpStatus.NOT_FOUND);
            }
        }
    @PostMapping("/Login")
    public ResponseEntity<?> login(@RequestBody LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginDto.getEmail(), loginDto.getPassword())
        );

        if (authentication.isAuthenticated()) {
            String token = jwtService.generateToken(loginDto.getEmail());
            return ResponseEntity.ok(Collections.singletonMap("token", token));
        } else {
            throw new UsernameNotFoundException("Invalid credentials");
        }
    }

    @PostMapping("/profile")
       public ResponseEntity<?> addDetails(@RequestBody UserDTO user)
       {
              UserDetails u=userService.add(user);
              if(u!=null)
              {
                   return  new ResponseEntity<>(u,HttpStatus.ACCEPTED);
              }
              else {
                     return new ResponseEntity<>("Something wents wrong",HttpStatus.NOT_ACCEPTABLE);
              }
       }

}
