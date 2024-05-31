package org.neatwave.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import lombok.Data;

import java.time.LocalDate;

@Data
public class OrderDTO {
    private Long orderId;

    @NotNull(message = "Order date is mandatory")
    @PastOrPresent(message = "Order date cannot be in the future")
    private LocalDate orderDate;

    @NotNull(message = "Customer ID is mandatory")
    private Long customerId;

    @NotBlank(message = "Status is mandatory")
    private String status;

    @NotNull(message = "Total price is mandatory")
    @Positive(message = "Total price must be greater than zero")
    private Double totalPrice;
}
