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
        Optional<Order> order = orderService.getOrderById(id);
        return order.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Order> createOrder(@Validated @RequestBody Order order) {
        orderService.createOrder(order);
        return ResponseEntity.ok(order);
    }

    @PutMapping(value = "/{id}",  consumes = {"application/json"})
    public ResponseEntity<Order> updateOrder(@Validated @RequestBody Order order, @PathVariable long id) {
        Optional<Order> dbOrder = orderService.getOrderById(id);
        if(dbOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            dbOrder.get().setOrderNumber(order.getOrderNumber());
            dbOrder.get().setPrice(order.getPrice());
            orderService.updateOrder(dbOrder.get());
            return ResponseEntity.ok(dbOrder.get());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Order> deleteOrder(@PathVariable long id){
        Optional<Order> dbOrder = orderService.getOrderById(id);
        if(dbOrder.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            orderService.deleteOrder(id);
            return ResponseEntity.noContent().build();
        }
    }
}
