package org.example.ridesmart.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String location;

    private Boolean active;

    @OneToOne(mappedBy = "user", cascade = CascadeType.ALL)
    private DriverProfile driverProfile;

    @OneToOne(mappedBy = "rider", cascade = CascadeType.ALL)
    private RideProfile rideProfile;

    private LocalDateTime createdAt;
    private LocalDateTime updatedAt;

}
