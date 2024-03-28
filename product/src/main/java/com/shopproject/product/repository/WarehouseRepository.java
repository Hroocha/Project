package com.shopproject.product.repository;

import com.shopproject.product.entities.Warehouse;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface WarehouseRepository extends CrudRepository<Warehouse, UUID> {
}

