package com.example.springserver.controllers;

import com.example.springserver.models.Product;
import com.example.springserver.services.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProductController {

    @Autowired
    private ProductService service;

    @GetMapping("/product/{productId}")
    public Product getProductById(@PathVariable int productId) {
        return service.getProductById(productId);
    }

    @GetMapping("/product/get-all")
    public List<Product> getAllProducts() {
        return service.getAllProducts();
    }

    @PostMapping("/product/create")
    public Product createProduct(@RequestBody ProductService.ProductRequest request) {
        return service.createProduct(request);
    }

    @PutMapping("/product/update")
    public Product updateProduct(@RequestBody ProductService.ProductRequest request) {
        return service.updateProduct(request);
    }

    @DeleteMapping("/product/delete/{productId}")
    public void delete(@PathVariable int productId) {
        service.delete(productId);
    }

    @GetMapping("/product/current/{mealId}")
    public List<Product> getAllCurrentMealProducts(@PathVariable int mealId) {
        return service.getAllCurrentMealProducts(mealId);
    }
}
