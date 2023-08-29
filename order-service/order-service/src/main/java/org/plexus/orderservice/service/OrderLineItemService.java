package org.plexus.orderservice.service;

import org.plexus.orderservice.dto.OrderLineItemDTO;

import java.util.List;

public interface OrderLineItemService {
    public OrderLineItemDTO createOrderLineItem(long orderId, OrderLineItemDTO orderLineItemDTO);

    public List<OrderLineItemDTO> getOrderLineItemByOrderId(long orderId);

    public OrderLineItemDTO getOrderLineItemById(long orderId,Long orderLineItemId);

    public OrderLineItemDTO updateOrderLineItem(long orderId, long orderLineItemId, OrderLineItemDTO requestFromComment);

    public void deleteOrderLineItem(long orderId, long orderLineItemId);
}
