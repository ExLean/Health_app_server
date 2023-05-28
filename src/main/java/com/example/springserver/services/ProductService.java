package com.example.springserver.services;

import com.example.springserver.exceptions.*;
import com.example.springserver.models.Product;
import com.example.springserver.models.type.Metric;
import com.example.springserver.repositories.FoodRepository;
import com.example.springserver.repositories.MealRepository;
import com.example.springserver.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.util.Streamable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;

    @Autowired
    private FoodRepository foodRepository;

    @Autowired
    private MealRepository mealRepository;

    public Product getProductById(int productId) {
        return productRepository
                .findById(productId)
                .orElseThrow(() ->
                        new ProductNotFoundException("Product with id: " + productId + ", does not exist."));
    }

    public List<Product> getAllProducts() {
        List<Product> allProducts = new ArrayList<>();
        Streamable.of(productRepository.findAll()).forEach(allProducts::add);
        return allProducts;
    }

    public Product createProduct(ProductRequest request) {
        Product product = new Product();
        List<Product> allProducts = getAllProducts();

        if (allProducts.size() > 0) {
            for (Product prod : allProducts) {
                if (prod.getFood().getId() == request.getFoodId() &&
                    prod.getMealId() == request.getMealId()) {
                    throw new ProductAlreadyExistException("There already is a product with that food item");
                }
            }
        }

        product.setFood(foodRepository.findById(request.getFoodId()).orElseThrow(() -> new FoodNotFoundException("Food with id: " + request.getFoodId() + "does not exists")));
        product.setMealId(request.getMealId());
        product.setAmount(request.getAmount());
        product.setMetric(request.getMetric());

        return productRepository.save(product);
    }

    public List<Product> getAllCurrentMealProducts(int mealId) {
        mealRepository.findById(mealId)
                .orElseThrow(() -> new MealNotFoundException("Meal with id: " + mealId + " does not exist"));

        List<Product> allProducts = getAllProducts();
        List<Product> allMealProducts = new ArrayList<>();

        for (Product product: allProducts) {
            if (product.getMealId() == mealId) {
                allMealProducts.add(product);
            }
        }

        return allMealProducts;
    }

    public static class ProductRequest {
        private int productId;
        private int foodId;
        private int mealId;
        private float amount;
        private Metric metric;

        public int getProductId() {
            return productId;
        }

        public int getFoodId() {
            return foodId;
        }

        public int getMealId() {
            return mealId;
        }

        public float getAmount() {
            return amount;
        }

        public Metric getMetric() {
            return metric;
        }

        public void setProductId(int productId) {
            this.productId = productId;
        }

        public void setFoodId(int foodId) {
            this.foodId = foodId;
        }

        public void setMealId(int mealId) {
            this.mealId = mealId;
        }

        public void setAmount(float amount) {
            this.amount = amount;
        }

        public void setMetric(Metric metric) {
            this.metric = metric;
        }
    }

    public Product updateProduct(ProductRequest request) {
        Product newProduct = getProductById(request.getProductId());

        if (request.getAmount() != 0) {
            newProduct.setAmount(request.getAmount());
        }
        if (request.getFoodId() != 0) {
            newProduct.setFood(foodRepository.findById(request.getFoodId()).orElseThrow(()
                    -> new FoodNotFoundException("Food with id: " + request.getFoodId() + " does not exist")));
        }
        if (request.getMetric() != null) {
            newProduct.setMetric(request.getMetric());
        }

        return productRepository.save(newProduct);
    }

    public void delete(int productId) {
        productRepository.delete(getProductById(productId));
    }
}
