package com.collabera.productservice.exceptions;

public class ProductException extends Exception {
	
	private static final long serialVersionUID = 1L;
	
	public ProductException(Integer productId) {
		super("Product Id does not exist: " + productId);
	}
}
