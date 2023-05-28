package com.example.springserver;

import com.example.springserver.controllers.MealController;
import com.example.springserver.controllers.ProductController;
import com.example.springserver.models.Food;
import com.example.springserver.models.Meal;
import com.example.springserver.models.Product;
import com.example.springserver.models.type.Metric;
import com.example.springserver.services.MealService;
import com.example.springserver.services.ProductService;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

@SpringBootTest
public class ProductControllerTest {

    @Mock
    private ProductService productService;

    @InjectMocks
    private ProductController productController;

    @Test
    void getProductByIdTest() {
        Meal meal = new Meal();
        meal.setId(11);
        meal.setTitle("TestMeal");
        meal.setAmount(100F);
        meal.setMetric(Metric.G);

        Food food = new Food();
        food.setName("Bananas");
        food.setCalories(55F);
        food.setProtein(12F);
        food.setCarbs(80F);
        food.setFat(2F);

        Product product = new Product();
        product.setId(111);
        product.setMealId(meal.getId());
        product.setFood(food);
        product.setAmount(100F);
        product.setMetric(Metric.G);

        Mockito.when(productService.getProductById(product.getId())).thenReturn(product);

        Product foundProduct = productController.getProductById(product.getId());

        assertEquals("Bananas", foundProduct.getFood().getName());
        assertEquals(11, foundProduct.getMealId());
    }

    @Test
    void getAllProductsTest() {
        Meal meal = new Meal();
        meal.setId(11);
        meal.setTitle("TestMeal");
        meal.setAmount(100F);
        meal.setMetric(Metric.G);

        Food food = new Food();
        food.setName("Bananas");
        food.setCalories(55F);
        food.setProtein(12F);
        food.setCarbs(80F);
        food.setFat(2F);

        Product product1 = new Product();
        product1.setId(111);
        product1.setMealId(meal.getId());
        product1.setFood(food);
        product1.setAmount(100F);
        product1.setMetric(Metric.G);

        Product product2 = new Product();
        product2.setId(222);
        product2.setMealId(meal.getId());
        product2.setFood(food);
        product2.setAmount(100F);
        product2.setMetric(Metric.G);

        List<Product> allProducts = new ArrayList<>();
        allProducts.add(product1);
        allProducts.add(product2);

        Mockito.when(productService.getAllProducts()).thenReturn(allProducts);

        List<Product> products = productController.getAllProducts();

        assertEquals(111, products.get(0).getId());
        assertEquals(222, products.get(1).getId());
    }

    @Test
    void createProductTest() {
        Meal meal = new Meal();
        meal.setId(11);
        meal.setTitle("TestMeal");
        meal.setAmount(100F);
        meal.setMetric(Metric.G);

        Food food = new Food();
        food.setId(1);
        food.setName("Bananas");
        food.setCalories(55F);
        food.setProtein(12F);
        food.setCarbs(80F);
        food.setFat(2F);

        Product product = new Product();
        product.setId(111);
        product.setMealId(meal.getId());
        product.setFood(food);
        product.setAmount(100F);
        product.setMetric(Metric.G);

        ProductService.ProductRequest productRequest = new ProductService.ProductRequest();
        productRequest.setFoodId(food.getId());
        productRequest.setMealId(meal.getId());
        productRequest.setAmount(100F);
        productRequest.setMetric(Metric.G);

        Mockito.when(productService.createProduct(productRequest)).thenReturn(product);

        Product newProduct = productController.createProduct(productRequest);

        assertEquals(111, newProduct.getId());
        assertEquals(11, newProduct.getMealId());
    }

    @Test
    void updateProductTest() {
        Meal meal = new Meal();
        meal.setId(11);
        meal.setTitle("TestMeal");
        meal.setAmount(100F);
        meal.setMetric(Metric.G);

        Food food = new Food();
        food.setId(1);
        food.setName("Bananas");
        food.setCalories(55F);
        food.setProtein(12F);
        food.setCarbs(80F);
        food.setFat(2F);

        Product product = new Product();
        product.setId(111);
        product.setMealId(meal.getId());
        product.setFood(food);
        product.setAmount(100F);
        product.setMetric(Metric.G);

        ProductService.ProductRequest productRequest = new ProductService.ProductRequest();
        productRequest.setFoodId(food.getId());
        productRequest.setMealId(meal.getId());
        productRequest.setAmount(100F);
        productRequest.setMetric(Metric.G);

        Mockito.when(productService.updateProduct(productRequest)).thenReturn(product);

        Product updateProduct = productController.updateProduct(productRequest);

        assertEquals(111, updateProduct.getId());
        assertEquals(11, updateProduct.getMealId());
    }

    @Test
    void deleteProductTest() {
        Meal meal = new Meal();
        meal.setId(11);
        meal.setTitle("TestMeal");
        meal.setAmount(100F);
        meal.setMetric(Metric.G);

        Food food = new Food();
        food.setName("Bananas");
        food.setCalories(55F);
        food.setProtein(12F);
        food.setCarbs(80F);
        food.setFat(2F);

        Product product = new Product();
        product.setId(111);
        product.setMealId(meal.getId());
        product.setFood(food);
        product.setAmount(100F);
        product.setMetric(Metric.G);

        productController.delete(product.getId());

        Mockito.verify(productService, Mockito.times(1)).delete(product.getId());
    }

    @Test
    void getAllCurrentMealProductsTest() {
        Meal meal = new Meal();
        meal.setId(11);
        meal.setTitle("TestMeal");
        meal.setAmount(100F);
        meal.setMetric(Metric.G);

        Food food = new Food();
        food.setId(1);
        food.setName("Bananas");
        food.setCalories(55F);
        food.setProtein(12F);
        food.setCarbs(80F);
        food.setFat(2F);

        Product product1 = new Product();
        product1.setId(111);
        product1.setMealId(meal.getId());
        product1.setFood(food);
        product1.setAmount(100F);
        product1.setMetric(Metric.G);

        Product product2 = new Product();
        product2.setId(222);
        product2.setMealId(meal.getId());
        product2.setFood(food);
        product2.setAmount(100F);
        product2.setMetric(Metric.G);

        List<Product> allProducts = new ArrayList<>();
        allProducts.add(product1);
        allProducts.add(product2);

        Mockito.when(productService.getAllCurrentMealProducts(meal.getId())).thenReturn(allProducts);

        List<Product> products = productController.getAllCurrentMealProducts(meal.getId());

        assertEquals(111, products.get(0).getId());
        assertEquals(222, products.get(1).getId());
    }
}
