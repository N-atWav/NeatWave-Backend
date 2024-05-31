package org.neatwave.controllers;

import org.neatwave.dto.ServiceItemDTO;
import org.neatwave.services.ServiceItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/services")
public class ServiceItemController {
    @Autowired
    private ServiceItemService serviceItemService;

    @GetMapping
    public List<ServiceItemDTO> getAllServiceItems() {
        return serviceItemService.getAllServiceItems();
    }

    @GetMapping("/{id}")
    public ResponseEntity<ServiceItemDTO> getServiceItemById(@PathVariable Long id) {
        return serviceItemService.getServiceItemById(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ServiceItemDTO createServiceItem(@RequestBody ServiceItemDTO serviceItemDTO) {
        return serviceItemService.createServiceItem(serviceItemDTO);
    }

    @PutMapping("/{id}")
    public ResponseEntity<ServiceItemDTO> updateServiceItem(@PathVariable Long id, @RequestBody ServiceItemDTO serviceItemDetails) {
        return ResponseEntity.ok(serviceItemService.updateServiceItem(id, serviceItemDetails));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteServiceItem(@PathVariable Long id) {
        serviceItemService.deleteServiceItem(id);
        return ResponseEntity.noContent().build();
    }
}
