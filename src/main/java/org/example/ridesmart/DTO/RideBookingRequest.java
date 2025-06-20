package org.example.ridesmart.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class RideBookingRequest {
    @NotNull(message = "User ID is required")
    private Long userId;

    @NotBlank(message = "Pickup location is required")
    private String pickuplocation;

    @NotBlank(message = "Drop location is required")
    private String dropLocation;
}
