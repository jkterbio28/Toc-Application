package com.collabera.productservice.dto;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import com.collabera.productservice.entities.Product;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ProductDTO {

	public Integer id;
	public String name;
	public String description;
	public String brand;
	public BigDecimal price;
	public Integer quantity;
	
	public static ProductDTO fromProduct(Product prod) {
		return ProductDTO.builder().id(prod.getId()).name(prod.getName())
				.description(prod.getDescription()).brand(prod.getBrand())
				.price(prod.getPrice()).quantity(prod.getQuantity()).build();
	}
	
	public static List<ProductDTO> fromListProduct(List<Product> products){
		List<ProductDTO> dtos = new ArrayList<>();
		products.forEach(prod -> dtos.add(ProductDTO.fromProduct(prod)));
		return dtos;
	}
	
}
