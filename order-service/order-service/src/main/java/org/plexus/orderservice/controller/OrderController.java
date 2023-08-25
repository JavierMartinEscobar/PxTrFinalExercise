package org.plexus.orderservice.controller;

import org.plexus.orderservice.model.Order;
import org.plexus.orderservice.service.OrderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/orders")
public class OrderController {
    @Autowired
    private OrderService orderService;

    @GetMapping
    public ResponseEntity<List<Order>> getOrders() {
        return ResponseEntity.ok(orderService.getAll());
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Order> getOrderById(@PathVariable long id) {
        Optional<Order> product = orderService.getOrderById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Order> createProduct(@Validated @RequestBody Order order) {
        orderService.createOrder(order);
        return ResponseEntity.ok(order);
    }

    @PutMapping(value = "/{id}",  consumes = {"application/json"})
    public ResponseEntity<Order> updateProduct(@Validated @RequestBody Order order, @PathVariable long id) {
        Optional<Order> dbProduct = orderService.getOrderById(id);
        if(dbProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            dbProduct.get().setOrderNumber(order.getOrderNumber());
            dbProduct.get().setPrice(order.getPrice());
            orderService.updateOrder(dbProduct.get());
            return ResponseEntity.ok(dbProduct.get());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable long id){
        Optional<Order> dbProduct = orderService.getOrderById(id);
        if(dbProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        }
    }
}
