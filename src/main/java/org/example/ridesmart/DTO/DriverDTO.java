package org.example.ridesmart.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.example.ridesmart.Validation.ValidPassword;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class DriverDTO {
    @NotBlank(message = "License number is required")
    private String licenseNumber;

    @NotBlank(message = "Vehicle type is required")
    private String vehicleType;

    @NotBlank(message = "Car plate number is required")
    private String carPlateNumber;

    @NotBlank(message = "Phone number is required")
    @Pattern(regexp = "^[0-9]{10}$", message = "Phone number must be 10 digits")
    private String phoneNumber;

    @NotBlank(message = "Password is required")
    @ValidPassword
    private String password;

    @NotBlank(message = "Location is required")
    private String location;

    @NotBlank(message = "Name is required")
    private String name;

    private Long id;

    private Boolean active;
}
