package org.plexus.orderservice.controller;

import jakarta.validation.Valid;
import org.plexus.orderservice.dto.OrderLineItemDTO;
import org.plexus.orderservice.service.OrderLineItemService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/")
public class OrderLineItemController {
    @Autowired
    private OrderLineItemService orderLineItemService;

    @GetMapping("/orders/{orderId}/orderLineItems")
    public List<OrderLineItemDTO> getOrderLineItemByOrderId(@PathVariable(value = "orderId") Long orderId){
        return orderLineItemService.getOrderLineItemByOrderId(orderId);
    }

    @GetMapping("/orders/{orderId}/orderLineItems/{id}")
    public ResponseEntity<OrderLineItemDTO> getOrderLineItemById(@PathVariable(value = "orderId") Long orderId, @PathVariable(value = "id") Long orderLineItemId){
        OrderLineItemDTO orderLineItemDTO = orderLineItemService.getOrderLineItemById(orderId, orderLineItemId);
        return new ResponseEntity<>(orderLineItemDTO, HttpStatus.OK);
    }

    @PostMapping("/orders/{orderId}/orderLineItems")
    public ResponseEntity<OrderLineItemDTO> saveOrderLineItem(@PathVariable(value = "orderId") long orderId,@Valid @RequestBody OrderLineItemDTO orderLineItemDTO){
        return new ResponseEntity<>(orderLineItemService.createOrderLineItem(orderId, orderLineItemDTO),HttpStatus.CREATED);
    }

    @PutMapping("/orders/{orderId}/orderLineItems/{id}")
    public ResponseEntity<OrderLineItemDTO> updateOrderLineItem(@PathVariable(value = "orderId") Long orderId,@PathVariable(value = "id") Long orderLineItemId,@Valid @RequestBody OrderLineItemDTO orderLineItemDTO){
        OrderLineItemDTO updateOrderLineItem = orderLineItemService.updateOrderLineItem(orderId, orderLineItemId, orderLineItemDTO);
        return new ResponseEntity<>(updateOrderLineItem,HttpStatus.OK);
    }

    @DeleteMapping("/orders/{orderId}/orderLineItems/{id}")
    public ResponseEntity<String> deleteOrderLineItem(@PathVariable(value = "orderId") Long orderId,@PathVariable(value = "id") Long orderLineItemId){
        orderLineItemService.deleteOrderLineItem(orderId, orderLineItemId);
        return new ResponseEntity<>("Order line item successfully removed",HttpStatus.OK);
    }
}