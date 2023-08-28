package org.Plexus.productservice.controller;

import org.Plexus.productservice.dto.ProductDTO;
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
    public ResponseEntity<List<ProductDTO>> getAllProducts(@RequestParam(name = "name", required = false) String name) {
        List<ProductDTO> products;

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
    public ResponseEntity<ProductDTO> getProductById(@PathVariable long id) {
        ProductDTO product = productService.getProductById(id);
        return ResponseEntity.ok(product);
    }

    @PostMapping(consumes = {"application/json"})
    public ResponseEntity<Product> createProduct(@Validated @RequestBody Product product) {
        productService.createProduct(product);
        return ResponseEntity.ok(product);
    }

    @PutMapping(value = "/{id}",  consumes = {"application/json"})
    public ResponseEntity<Product> updateProduct(@Validated @RequestBody Product product, @PathVariable long id) {
        Product dbProduct = productService.getProductByIdNoDTO(id);
        dbProduct.setName(product.getName());
        dbProduct.setDescription(product.getDescription());
        dbProduct.setPrice(product.getPrice());
        productService.updateProduct(dbProduct);
        return ResponseEntity.ok(dbProduct);
    }

    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Product> deleteProduct(@PathVariable long id){
        productService.deleteProduct(id);
        return ResponseEntity.ok().build();
    }
}
