package com.aamirnawaz.inventoryservice.dto;

import com.aamirnawaz.inventoryservice.model.Inventory;
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

    public InventoryResponse(Inventory inventory) {
        this.id = inventory.getId();
        this.skuCode = inventory.getSkuCode();
        this.quantity = inventory.getQuantity();
    }

}

