package com.aamirnawaz.orderservice.model;


import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Table(name = "orders")
public class Order {
    @jakarta.persistence.Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNumber;

    private Boolean orderStatus = false;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderItems> orderItemsList;
}
