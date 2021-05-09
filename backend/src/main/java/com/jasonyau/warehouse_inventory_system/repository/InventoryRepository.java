package com.jasonyau.warehouse_inventory_system.repository;

import com.jasonyau.warehouse_inventory_system.models.Inventory;
import com.jasonyau.warehouse_inventory_system.models.InventoryId;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface InventoryRepository extends JpaRepository<Inventory, InventoryId> {

    @Query("select x from Inventory x where " +
            "x.productId like %?1% or " +
            "x.product.nameEn like %?1% or " +
            "x.product.nameHk like %?1% or " +
            "x.product.nameCn like %?1%")
    List<Inventory> findAllByWordContaining(String search);
}
