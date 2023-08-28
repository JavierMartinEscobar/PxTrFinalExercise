package org.Plexus.productservice.service;

import org.Plexus.productservice.dto.ProductDTO;
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
    @Autowired
    private ProductMapper productMapper;

    public long generateSequence(String seqName) {
        DatabaseSequence counter = mongoOperations.findAndModify(query(where("_id").is(seqName)),
                new Update().inc("seq",1), options().returnNew(true).upsert(true),
                DatabaseSequence.class);
        return !Objects.isNull(counter) ? counter.getSeq() : 1;
    }

    public List<ProductDTO> getAllProducts() {
        return productMapper.toDTOList(productRepository.findAll());
    }

    public ProductDTO getProductById(long id) {
        return productMapper.toDTO(productRepository.findById(id).orElseThrow());
    }

    public Product getProductByIdNoDTO(long id) {
        return productRepository.findById(id).orElseThrow();
    }

    public List<ProductDTO> getProductsByName(String name) {
        return productMapper.toDTOList(productRepository.findByNameRegexIgnoreCase(name));
    }
    public ProductDTO createProduct(Product p) {
        p.setId(generateSequence(Product.SEQUENCE_NAME));
        return productMapper.toDTO(productRepository.save(p));
    }

    public ProductDTO updateProduct(Product p) {
        return productMapper.toDTO(productRepository.save(p));
    }

    public void deleteProduct(long id) {
        productRepository.deleteById(id);
    }
}
