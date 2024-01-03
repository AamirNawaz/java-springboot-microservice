package com.aamirnawaz.orderservice.controller;

import com.aamirnawaz.orderservice.dto.OrderRequest;
import com.aamirnawaz.orderservice.model.Order;
import com.aamirnawaz.orderservice.service.OrderService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/order")
@RequiredArgsConstructor
public class OrderController {
    private final OrderService orderService;

    @PostMapping("/place-order")
    public String orderPlaced(@RequestBody OrderRequest orderRequest) {
        orderService.placeOrder(orderRequest);
        return "Order placed successfully!";
    }

    @GetMapping()
    public List<Order> getAllOrders() {
        return orderService.getAllOrders();
    }

    @GetMapping("/track-order/{orderNumber}")
    public Order trackOrderByOrderNumber(@PathVariable String orderNumber) {
        return orderService.trackOrder(orderNumber);
    }

    @DeleteMapping("/{orderId}")
    public String deleteOrderById(@PathVariable Long orderId) {
        return orderService.deleteOrderById(orderId);
    }


}
