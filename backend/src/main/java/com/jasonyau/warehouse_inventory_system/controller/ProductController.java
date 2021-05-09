package com.jasonyau.warehouse_inventory_system.controller;

import com.jasonyau.warehouse_inventory_system.models.Product;
import com.jasonyau.warehouse_inventory_system.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.NoSuchElementException;

@RestController
@RequestMapping("/product")
public class ProductController {
    @Autowired
    ProductService ProductService;

    @GetMapping("")
    public List<Product> list() {
        return ProductService.listAllProduct();
    }

    @GetMapping("/{id}")
    public ResponseEntity<Product> get(@PathVariable String id) {
        try {
            Product Product = ProductService.getProduct(id);
            return new ResponseEntity<Product>(Product, HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<Product>(HttpStatus.NOT_FOUND);
        }
    }

    @PostMapping("/")
    public void add(@RequestBody Product product) {
        ProductService.saveProduct(product);
    }

    @PutMapping("/")
    @CrossOrigin(origins = "http://localhost:3000")
    public void updateMultiple(@RequestBody List<Product> products) {
        ProductService.saveAllProducts(products);
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> update(@RequestBody Product product, @PathVariable String id) {
        try {
            Product existProduct = ProductService.getProduct(id);
            product.setId(id);
            ProductService.saveProduct(product);
            return new ResponseEntity<>(HttpStatus.OK);
        } catch (NoSuchElementException e) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
    }

    @DeleteMapping("/{id}")
    public void delete(@PathVariable String id) {
        ProductService.deleteProduct(id);
    }
}