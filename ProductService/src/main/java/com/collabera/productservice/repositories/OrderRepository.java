package com.collabera.productservice.repositories;

import java.util.List;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.collabera.productservice.entities.Order;

@Repository
public interface OrderRepository extends CrudRepository<Order, Integer>{
	List<Order> findAll();
}
