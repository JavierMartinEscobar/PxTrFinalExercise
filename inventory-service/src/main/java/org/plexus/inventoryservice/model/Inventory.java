package org.plexus.inventoryservice.model;

import jakarta.persistence.*;

@Table(name = "inventory")
@Entity
public class Inventory {

    @Id
    private long id;
    private String skuCode;
    private int quantity;

    public Inventory() {
    }

    public Inventory(long id, String skuCode, int quantity) {
        this.id= id;
        this.skuCode = skuCode;
        this.quantity = quantity;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        id = id;
    }

    public String getSkuCode() {
        return skuCode;
    }

    public void setSkuCode(String skuCode) {
        this.skuCode = skuCode;
    }

    public int getquantity() {
        return quantity;
    }

    public void setquantity(int quantity) {
        this.quantity = quantity;
    }

    @Override
    public String toString() {
        return "Inventory{" +
                "id=" + id +
                ", skuCode='" + skuCode + '\'' +
                ", quantity=" + quantity +
                '}';
    }
}
