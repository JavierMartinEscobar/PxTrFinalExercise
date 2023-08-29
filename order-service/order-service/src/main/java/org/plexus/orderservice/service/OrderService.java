package org.plexus.orderservice.service;

import org.plexus.orderservice.dto.OrderDTO;

import java.util.List;

public interface OrderService {
    public OrderDTO createOrder(OrderDTO orderDTO);

    public List<OrderDTO> getAllOrders();

    public OrderDTO getOrderById(long id);

    public OrderDTO updateOrder(OrderDTO orderDTO,long id);

    public void deleteOrder(long id);
}
