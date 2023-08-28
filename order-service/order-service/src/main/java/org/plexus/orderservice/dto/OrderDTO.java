package org.plexus.orderservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.plexus.orderservice.model.OrderLineItem;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
    private String orderNumber;
    private List<OrderLineItem> orderLineItemList;
    private double price;
}