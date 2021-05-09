package com.jasonyau.warehouse_inventory_system.models;

import javax.persistence.*;

@Entity
@Table(name = "inventory")
@IdClass(InventoryId.class)
public class Inventory {
    @Id
    @Column(name = "location_id")
    private String locationId;

    @Id
    @Column(name = "product_id")
    private String productId;

    private int quantity;

    @MapsId
    @ManyToOne
    @JoinColumn(
            name = "location_id", referencedColumnName = "id",
            insertable = false, updatable = false
    )
    private Location location;

    @MapsId
    @ManyToOne
    @JoinColumn(
            name = "product_id", referencedColumnName = "id",
            insertable = false, updatable = false
    )
    private Product product;

    public Inventory() {

    }

    public Inventory(String locationId, String productId, int quantity) {
        this.locationId = locationId;
        this.productId = productId;
        this.quantity = quantity;
    }

    public InventoryId getId() {
        return new InventoryId(locationId, productId);
    }

    public String getLocationId() {
        return locationId;
    }

    public String getProductId() {
        return productId;
    }

    public int getQuantity() {
        return quantity;
    }

    public Location getLocation() {
        return location;
    }

    public Product getProduct() {
        return product;
    }

    public void setLocationId(String locationId) {
        this.locationId = locationId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }
}
