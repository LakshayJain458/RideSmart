package org.example.ridesmart.DTO;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class DriverDTO {
    private String licenseNumber;
    private String vehicleType;
    private String carPlateNumber;
    private String phoneNumber;
    private String password;
    private String location;
    private String name;
    private Long id;

    private Boolean active;

}
