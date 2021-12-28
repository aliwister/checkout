package com.badals.checkout.repository;


import com.badals.checkout.domain.TenantPayment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the OrderPayment entity.
 */
@SuppressWarnings("unused")
@Repository
public interface TenantPaymentRepository extends JpaRepository<TenantPayment, Long> {

}
