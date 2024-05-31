package org.neatwave.services;

import org.neatwave.dto.ServiceItemDTO;
import org.neatwave.models.ServiceItem;
import org.neatwave.repositories.ServiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ServiceItemService {
    @Autowired
    private ServiceItemRepository serviceItemRepository;

    public List<ServiceItemDTO> getAllServiceItems() {
        return serviceItemRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<ServiceItemDTO> getServiceItemById(Long serviceId) {
        return serviceItemRepository.findById(serviceId)
                .map(this::convertToDTO);
    }

    public ServiceItemDTO createServiceItem(ServiceItemDTO serviceItemDTO) {
        ServiceItem serviceItem = convertToEntity(serviceItemDTO);
        ServiceItem savedServiceItem = serviceItemRepository.save(serviceItem);
        return convertToDTO(savedServiceItem);
    }

    public ServiceItemDTO updateServiceItem(Long serviceId, ServiceItemDTO serviceItemDetails) {
        ServiceItem serviceItem = serviceItemRepository.findById(serviceId)
                .orElseThrow(() -> new RuntimeException("Service item not found"));
        serviceItem.setServiceName(serviceItemDetails.getServiceName());
        serviceItem.setDescription(serviceItemDetails.getDescription());
        serviceItem.setPrice(serviceItemDetails.getPrice());
        ServiceItem updatedServiceItem = serviceItemRepository.save(serviceItem);
        return convertToDTO(updatedServiceItem);
    }

    public void deleteServiceItem(Long serviceId) {
        serviceItemRepository.deleteById(serviceId);
    }

    private ServiceItemDTO convertToDTO(ServiceItem serviceItem) {
        ServiceItemDTO serviceItemDTO = new ServiceItemDTO();
        serviceItemDTO.setServiceId(serviceItem.getServiceId());
        serviceItemDTO.setServiceName(serviceItem.getServiceName());
        serviceItemDTO.setDescription(serviceItem.getDescription());
        serviceItemDTO.setPrice(serviceItem.getPrice());
        return serviceItemDTO;
    }

    private ServiceItem convertToEntity(ServiceItemDTO serviceItemDTO) {
        ServiceItem serviceItem = new ServiceItem();
        serviceItem.setServiceId(serviceItemDTO.getServiceId());
        serviceItem.setServiceName(serviceItemDTO.getServiceName());
        serviceItem.setDescription(serviceItemDTO.getDescription());
        serviceItem.setPrice(serviceItemDTO.getPrice());
        return serviceItem;
    }
}
