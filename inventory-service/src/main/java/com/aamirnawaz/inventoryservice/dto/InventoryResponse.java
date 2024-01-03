package com.aamirnawaz.inventoryservice.dto;

import com.aamirnawaz.inventoryservice.model.Inventory;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

@Data
@Getter
@Setter
public class InventoryResponse {
    private Long id;
    private String skuCode;
    private Integer quantity;

    public InventoryResponse(Inventory inventory) {
        this.id = inventory.getId();
        this.skuCode = inventory.getSkuCode();
        this.quantity = inventory.getQuantity();
    }

}

