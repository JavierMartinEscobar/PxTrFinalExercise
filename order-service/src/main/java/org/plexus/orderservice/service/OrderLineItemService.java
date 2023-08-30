package org.plexus.orderservice.service;

import org.modelmapper.ModelMapper;
import org.plexus.orderservice.dto.OrderLineItemDTO;
import org.plexus.orderservice.exception.BlogAppException;
import org.plexus.orderservice.exception.ResourceNotFoundException;
import org.plexus.orderservice.model.Order;
import org.plexus.orderservice.model.OrderLineItem;
import org.plexus.orderservice.repository.OrderLineItemRepository;
import org.plexus.orderservice.repository.OrderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
public class OrderLineItemService {
    @Autowired
    private ModelMapper modelMapper;

    @Autowired
    private OrderLineItemRepository orderLineItemRepository;

    @Autowired
    private OrderRepository orderRepository;

    public OrderLineItemDTO createOrderLineItem(long orderId, OrderLineItemDTO orderLineItemDTO) {
        OrderLineItem orderLineItem = mapearEntidad(orderLineItemDTO);
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        orderLineItem.setOrder(order);
        order.getOrderLineItem().add(orderLineItem);
        order.setPrice(order.getPrice() + (orderLineItem.getPrice() * orderLineItem.getQuantity()));
        OrderLineItem newOrderLineItem = orderLineItemRepository.save(orderLineItem);
        return mapearDTO(newOrderLineItem);
    }

    public List<OrderLineItemDTO> getOrderLineItemByOrderId(long orderId) {
        List<OrderLineItem> orderLineItems = orderLineItemRepository.findByOrderId(orderId);
        return orderLineItems.stream().map(orderLineItem -> mapearDTO(orderLineItem)).collect(Collectors.toList());
    }

    public OrderLineItemDTO getOrderLineItemById(long orderId, Long orderLineItemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        OrderLineItem orderLineItem = orderLineItemRepository.findById(orderLineItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order line item", "id", orderLineItemId));

        if(!orderLineItem.getOrder().getId().equals(order.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "The order line item does not belong to the order");
        }

        return mapearDTO(orderLineItem);
    }

    public OrderLineItemDTO updateOrderLineItem(long orderId, long orderLineItemId, OrderLineItemDTO requestFromComment) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        OrderLineItem orderLineItem = orderLineItemRepository.findById(orderLineItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order line item", "id", orderLineItemId));

        if(!orderLineItem.getOrder().getId().equals(order.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "The order line item does not belong to the order");
        }

        orderLineItem.setSkuCode(requestFromComment.getSkuCode());
        orderLineItem.setPrice(requestFromComment.getPrice());
        orderLineItem.setQuantity(requestFromComment.getQuantity());

        OrderLineItem updateOrderLineItem = orderLineItemRepository.save(orderLineItem);
        return mapearDTO(updateOrderLineItem);
    }

    public void deleteOrderLineItem(long orderId, long orderLineItemId) {
        Order order = orderRepository.findById(orderId)
                .orElseThrow(() -> new ResourceNotFoundException("Order", "id", orderId));

        OrderLineItem orderLineItem = orderLineItemRepository.findById(orderLineItemId)
                .orElseThrow(() -> new ResourceNotFoundException("Order line item", "id", orderLineItemId));

        if(!orderLineItem.getOrder().getId().equals(order.getId())) {
            throw new BlogAppException(HttpStatus.BAD_REQUEST, "The order line item does not belong to the order");
        }

        order.setPrice(order.getPrice() - (orderLineItem.getQuantity() * orderLineItem.getPrice()));
        orderLineItemRepository.delete(orderLineItem);
    }

    // Convierte Entidad a DTO
    private OrderLineItemDTO mapearDTO(OrderLineItem orderLineItem) {
        OrderLineItemDTO orderLineItemDTO = modelMapper.map(orderLineItem, OrderLineItemDTO.class);
        return orderLineItemDTO;
    }

    // Convierte de DTO a Entidad
    private OrderLineItem mapearEntidad(OrderLineItemDTO orderLineItemDTO) {
        OrderLineItem orderLineItem = modelMapper.map(orderLineItemDTO, OrderLineItem.class);
        return orderLineItem;
    }
}
