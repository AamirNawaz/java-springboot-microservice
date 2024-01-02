package com.aamirnawaz.orderservice.service;

import com.aamirnawaz.orderservice.dto.OrderItemsDto;
import com.aamirnawaz.orderservice.dto.OrderRequest;
import com.aamirnawaz.orderservice.model.Order;
import com.aamirnawaz.orderservice.model.OrderItems;
import com.aamirnawaz.orderservice.repository.OrderRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository orderRepository;

    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItems> orderItems = orderRequest.getOrderItemsDtoList().stream().map(this::mapToDto).toList();
        order.setOrderItemsList(orderItems);
        orderRepository.save(order);
        System.out.println("Placing order in service");
    }

    public Order trackOrder(String orderNumber) {
        return orderRepository.findByOrderNumber(orderNumber);
    }

    private OrderItems mapToDto(OrderItemsDto orderItemsDto) {
        OrderItems orderItems = new OrderItems();
        orderItems.setSkuCode(orderItemsDto.getSkuCode());
        orderItems.setPrice(orderItemsDto.getPrice());
        orderItems.setQuantity(orderItemsDto.getQuantity());
        return orderItems;
    }

    public List<Order> getAllOrders() {
        return orderRepository.findAll();
    }
    
}
