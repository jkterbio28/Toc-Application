package com.collabera.productservice.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.collabera.productservice.entities.Product;

@Repository
public interface ProductRepository extends CrudRepository<Product, Integer>{
	List<Product> findAll();
}
