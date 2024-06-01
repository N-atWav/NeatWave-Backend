package org.neatwave.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.time.LocalDate;

@Data
public class CustomerDTO {
    private Long customerId;

    @NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "First name cannot be longer than 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters")
    private String lastName;

    @NotBlank(message = "Phone is mandatory")
    @Size(max = 15, message = "Phone cannot be longer than 15 characters")
    private String phone;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Address is mandatory")
    @Size(max = 255, message = "Address cannot be longer than 255 characters")
    private String address;

    @PastOrPresent(message = "Registration date cannot be in the future")
    private LocalDate registrationDate;
}
