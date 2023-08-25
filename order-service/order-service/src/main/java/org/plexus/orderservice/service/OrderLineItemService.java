package org.plexus.orderservice.service;

import jakarta.ws.rs.NotFoundException;
import org.plexus.orderservice.model.OrderLineItem;
import org.plexus.orderservice.repository.OrderLineItemRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class OrderLineItemService {
    @Autowired
    private OrderLineItemRepository orderLineItemRepository;

    public List<OrderLineItem> getAll(){
        return orderLineItemRepository.findAll();
    }

    public OrderLineItem getOrderListItemById(Long id) {
        return orderLineItemRepository.findById(id).orElseThrow(() -> new NotFoundException("Order not found: " + id));
    }

    public OrderLineItem save(OrderLineItem orderLineItem) {
        OrderLineItem nuevoOrderLineItem = orderLineItemRepository.save(orderLineItem);
        return nuevoOrderLineItem;
    }

    public void deleteById(long id) {
        orderLineItemRepository.deleteById(id);
    }

    public boolean existsById(Long id) {
        if (orderLineItemRepository.existsById(id)) {
            return true;
        } else {
            return false;
        }
    }
}
