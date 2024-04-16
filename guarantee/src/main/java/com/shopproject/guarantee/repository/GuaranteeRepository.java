package com.shopproject.guarantee.repository;

import com.shopproject.guarantee.entity.Guarantee;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface GuaranteeRepository extends CrudRepository<Guarantee, UUID> {
    Optional<Guarantee> getGuaranteeByPurchaseId(UUID purchaseId);
}
