package com.aamirnawaz.inventoryservice.controller;

import com.aamirnawaz.inventoryservice.dto.InventoryRequest;
import com.aamirnawaz.inventoryservice.dto.InventoryResponse;
import com.aamirnawaz.inventoryservice.service.InventoryService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin(origins = "*")
@RestController
@RequestMapping("/api/inventory")
@RequiredArgsConstructor
public class InventoryController {

    private final InventoryService inventoryService;

    @PostMapping("/add-inventory")
    @ResponseStatus(HttpStatus.CREATED)
    public InventoryResponse addInventoryItems(@RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.addInventory(inventoryRequest);

    }

    @PutMapping("/update-inventory/{id}")
    @ResponseStatus(HttpStatus.OK)
    public InventoryResponse updateInventory(@PathVariable Long id, @RequestBody InventoryRequest inventoryRequest) {
        return inventoryService.updateInventory(id, inventoryRequest);
    }

    @DeleteMapping("delete-inventory/{id}")
    public String deleteInventory(@PathVariable Long id) {
        return inventoryService.deleteInventory(id);
    }

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> getAllInventories() {
        return inventoryService.getAllInventories();
    }

    @GetMapping("/isInStock/{skuCode}")
    @ResponseStatus(HttpStatus.OK)
    public boolean isInStock(@PathVariable String skuCode) {
        return inventoryService.isInStock(skuCode);
    }

    @GetMapping("/isExistsInStock/")
    @ResponseStatus(HttpStatus.OK)
    public List<InventoryResponse> isExistsInStock(@RequestParam List<String> skuCodes) {
        return inventoryService.isExistsInStock(skuCodes);
    }
}
