package org.Plexus.productservice.model;

import lombok.*;
import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.Transient;
import org.springframework.data.mongodb.core.mapping.Document;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Document(collection = "Products")
@Getter
@Setter
@ToString
public class Product {

    @Transient
    public static final String SEQUENCE_NAME = "products_sequence";
    @Id
    private long id;
    private String name;
    private String description;
    private Double price;
}
