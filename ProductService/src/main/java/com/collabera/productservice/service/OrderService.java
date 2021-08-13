package com.collabera.productservice.service;

import java.util.List;

import com.collabera.productservice.entities.Order;
import com.collabera.productservice.exceptions.ProductException;

public interface OrderService {
	
	List<Order> findAll();
	
//	Order findById(Integer id);
	
	Order add(Order order) throws ProductException;
	
//	Order delete(Integer id);
	
//	Order update(Integer id, Order order);
	
}
