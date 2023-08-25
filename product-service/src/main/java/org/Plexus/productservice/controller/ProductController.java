package org.Plexus.productservice.controller;

import org.Plexus.productservice.model.Product;
import org.Plexus.productservice.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/products")
public class ProductController {

    @Autowired
    private ProductService productService;

    @GetMapping
    public ResponseEntity<List<Product>> getAllProducts(@RequestParam(name = "name", required = false) String name) {
        List<Product> products;

        if(name == null) {
            products = productService.getAllProducts();
        } else {
            products = productService.getProductsByName(name);
        }

        if(products.isEmpty()) {
            return ResponseEntity.noContent().build();
        } else {
            return ResponseEntity.ok(products);
        }
    }

    @GetMapping(value = "/{id}")
    public ResponseEntity<Product> getProductById(@PathVariable long id) {
        Optional<Product> product = productService.getProductById(id);
        return product.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Product> createProduct(@Validated @RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping(value = "/{id}",  consumes = {"application/json"})
    public ResponseEntity<Product> updateProduct(@Validated @RequestBody Product product, @PathVariable long id) {
        Optional<Product> dbProduct = productService.getProductById(id);
        if(dbProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            dbProduct.get().setName(product.getName());
            dbProduct.get().setDescription(product.getDescription());
            dbProduct.get().setPrice(product.getPrice());
            productService.updateProduct(dbProduct.get());
            return ResponseEntity.ok(dbProduct.get());
        }
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long id){
        Optional<Product> dbProduct = productService.getProductById(id);
        if(dbProduct.isEmpty()) {
            return ResponseEntity.notFound().build();
        } else {
            productService.deleteProduct(id);
            return ResponseEntity.noContent().build();
        }
    }
}
