package com.collabera.productservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.collabera.productservice.entities.Order;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class OrderDTO {
	private Integer id;
	private Integer productId;
	private Integer quantity;
	private BigDecimal totalPrice;

	public static OrderDTO fromOrder(Order order) {
		return OrderDTO.builder().id(order.getId()).productId(order.getProductId())
				.quantity(order.getQuantity()).totalPrice(order.getTotalPrice()).build();
	}
	
	public static List<OrderDTO> fromListOrder(List<Order> orders){
		List<OrderDTO> dtos = new ArrayList<>();
		orders.forEach(order -> dtos.add(OrderDTO.fromOrder(order)));
		return dtos;
	}
}
