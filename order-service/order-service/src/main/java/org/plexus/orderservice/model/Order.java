package org.plexus.orderservice.model;

import jakarta.persistence.*;

import java.util.List;

@Table(name = "orders")
@Entity
public class Order {
    // Instances
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "orderNumber")
    private String orderNumber;
    @OneToMany(cascade = CascadeType.ALL)
    private List<OrderLineItem> orderLineItem;
    @Column(name = "price")
    private double price;
    
    // Setter y Getter
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderNumber() {
        return orderNumber;
    }

    public void setOrderNumber(String orderNumber) {
        this.orderNumber = orderNumber;
    }

    public List<OrderLineItem> getOrderLineItem() {
        return orderLineItem;
    }

    public void setOrderLineItem(List<OrderLineItem> orderLineItem) {
        this.orderLineItem = orderLineItem;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    // Constructors
    public Order() {
    }

    public Order(Long id, String orderNumber, List<OrderLineItem> orderLineItem, double price) {
        this.id = id;
        this.orderNumber = orderNumber;
        this.orderLineItem = orderLineItem;
        this.price = price;
    }

    // Methods
    public String toString() {
        return "Order[ " + id + ", " + orderNumber + ", " + orderLineItem + ", " + price + "]";
    }
}