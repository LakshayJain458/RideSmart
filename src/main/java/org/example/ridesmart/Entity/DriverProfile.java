package org.example.ridesmart.Entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class DriverProfile {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String name;
    private String email;
    private String phoneNumber;
    private String password;
    private String location;

    private Boolean active;

    @OneToOne
    @JoinColumn(name = "user_id")
    private UserDetails user;

    private String licenseNumber;
    private String vehicleType;
    private String carPlateNumber;
}
