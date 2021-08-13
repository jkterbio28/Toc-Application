package com.collabera.productservice.serviceimpl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.collabera.productservice.entities.Product;
import com.collabera.productservice.repositories.ProductRepository;
import com.collabera.productservice.service.ProductService;

@Service("productService")
public class ProductServiceImpl implements ProductService {

	@Autowired
	ProductRepository productRepository;

	@Override
	public List<Product> findAll() {
		return productRepository.findAll();
	}

	@Override
	public Product findById(Integer id) {
		return productRepository.findById(id).orElse(null);
	}

	@Override
	public Product add(Product product) {
		product.setId(0);
		if (!productRepository.existsById(product.getId())) {
			return productRepository.save(product);
		}
		return null;
	}

	@Override
	public Product delete(Integer id) {
		Product productFound = productRepository.findById(id).orElse(null);
		if (productFound != null) {
			productRepository.deleteById(id);
		}
		return productFound;
	}

	@Override
	public Product update(Integer id, Product product) {
		Product productFound = productRepository.findById(id).orElse(null);
		if (productFound != null) {
			productFound.copyFrom(product);
			return productRepository.save(productFound);
		}
		return null;
	}

}
