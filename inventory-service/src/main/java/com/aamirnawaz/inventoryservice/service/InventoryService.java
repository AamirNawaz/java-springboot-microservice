package com.aamirnawaz.inventoryservice.service;

import com.aamirnawaz.inventoryservice.dto.InventoryRequest;
import com.aamirnawaz.inventoryservice.dto.InventoryResponse;
import com.aamirnawaz.inventoryservice.model.Inventory;
import com.aamirnawaz.inventoryservice.repository.InventoryRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class InventoryService {
    private final InventoryRepository inventoryRepository;

    @Transactional
    public boolean isInStock(String skuCode) {
        Optional<Inventory> inventory = inventoryRepository.findBySkuCode(skuCode);
        return inventory.get().getQuantity() > 0;
    }

    public InventoryResponse addInventory(InventoryRequest inventoryRequest) {
        Inventory inventory = new Inventory();
        inventory.setSkuCode(inventoryRequest.getSkuCode());
        inventory.setQuantity(inventoryRequest.getQuantity());
        Inventory savedInventory = inventoryRepository.save(inventory);
        return new InventoryResponse(savedInventory);

    }

    public InventoryResponse updateInventory(Long id, InventoryRequest inventoryRequest) {
        Optional<Inventory> inventoryData = inventoryRepository.findById(id);
        if (inventoryData.isEmpty()) {
            throw new IllegalStateException("Inventory with given id {" + id + "} not found!");
        }
        Inventory inventoryResult = inventoryData.get();
        inventoryResult.setQuantity(inventoryRequest.getQuantity());
        inventoryResult.setSkuCode(inventoryRequest.getSkuCode());
        inventoryRepository.save(inventoryResult);
        return new InventoryResponse(inventoryResult);
    }

    public String deleteInventory(Long id) {
        Optional<Inventory> inventoryResult = inventoryRepository.findById(id);
        if (inventoryResult.isPresent()) {
            inventoryRepository.deleteById(id);
            return "Inventory with given Id {" + id + "}deleted successfully!";
        }
        return "Inventory with given Id {" + id + "} not found!";
    }

    public List<InventoryResponse> getAllInventories() {
        List<Inventory> inventories = inventoryRepository.findAll();
        return inventories.stream()
                .map(this::mapToDto)
                .toList();
    }

    private InventoryResponse mapToDto(Inventory inventory) {
        return new InventoryResponse(inventory);
    }

    @Transactional
    public List<InventoryResponse> isExistsInStock(List<String> skuCodes) {
        return inventoryRepository.findBySkuCodeIn(skuCodes).stream()
                .map(inventory ->
                        InventoryResponse.builder()
                                .id(inventory.getId())
                                .skuCode(inventory.getSkuCode())
                                .isInStock(inventory.getQuantity() > 0)
                                .quantity(inventory.getQuantity())
                                .build()
                ).toList();
    }
}
