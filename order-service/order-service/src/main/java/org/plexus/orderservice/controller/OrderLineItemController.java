package org.plexus.orderservice.controller;

import org.plexus.orderservice.model.OrderLineItem;
import org.plexus.orderservice.service.OrderLineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/orderlistitem")
public class OrderLineItemController {
    @Autowired
    private OrderLineItemService orderLineItemService;

    @GetMapping
    public ResponseEntity<List<OrderLineItem>> getAllOrderListItems() {
        List<OrderLineItem> orderLineItems = orderLineItemService.getAll();
        if(orderLineItems.isEmpty()) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.ok(orderLineItems);
    }

    @GetMapping("/{id}")
    public ResponseEntity<OrderLineItem> getOrderListItemById(@PathVariable Long id) {
        OrderLineItem orderLineItem = orderLineItemService.getOrderListItemById(id);
        if(orderLineItem == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(orderLineItem);
    }

    @PostMapping
    public ResponseEntity<OrderLineItem> createOrderListItem(@RequestBody OrderLineItem orderLineItem) {
        OrderLineItem savedOrderLineItem = orderLineItemService.save(orderLineItem);
        return ResponseEntity.ok(savedOrderLineItem);
    }

    @PutMapping(value = "/{id}", consumes = {"application/json"})
    public ResponseEntity<OrderLineItem> updateOrderListItem(@PathVariable Long id, @Validated @RequestBody OrderLineItem orderLineItem) {
        if (!orderLineItemService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderLineItem.setId(id);
        OrderLineItem updatedOrderLineItem = orderLineItemService.save(orderLineItem);
        return ResponseEntity.ok(orderLineItem);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteOrderListItem(@PathVariable Long id) {
        if (!orderLineItemService.existsById(id)) {
            return ResponseEntity.notFound().build();
        }
        orderLineItemService.deleteById(id);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }
}

