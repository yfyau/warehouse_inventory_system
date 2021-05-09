package com.jasonyau.warehouse_inventory_system.service;

import com.jasonyau.warehouse_inventory_system.models.Product;
import com.jasonyau.warehouse_inventory_system.repository.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class ProductService {
    @Autowired
    private ProductRepository productRepository;

    public List<Product> listAllProduct() {
        return productRepository.findAll();
    }

    public void saveProduct(Product product) {
        productRepository.save(product);
    }

    public void saveAllProducts(List<Product> products) {
        productRepository.saveAll(products);
    }

    public boolean isProductExist(String id) {
        return productRepository.existsById(id);
    }

    public Product getProduct(String id) {
        return productRepository.findById(id).get();
    }

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
