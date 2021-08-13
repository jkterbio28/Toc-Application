package com.collabera.productservice.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collabera.productservice.controllers.dto.ResponseOrderDTO;
import com.collabera.productservice.dto.OrderDTO;
import com.collabera.productservice.entities.Message;
import com.collabera.productservice.entities.Order;
import com.collabera.productservice.exceptions.ProductException;
import com.collabera.productservice.service.MessageProducer;
import com.collabera.productservice.service.OrderService;

@RestController
@RequestMapping("/orders")
public class OrderController {
	@Qualifier("orderService")

	@Autowired
	OrderService orderService;
	
	@Autowired
	MessageProducer producer;
	
	@GetMapping("")
	public ResponseEntity<EntityModel<ResponseOrderDTO>> getAll(){
		List<Order> orders = orderService.findAll();
		ResponseOrderDTO resp = ResponseOrderDTO.builder().message(orders.size() + " Record(s) retrieved").orders(OrderDTO.fromListOrder(orders)).build();
		
		EntityModel<ResponseOrderDTO> entity = EntityModel.of(resp);
		entity.add(linkTo(methodOn(OrderController.class).getAll()).withSelfRel());
		producer.sendMessage(Message.builder()
				.message(orders.size() + " Record(s) retrieved")
				.action("Get All Order Success").time(LocalDateTime.now()).build());
		return ResponseEntity.status(200).body(entity);
	}
	
	@PostMapping("")
	public ResponseEntity<ResponseOrderDTO> postOne(@RequestBody Order order) throws ProductException{
		order = orderService.add(order);
		ResponseOrderDTO resp;
		
		if(order != null) {
			resp = ResponseOrderDTO.builder()
					.order(OrderDTO.fromOrder(order))
					.message("Order successfully added")
					.build();
			producer.sendMessage(Message.builder()
					.message("Order successfully added")
					.action("Add Success").time(LocalDateTime.now()).build());
			return ResponseEntity.ok(resp);
		}
		
		resp = ResponseOrderDTO.builder()
				.message("Please check your input/s")
				.build();
		producer.sendMessage(Message.builder()
				.message("[Order] Please check your input/s")
				.action("Add Fail").time(LocalDateTime.now()).build());
		return ResponseEntity.status(400).body(resp);
	}
	
	@ExceptionHandler(value = ProductException.class)
	public ResponseEntity<ResponseOrderDTO> allError(Exception e){
		ResponseOrderDTO resp = ResponseOrderDTO.builder()
				.message(e.getMessage())
				.build();
		producer.sendMessage(Message.builder()
				.message(e.getMessage())
				.action("Add Order Fail").time(LocalDateTime.now()).build());
		return ResponseEntity.status(400).body(resp);
	}
	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseOrderDTO> generalError(Exception e){
		ResponseOrderDTO resp = ResponseOrderDTO.builder()
				.message("Ooops there is something wrong!")
				.build();
		producer.sendMessage(Message.builder()
				.message("Ooops there is something wrong!")
				.action("Exception Occured").time(LocalDateTime.now()).build());
		return ResponseEntity.status(400).body(resp);
	}
}
