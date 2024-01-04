package com.aamirnawaz.orderservice.service;

import com.aamirnawaz.orderservice.dto.InventoryResponse;
import com.aamirnawaz.orderservice.dto.OrderItemsDto;
import com.aamirnawaz.orderservice.dto.OrderRequest;
import com.aamirnawaz.orderservice.model.Order;
import com.aamirnawaz.orderservice.model.OrderItems;
import com.aamirnawaz.orderservice.repository.OrderRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional
public class OrderService {
    private final OrderRepository orderRepository;
    private final WebClient webClient;


    public void placeOrder(OrderRequest orderRequest) {
        Order order = new Order();
        order.setOrderNumber(UUID.randomUUID().toString());
        List<OrderItems> orderItems = orderRequest.getOrderItemsDtoList().stream().map(this::mapToDto).toList();
        order.setOrderItemsList(orderItems);

        //checking stock in inventory before placing order
        List skuCodesList = order.getOrderItemsList().stream()
                .map(OrderItems::getSkuCode)
                .toList();

        InventoryResponse[] inventoryResponses = webClient.get()
                .uri("http://localhost:8092/api/inventory/isExistsInStock/",
                        uriBuilder -> uriBuilder.queryParam("skuCodes", skuCodesList).build())
                .retrieve()
                .bodyToMono(InventoryResponse[].class)
                .block();

        boolean allProductInStock = Arrays.stream(inventoryResponses)
                .allMatch(InventoryResponse::getIsInStock);

        if (allProductInStock) {
            orderRepository.save(order);
            System.out.println("Placing order in service");
        } else {
            throw new IllegalArgumentException("Product is not in stock");
        }
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

    public String deleteOrderById(Long orderId) {
        Optional<Order> orderResult = orderRepository.findById(orderId);
        if (orderResult.isPresent()) {
            orderRepository.deleteById(orderId);
            return "Order with given Id {" + orderId + "}deleted successfully!";
        }
        return "Order with given Id {" + orderId + "} not found!";

    }
}
