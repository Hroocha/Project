package com.shopproject.product.repository;

import com.shopproject.product.entities.Product;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ProductRepository extends PagingAndSortingRepository<Product, UUID>, CrudRepository<Product, UUID> {
}
