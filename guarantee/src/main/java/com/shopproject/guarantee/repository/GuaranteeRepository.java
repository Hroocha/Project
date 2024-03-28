package com.shopproject.guarantee.repository;

import com.shopproject.guarantee.entity.Guarantee;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuaranteeRepository extends CrudRepository<Guarantee, UUID> {
    Optional<Guarantee> getGuaranteeByPurchaseId(UUID purchaseId);
}
