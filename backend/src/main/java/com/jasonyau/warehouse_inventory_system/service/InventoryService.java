package com.jasonyau.warehouse_inventory_system.service;

import com.jasonyau.warehouse_inventory_system.models.Inventory;
import com.jasonyau.warehouse_inventory_system.models.InventoryId;
import com.jasonyau.warehouse_inventory_system.models.Product;
import com.jasonyau.warehouse_inventory_system.repository.InventoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
@Transactional
public class InventoryService {
    @Autowired
    private InventoryRepository InventoryRepository;

    public List<Inventory> listAllInventoryLike(String search) {
        return InventoryRepository.findAllByWordContaining(search);
    }

    public List<Inventory> listAllInventory() {
        return InventoryRepository.findAll();
    }

    public void saveInventory(Inventory user) {
        InventoryRepository.save(user);
    }

    public void saveAllInventories(List<Inventory> inventories) {
        List<Inventory> valid = new ArrayList<>();
        List<Inventory> invalid = new ArrayList<>();
        for (Inventory inventory : inventories) {
            if (inventory.getQuantity() > 0)
                valid.add(inventory);
            else
                invalid.add(inventory);
        }

        InventoryRepository.deleteAll(invalid);
        InventoryRepository.saveAll(valid);
    }

    public Inventory getInventory(InventoryId id) {
        return InventoryRepository.findById(id).get();
    }

    public void deleteInventory(InventoryId id) {
        InventoryRepository.deleteById(id);
    }
}
