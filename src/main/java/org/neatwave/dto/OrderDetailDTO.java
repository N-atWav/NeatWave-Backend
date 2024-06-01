package org.neatwave.dto;

import lombok.Data;

@Data
public class OrderDetailDTO {
    private Long orderDetailId;
    private Long orderId;
    private Long serviceId;
    private Integer quantity;
    private Double price;
}
