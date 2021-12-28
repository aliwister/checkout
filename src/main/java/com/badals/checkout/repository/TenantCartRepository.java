package com.badals.checkout.repository;

import com.badals.checkout.domain.TenantCart;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantCartRepository extends JpaRepository<TenantCart, Long> {
   Optional<TenantCart> findBySecureKey(String secureKey);
   Optional<TenantCart> findByPaymentTokenAndCheckedOut(String paymentToken, Boolean checkedOut);
}

