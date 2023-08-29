package org.plexus.orderservice.service;

import org.modelmapper.ModelMapper;
import org.plexus.orderservice.dto.OrderDTO;
import org.plexus.orderservice.exception.ResourceNotFoundException;
import org.plexus.orderservice.model.Order;
import org.plexus.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class OrderServiceImpl implements OrderService{
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderRepository orderRepository;

    @Override
    public OrderDTO createOrder(OrderDTO orderDTO) {
        Order order = mapearEntidad(orderDTO);

        Order newOrder = orderRepository.save(order);

        OrderDTO orderResponse = mapearDTO(newOrder);
        return orderResponse;
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Order> orders = orderRepository.findAll();
        return orders.stream().map(order -> mapearDTO(order)).collect(Collectors.toList());
    }

    @Override
    public OrderDTO getOrderById(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        return mapearDTO(order);
    }

    @Override
    public OrderDTO updateOrder(OrderDTO orderDTO, long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));

        order.setOrderNumber(orderDTO.getOrderNumber());
        order.setPrice(orderDTO.getPrice());

        Order updateOrder = orderRepository.save(order);
        return mapearDTO(updateOrder);
    }

    @Override
    public void deleteOrder(long id) {
        Order order = orderRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Order", "id", id));
        orderRepository.delete(order);
    }

    // Convierte Entidad a DTO
    private OrderDTO mapearDTO(Order publicacion) {
        OrderDTO publicacionDTO = modelMapper.map(publicacion, OrderDTO.class);
        return publicacionDTO;
    }

    // Convierte de DTO a Entidad
    private Order mapearEntidad(OrderDTO publicacionDTO) {
        Order publicacion = modelMapper.map(publicacionDTO, Order.class);
        return publicacion;
    }
}
