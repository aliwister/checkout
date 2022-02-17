package com.badals.checkout.repository;

import com.badals.checkout.domain.Checkout;
import com.badals.enumeration.CartState;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CheckoutRepository extends JpaRepository<Checkout, Long> {
   Optional<Checkout> findBySecureKey(String secureKey);
   Optional<Checkout> findByPaymentToken(String paymentToken);

   @Modifying
   @Query("UPDATE TenantCart c set c.cartState = ?2 where c.secureKey = ?1")
   public void closeCart(String secureKey, CartState state);
}

