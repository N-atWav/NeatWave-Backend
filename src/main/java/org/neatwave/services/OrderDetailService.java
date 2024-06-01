package org.neatwave.services;

import org.neatwave.dto.OrderDetailDTO;
import org.neatwave.models.OrderDetail;
import org.neatwave.repositories.OrderDetailRepository;
import org.neatwave.repositories.OrderRepository;
import org.neatwave.repositories.ServiceItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class OrderDetailService {
    @Autowired
    private OrderDetailRepository orderDetailRepository;

    @Autowired
    private OrderRepository orderRepository;

    @Autowired
    private ServiceItemRepository serviceItemRepository;

    public List<OrderDetailDTO> getAllOrderDetails() {
        return orderDetailRepository.findAll().stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public Optional<OrderDetailDTO> getOrderDetailById(Long orderDetailId) {
        return orderDetailRepository.findById(orderDetailId)
                .map(this::convertToDTO);
    }

    public OrderDetailDTO createOrderDetail(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = convertToEntity(orderDetailDTO);
        OrderDetail savedOrderDetail = orderDetailRepository.save(orderDetail);
        return convertToDTO(savedOrderDetail);
    }

    public OrderDetailDTO updateOrderDetail(Long orderDetailId, OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = orderDetailRepository.findById(orderDetailId)
                .orElseThrow(() -> new RuntimeException("OrderDetail not found"));

        orderDetail.setOrder(orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found")));
        orderDetail.setService(serviceItemRepository.findById(orderDetailDTO.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found")));
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPrice(orderDetailDTO.getPrice());

        OrderDetail updatedOrderDetail = orderDetailRepository.save(orderDetail);
        return convertToDTO(updatedOrderDetail);
    }

    public void deleteOrderDetail(Long orderDetailId) {
        orderDetailRepository.deleteById(orderDetailId);
    }

    private OrderDetailDTO convertToDTO(OrderDetail orderDetail) {
        OrderDetailDTO orderDetailDTO = new OrderDetailDTO();
        orderDetailDTO.setOrderDetailId(orderDetail.getOrderDetailId());
        orderDetailDTO.setOrderId(orderDetail.getOrder().getOrderId());
        orderDetailDTO.setServiceId(orderDetail.getService().getServiceId());
        orderDetailDTO.setQuantity(orderDetail.getQuantity());
        orderDetailDTO.setPrice(orderDetail.getPrice());
        return orderDetailDTO;
    }

    private OrderDetail convertToEntity(OrderDetailDTO orderDetailDTO) {
        OrderDetail orderDetail = new OrderDetail();
        orderDetail.setOrderDetailId(orderDetailDTO.getOrderDetailId());
        orderDetail.setOrder(orderRepository.findById(orderDetailDTO.getOrderId())
                .orElseThrow(() -> new RuntimeException("Order not found")));
        orderDetail.setService(serviceItemRepository.findById(orderDetailDTO.getServiceId())
                .orElseThrow(() -> new RuntimeException("Service not found")));
        orderDetail.setQuantity(orderDetailDTO.getQuantity());
        orderDetail.setPrice(orderDetailDTO.getPrice());
        return orderDetail;
    }
}
