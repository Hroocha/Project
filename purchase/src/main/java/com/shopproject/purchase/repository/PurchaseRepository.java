package com.shopproject.purchase.repository;

import com.shopproject.purchase.entities.Purchase;
import com.shopproject.purchase.entities.Status;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PurchaseRepository extends PagingAndSortingRepository<Purchase, UUID>, CrudRepository<Purchase, UUID> {

    Optional<Purchase> findFirstByStatusOrderByDateOfPurchase(Status status);

    Optional<Purchase> findFirstByStatusInOrderByDateOfPurchase(Status [] statuses);



    Page<Purchase> findByUserId(Pageable pageable, UUID id);

    List<Purchase> getAllByDateOfPurchaseBetweenAndStatus(LocalDateTime from, LocalDateTime to, Status status);

    List<Purchase> getAllByDateOfPurchaseBetweenAndStatusAndUserIdEquals(
            LocalDateTime from, LocalDateTime to, Status status, UUID userid);

    List<Purchase> getAllByDateOfPurchaseBetweenAndStatusAndProductIdEquals(
            LocalDateTime from, LocalDateTime to, Status status, UUID productId);
}
