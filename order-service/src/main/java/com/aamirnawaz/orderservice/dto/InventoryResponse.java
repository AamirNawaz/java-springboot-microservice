package com.aamirnawaz.orderservice.dto;

import lombok.*;

@Data
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class InventoryResponse {
    private Long id;
    private String skuCode;
    private Integer quantity;
    private Boolean isInStock;

}

