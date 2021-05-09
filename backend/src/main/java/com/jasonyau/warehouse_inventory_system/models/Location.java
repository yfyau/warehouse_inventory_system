package com.jasonyau.warehouse_inventory_system.models;

import javax.persistence.*;

@Entity
@Table(name = "locations")
public class Location {
    @Id
    private String id;
    private String nameEn;
    private String nameHk;
    private String nameCn;

    public Location() {

    }

    public Location(String id, String nameEn, String nameHk, String nameCn) {
        this.id = id;
        this.nameEn = nameEn;
        this.nameHk = nameHk;
        this.nameCn = nameCn;
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
}
