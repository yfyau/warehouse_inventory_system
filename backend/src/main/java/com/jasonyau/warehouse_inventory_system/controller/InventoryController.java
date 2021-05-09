package com.jasonyau.warehouse_inventory_system.controller;

import com.jasonyau.warehouse_inventory_system.models.Inventory;
import com.jasonyau.warehouse_inventory_system.models.InventoryId;
import com.jasonyau.warehouse_inventory_system.models.Location;
import com.jasonyau.warehouse_inventory_system.service.InventoryService;
import com.jasonyau.warehouse_inventory_system.service.LocationService;
import com.jasonyau.warehouse_inventory_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.sql.Array;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@RestController
@RequestMapping("/inventory")
public class InventoryController {

    @Autowired
    LocationService LocationService;
    @Autowired
    ProductService ProductService;
    @Autowired
    InventoryService InventoryService;

    @GetMapping("")
    public List<Inventory> list(@RequestParam(name = "search", required = false) String search) {
        if (search != null)
            return InventoryService.listAllInventoryLike(search);

        return InventoryService.listAllInventory();
    }


    @PutMapping("/")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> updateMultiple(@RequestBody List<Inventory> inventories) {
        for (Inventory inventory : inventories) {
            try {
                Inventory db = InventoryService.getInventory(inventory.getId());
                int quantity = db.getQuantity() + inventory.getQuantity();

                if (quantity < 0) {
                    return new ResponseEntity<String>(
                            inventory.getLocationId() + " " + inventory.getProductId() + " quantity can't be less than 0",
                            HttpStatus.BAD_REQUEST
                    );
                }

                inventory.setQuantity(quantity);
            } catch (NoSuchElementException ignored) {
                // Simply add with incoming data
            }
        }

        try {
            InventoryService.saveAllInventories(inventories);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }

    @PutMapping("/transfer/")
    @CrossOrigin(origins = "http://localhost:3000")
    public ResponseEntity<String> transfer(@RequestBody Map<String, Object> params) {
        /*  frLocationId
            toLocationId
            productId
            quantity

            Not transfer if:
            1. No such locations exist
            2. No such product exist
            3. frLocation don't have enough product
        */

        String frLocationId = (String) params.get("frLocationId");
        String toLocationId = (String) params.get("toLocationId");
        String productId = (String) params.get("productId");
        int quantity = (int) params.get("quantity");

        // Check Locations
        if (!LocationService.isLocationExist(frLocationId))
            return new ResponseEntity<String>("Location not existed: " + frLocationId, HttpStatus.BAD_REQUEST);

        if (!LocationService.isLocationExist(toLocationId))
            return new ResponseEntity<String>("Location not existed: " + frLocationId, HttpStatus.BAD_REQUEST);

        if (!ProductService.isProductExist(productId))
            return new ResponseEntity<String>("Product not existed: " + productId, HttpStatus.BAD_REQUEST);

        Inventory from;
        Inventory to;
        try {
            from = InventoryService.getInventory(new InventoryId(frLocationId, productId));
        } catch (NoSuchElementException e) {
            return new ResponseEntity<String>("From Location don't have " + productId, HttpStatus.BAD_REQUEST);
        }

        if (from.getQuantity() < quantity)
            return new ResponseEntity<String>("From Location only have " + from.getQuantity() + " products", HttpStatus.BAD_REQUEST);

        from.setQuantity(from.getQuantity() - quantity);

        try {
            to = InventoryService.getInventory(new InventoryId(toLocationId, productId));
            to.setQuantity(to.getQuantity() + quantity);
        } catch (NoSuchElementException e) {
            to = new Inventory(toLocationId, productId, quantity);
        }

        try {
            List<Inventory> list = Stream.of(from, to).collect(Collectors.toList());
            InventoryService.saveAllInventories(list);
        } catch (Exception e) {
            return new ResponseEntity<String>(e.getMessage(), HttpStatus.BAD_REQUEST);
        }

        return new ResponseEntity<String>("Success", HttpStatus.OK);
    }
}