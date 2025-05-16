package org.example.ridesmart.Entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ridesmart.Enum.RideStatus;

import java.time.LocalDateTime;

@Entity
@Table(name = "ride_profile")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RideProfile {

   @Id
   @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long Id;

   private String pickuplocation;
   private String dropLocation;
   private double km;
   private int rideRating;
   private Long rideDuration;
   private LocalDateTime rideTime;
   private Long cost;
   @OneToOne
   @JoinColumn(name = "user_id")
   private UserDetails user;

   @OneToOne
   @JoinColumn(name = "driver_id")
   private DriverProfile driver;
   @Enumerated(EnumType.STRING)
   private RideStatus status;

}
