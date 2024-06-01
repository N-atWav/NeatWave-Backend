package org.neatwave.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.util.Date;

@Data
public class EmployeeDTO {
    private Long employeeId;

    @NotBlank(message = "First name is mandatory")
    @Size(max = 50, message = "First name cannot be longer than 50 characters")
    private String firstName;

    @NotBlank(message = "Last name is mandatory")
    @Size(max = 50, message = "Last name cannot be longer than 50 characters")
    private String lastName;

    @NotBlank(message = "Position is mandatory")
    @Size(max = 100, message = "Position cannot be longer than 100 characters")
    private String position;

    @NotBlank(message = "Email is mandatory")
    @Email(message = "Email should be valid")
    private String email;

    @NotBlank(message = "Phone is mandatory")
    @Size(max = 15, message = "Phone cannot be longer than 15 characters")
    private String phone;

    @PastOrPresent(message = "Hire date cannot be in the future")
    private Date hireDate;
}
