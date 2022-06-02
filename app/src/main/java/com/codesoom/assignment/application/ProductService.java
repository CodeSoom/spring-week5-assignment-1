package com.codesoom.assignment.application;

import java.util.List;


import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.codesoom.assignment.domain.Product;
import com.codesoom.assignment.domain.ProductRepository;
import com.codesoom.assignment.dto.ProductData;
import com.codesoom.assignment.exception.ProductNotFoundException;

@Service
@Transactional
public class ProductService {
	private final ProductRepository productRepository;

	public ProductService(ProductRepository productRepository) {
		this.productRepository = productRepository;
	}

	@Transactional(readOnly = true)
	public List<Product> getProducts() {
		return productRepository.findAll();
	}

	@Transactional(readOnly = true)
	public Product getProduct(Long id) {
		return findProduct(id);
	}

	public Product createProduct(ProductData productData) {
		Product product = Product.builder()
			.name(productData.getName())
			.maker(productData.getMaker())
			.price(productData.getPrice())
			.imageUrl(productData.getImageUrl())
			.build();
		return productRepository.save(product);
	}

	public Product updateProduct(Long id, ProductData productData) {
		Product product = findProduct(id);

		product.change(
			productData.getName(),
			productData.getMaker(),
			productData.getPrice(),
			productData.getImageUrl()
		);

		return product;
	}

	public Product deleteProduct(Long id) {
		Product product = findProduct(id);

		productRepository.delete(product);

		return product;
	}
	private Product findProduct(Long id) {
		return productRepository.findById(id)
			.orElseThrow(() -> new ProductNotFoundException(id));
	}
}
