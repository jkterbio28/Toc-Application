package com.collabera.productservice.service;

import java.util.List;

import com.collabera.productservice.entities.Product;

public interface ProductService {
	List<Product> findAll();
	
	Product findById(Integer id);
	
	Product add(Product product);
	
	Product delete(Integer id);
	
	Product update(Integer id, Product product);
	
}
