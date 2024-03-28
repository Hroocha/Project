package com.shopproject.purchase.repository;

import com.shopproject.purchase.entities.Purchase;
import com.shopproject.purchase.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;


import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, UUID>, CrudRepository<Purchase, UUID> {

    Optional <Purchase> findFirstByStatusOrderByDateOfPurchase(Status status);
    Optional <Purchase> findFirstByStatusOrStatusOrderByDateOfPurchase(Status statusOne, Status statusTwo);
    Page<Purchase> findByUserId(Pageable pageable, UUID id);

//    List<Purchase> findByUserId(UUID id);
//    @Query(value = "UPDATE purchase SET status = :status WHERE id = :id", nativeQuery = true)
//    @Modifying
//    @Transactional
//    void updateStatus(@Param("id") UUID id, Status status);
//
//    @Query(value = "UPDATE purchase SET status = :status, comment = :comment WHERE id = :id", nativeQuery = true)
//    @Modifying
//    @Transactional
//    void updateStatusError(@Param("id") UUID id, Status status, String comment);

//    @Query(value = "SELECT * FROM purchase WHERE status = :status ORDER BY date_of_purchase LIMIT 1", nativeQuery = true)
//    @Modifying
//    @Transactional
//    Optional <Purchase> takePurchase(Status status);


}
