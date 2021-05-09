package com.jasonyau.warehouse_inventory_system.models;

import java.io.Serializable;

public class InventoryId implements Serializable {
    private String locationId;
    private String productId;

    InventoryId() {

    }

    public InventoryId(String locationId, String productId) {
        this.locationId = locationId;
        this.productId = productId;
    }

    public String getLocationId() {
        return locationId;
    }

    public String getProductId() {
        return productId;
    }
}