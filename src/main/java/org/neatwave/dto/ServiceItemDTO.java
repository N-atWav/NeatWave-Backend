package org.neatwave.dto;

import lombok.Data;

@Data
public class ServiceItemDTO {
    private Long serviceId;
    private String serviceName;
    private String description;
    private Double price;
}
