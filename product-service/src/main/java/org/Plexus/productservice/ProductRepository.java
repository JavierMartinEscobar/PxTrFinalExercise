package org.Plexus.productservice;

import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface ProductRepository extends MongoRepository<Product, Long> {
    public List<Product> findByName(String name);
}
