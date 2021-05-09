package com.jasonyau.warehouse_inventory_system.repository;

import com.jasonyau.warehouse_inventory_system.models.Location;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<Location, String> {

}