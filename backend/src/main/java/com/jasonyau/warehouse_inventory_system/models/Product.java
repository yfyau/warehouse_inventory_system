package com.jasonyau.warehouse_inventory_system.models;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "products")
public class Product {
    @Id
    private String id;
    private String nameEn;
    private String nameHk;
    private String nameCn;

    @Column(name="weight_g")
    private int weightG;

    public Product() {

    }

    public Product(String id, String nameEn, String nameHk, String nameCn, int weightG) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameHk = nameHk;
        this.nameCn = nameCn;
        this.weightG = weightG;
    }

    public String getId() {
        return id;
    }

    public String getNameEn() {
        return nameEn;
    }

    public String getNameHk() {
        return nameHk;
    }

    public String getNameCn() {
        return nameCn;
    }

    public int getWeightG() {
        return weightG;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setNameEn(String nameEn) {
        this.nameEn = nameEn;
    }

    public void setNameHk(String nameHk) {
        this.nameHk = nameHk;
    }

    public void setNameCn(String nameCn) {
        this.nameCn = nameCn;
    }

    public void setWeightG(int weightG) {
        this.weightG = weightG;
    }
}
