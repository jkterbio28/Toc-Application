package com.collabera.productservice.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collabera.productservice.entities.Order;
import com.collabera.productservice.entities.Product;
import com.collabera.productservice.exceptions.ProductException;
import com.collabera.productservice.repositories.OrderRepository;
import com.collabera.productservice.repositories.ProductRepository;
import com.collabera.productservice.service.OrderService;

@Service("orderService")
public class OrderServiceImpl implements OrderService {

	@Autowired
	OrderRepository orderRepository;
	
	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Order> findAll() {
		return orderRepository.findAll();
	}

//	@Override
//	public Order findById(Integer id) {
//		return orderRepository.findById(id).orElse(null);
//	}

	@Override
	public Order add(Order order) throws ProductException {
		order.setId(0);
		if (!orderRepository.existsById(order.getId())) {
			if(productRepository.existsById(order.getProductId())) {
				Product prod = productRepository.findById(order.getProductId()).orElse(null);
				if(prod != null) {
					if(order.getQuantity() <= prod.getQuantity()) {
						int totalQuantity = prod.getQuantity() - order.getQuantity();
						prod.setQuantity(totalQuantity);
						productRepository.save(prod);
					}else {
						throw new ProductException(order.getProductId());
					}
				}
				return orderRepository.save(order);				
			}else {
				throw new ProductException(order.getProductId());
			}
		}
		return null;
	}
}
