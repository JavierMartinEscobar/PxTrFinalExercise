package org.Plexus.productservice.repository;

import org.Plexus.productservice.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface ProductRepository extends MongoRepository<Product, Long> {
    List<Product> findByNameRegexIgnoreCase(String name);
}
