package com.project.ProductService.query.api.projection;

import com.project.ProductService.command.api.data.Product;
import com.project.ProductService.command.api.data.ProductRepository;
import com.project.ProductService.common.model.ProductRestModel;
import com.project.ProductService.query.api.queries.GetProductsQuery;
import org.axonframework.queryhandling.QueryHandler;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class ProductProjection {
    private ProductRepository productRepository;

    public ProductProjection(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @QueryHandler
    public List<ProductRestModel> handle(GetProductsQuery getProductsQuery){
        // We have list of products
        List<Product> products = productRepository.findAll();
        // Converting to the list of productRestModels
        List<ProductRestModel> productRestModels = products.stream()
                .map(product ->
                        ProductRestModel
                                .builder()
                                .quantity(product.getQuantity())
                                .price(product.getPrice())
                                .name(product.getName())
                                .build()).collect(Collectors.toList());
        return productRestModels;
    }
}
