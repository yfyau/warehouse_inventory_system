package com.jasonyau.warehouse_inventory_system.repository;

import com.jasonyau.warehouse_inventory_system.models.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, String> {

}