package com.collabera.productservice.controllers;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

import java.time.LocalDateTime;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.collabera.productservice.controllers.dto.ResponseDTO;
import com.collabera.productservice.controllers.dto.ResponseOrderDTO;
import com.collabera.productservice.dto.ProductDTO;
import com.collabera.productservice.entities.Message;
import com.collabera.productservice.entities.Product;
import com.collabera.productservice.service.MessageProducer;
import com.collabera.productservice.service.ProductService;

@RestController
@RequestMapping("/products")
public class ProductController {

	@Qualifier("productService")

	@Autowired
	ProductService prodService;

	@Autowired
	MessageProducer producer;

	@GetMapping("")
	public ResponseEntity<EntityModel<ResponseDTO>> getAll() {
		List<Product> products = prodService.findAll();
		ResponseDTO resp = ResponseDTO.builder().message(products.size() + " Record(s) retrieved")
				.products(ProductDTO.fromListProduct(products)).build();

		EntityModel<ResponseDTO> entity = EntityModel.of(resp);
		entity.add(linkTo(methodOn(ProductController.class).getAll()).withSelfRel());
		producer.sendMessage(Message.builder()
				.message(products.size() + " Record(s) retrieved")
				.action("Get All Product Success").time(LocalDateTime.now()).build());
		return ResponseEntity.status(200).body(entity);
	}

	@GetMapping("/{id}")
	public ResponseEntity<EntityModel<ResponseDTO>> getById(@PathVariable("id") Integer id) {
		Product productFound = prodService.findById(id);
		ResponseDTO resp = null;

		EntityModel<ResponseDTO> entity;
		if (productFound != null) {
			resp = ResponseDTO.builder().product(ProductDTO.fromProduct(productFound))
					.message("Successfully found the product with id: " + id).build();

			entity = EntityModel.of(resp);
			entity.add(linkTo(methodOn(ProductController.class).getAll()).withRel("all"));
			entity.add(linkTo(methodOn(ProductController.class).updateById(id, null)).withRel("update"));
			entity.add(linkTo(methodOn(ProductController.class).deleteById(id)).withRel("delete"));
			producer.sendMessage(Message.builder()
					.message("Successfully found the product with id: " + id)
					.action("Get Product By Id Success").time(LocalDateTime.now()).build());
			return ResponseEntity.ok(entity);

		}
		resp = ResponseDTO.builder().message("Product with id: " + id + " does not exist.").build();
		entity = EntityModel.of(resp);
		entity.add(linkTo(methodOn(ProductController.class).getAll()).withRel("all"));
		producer.sendMessage(Message.builder()
				.message("Product with id: " + id + " does not exist.")
				.action("Get Product By Id Fail").time(LocalDateTime.now()).build());
		return ResponseEntity.status(404).body(entity);
	}

	@PostMapping("")
	public ResponseEntity<ResponseDTO> postOne(@RequestBody Product prod) {
		prod = prodService.add(prod);
		ResponseDTO resp;

		if (prod != null) {
			resp = ResponseDTO.builder().product(ProductDTO.fromProduct(prod)).message("Product successfully added")
					.build();
			producer.sendMessage(Message.builder()
					.message("Product successfully added")
					.action("Add One Success").time(LocalDateTime.now()).build());
			return ResponseEntity.ok(resp);
		}

		resp = ResponseDTO.builder().message("Please check your input/s").build();
		producer.sendMessage(Message.builder()
				.message("Product was not successfully added")
				.action("Add One Fail").time(LocalDateTime.now()).build());
		return ResponseEntity.status(400).body(resp);
	}

	@PutMapping("/{id}")
	public ResponseEntity<ResponseDTO> updateById(@PathVariable("id") Integer id, @RequestBody Product prod) {
		prod = prodService.update(id, prod);
		ResponseDTO resp;
		if (prod != null) {
			resp = ResponseDTO.builder().product(ProductDTO.fromProduct(prod))
					.message("Product with id: " + id + " 	successfully updated").build();
			producer.sendMessage(Message.builder()
					.message("Product with id: " + id + " 	successfully updated")
					.action("Update One Success").time(LocalDateTime.now()).build());
			return ResponseEntity.ok(resp);
		}
		resp = ResponseDTO.builder().message("Product with id: " + id + " 	not successfully updated").build();
		producer.sendMessage(Message.builder()
				.message("Product with id: " + id + " 	not successfully updated")
				.action("Update One Fail").time(LocalDateTime.now()).build());
		return ResponseEntity.status(404).body(resp);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<ResponseDTO> deleteById(@PathVariable("id") Integer id) {
		Product prod = prodService.delete(id);
		ResponseDTO resp;
		if (prod != null) {
			resp = ResponseDTO.builder().product(ProductDTO.fromProduct(prod))
					.message("Product with id: " + id + " successfully delete").build();
			producer.sendMessage(Message.builder()
					.message("Product with id: " + id + " successfully delete")
					.action("Delete One Success").time(LocalDateTime.now()).build());
			return ResponseEntity.ok(resp);
		}

		resp = ResponseDTO.builder().message("Product with id: " + id + " was not successfully deleted").build();
		producer.sendMessage(Message.builder()
				.message("Product with id: " + id + " was not successfully deleted")
				.action("Delete One Fail").time(LocalDateTime.now()).build());
		return ResponseEntity.status(404).body(resp);
	}

	@ExceptionHandler(value = Exception.class)
	public ResponseEntity<ResponseDTO> generalError(Exception e) {
		ResponseDTO resp = ResponseDTO.builder().message("Ooops there is something wrong!").build();
		producer.sendMessage(Message.builder()
				.message("Ooops there is something wrong!")
				.action("Exception occured").time(LocalDateTime.now()).build());
		return ResponseEntity.status(400).body(resp);
	}
}
