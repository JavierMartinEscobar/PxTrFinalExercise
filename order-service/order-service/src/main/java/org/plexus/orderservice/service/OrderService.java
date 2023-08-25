package org.plexus.orderservice.service;

import jakarta.ws.rs.NotFoundException;
import org.plexus.orderservice.model.Order;
import org.plexus.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OrderService {
    @Autowired
    private OrderRepository orderRepository;

    public List<Order> getAll(){
        return orderRepository.findAll();
    }

    public Optional<Order> getOrderById(long id) {
        return orderRepository.findById(id);
    }

    public void createOrder(Order order) {
        orderRepository.save(order);
    }

    public void updateOrder(Order order) {
        orderRepository.save(order);
    }

    public void deleteOrder(long id) {
        orderRepository.deleteById(id);
    }
}
