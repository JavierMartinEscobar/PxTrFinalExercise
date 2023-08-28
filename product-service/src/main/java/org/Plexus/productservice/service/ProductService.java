package org.Plexus.productservice.service;

import org.Plexus.productservice.repository.ProductRepository;
import org.Plexus.productservice.model.DatabaseSequence;
import org.Plexus.productservice.model.Product;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoOperations;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

import static org.springframework.data.mongodb.core.FindAndModifyOptions.options;
import static org.springframework.data.mongodb.core.query.Criteria.where;
import static org.springframework.data.mongodb.core.query.Query.query;

@Service
public class ProductService {

    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private MongoOperations mongoOperations;

    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(long id) {
        return productRepository.findById(id);
    }

    public List<Product> getProductsByName(String name) {
        return productRepository.findByNameRegexIgnoreCase(name);
    }
    public void createProduct(Product p) {
        p.setId(generateSequence(Product.SEQUENCE_NAME));
        productRepository.save(p);
    }

    public void updateProduct(Product p) {
        productRepository.save(p);
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
