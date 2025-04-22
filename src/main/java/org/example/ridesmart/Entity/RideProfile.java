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
    private Long id;

    @OneToOne
    @JoinColumn(name = "rider_id")
    private UserDetails rider;

    @OneToOne
    @JoinColumn(name = "driver_id")
    private DriverProfile driver;

    private String pickupLocation;
    private String dropoffLocation;

    private double distanceKm;
    private double estimatedFare;
    private LocalDateTime rideTime;

    @Enumerated(EnumType.STRING)
    private RideStatus status;

    private LocalDateTime createdAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public UserDetails getRider() {
        return rider;
    }

    public void setRider(UserDetails rider) {
        this.rider = rider;
    }

    public DriverProfile getDriver() {
        return driver;
    }

    public void setDriver(DriverProfile driver) {
        this.driver = driver;
    }

    public String getPickupLocation() {
        return pickupLocation;
    }

    public void setPickupLocation(String pickupLocation) {
        this.pickupLocation = pickupLocation;
    }

    public String getDropoffLocation() {
        return dropoffLocation;
    }

    public void setDropoffLocation(String dropoffLocation) {
        this.dropoffLocation = dropoffLocation;
    }

    public double getDistanceKm() {
        return distanceKm;
    }

    public void setDistanceKm(double distanceKm) {
        this.distanceKm = distanceKm;
    }

    public double getEstimatedFare() {
        return estimatedFare;
    }

    public void setEstimatedFare(double estimatedFare) {
        this.estimatedFare = estimatedFare;
    }

    public LocalDateTime getRideTime() {
        return rideTime;
    }

    public void setRideTime(LocalDateTime rideTime) {
        this.rideTime = rideTime;
    }

    public RideStatus getStatus() {
        return status;
    }

    public void setStatus(RideStatus status) {
        this.status = status;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
}
