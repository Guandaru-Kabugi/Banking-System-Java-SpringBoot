package com.backend.customerservice.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CustomerRequest {
    @NotBlank @Size(max=80)
    private String firstName;

    @NotBlank @Size(max=80)
    private String lastName;
    @Email @NotBlank @Size(max=254)
    private String email;

    @Pattern(regexp = "^(?:\\+254|254|0)[17]\\d{8}$", message = "Invalid Kenyan phone number format")
    private String phone;

    @NotBlank @Size(max=512)
    private String address;

    @NotBlank @Size(max=64)
    private String externalId;
}
