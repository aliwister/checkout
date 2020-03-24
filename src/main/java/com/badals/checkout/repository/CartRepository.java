package com.badals.checkout.repository;

import com.badals.checkout.domain.Cart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CartRepository extends JpaRepository<Cart, Long> {
   Optional<Cart> findBySecureKey(String secureKey);
   Optional<Cart> findByPaymentTokenAndCheckedOut(String paymentToken, Boolean checkedOut);
}
