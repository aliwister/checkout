package com.badals.checkout.repository;


import com.badals.checkout.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
   Optional<Order> findByReferenceAndConfirmationKey(String reference, String confirmationKey);
}
