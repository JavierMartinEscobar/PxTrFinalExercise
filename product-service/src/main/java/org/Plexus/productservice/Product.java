package org.Plexus.productservice;

import org.springframework.data.annotation.Id;

public class Product {

    @Id
    private long id;
    private String name;
    private String description;
    private Double price;

    public Product () {}

    public Product (Long id, String name, String description, Double price) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.price = price;
    }

    public long getId() { return id; }

    public String getName() { return name; }
    public void setName(String name) { this.name = name; }

    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }

    public Double getPrice() { return price; }
    public void setPrice(Double price) { this.price = price; }

    public String toString() {
        return "Product[ " + id + ", " + name + ", " + description + ", " + price + "]";
    }
}
