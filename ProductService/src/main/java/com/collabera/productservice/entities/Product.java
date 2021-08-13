package com.collabera.productservice.entities;

import java.math.BigDecimal;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "products")
public class Product {

	@Id
	@GeneratedValue(strategy = GenerationType.AUTO)
	private Integer id;
	@Column(nullable = false)
	private String name;
	private String description;
	private String brand;
	@Column(nullable = false)
	@Builder.Default
	private BigDecimal price = new BigDecimal(0);
	@Column(nullable = false)
	private Integer quantity;
	
	public void copyFrom(Product prod) {
		if (prod.getName() != null) {
			this.setName(prod.getName());
		}
		if (prod.getDescription() != null) {
			this.setDescription(prod.getDescription());
		}
		if (prod.getBrand() != null) {
			this.setBrand(prod.getBrand());				
		}
		if (prod.getPrice() != null) {
			this.setPrice(prod.getPrice());
		}
		if (prod.getQuantity() != null) {
			this.setQuantity(prod.getQuantity());
		}
	}
}
