package com.badals.checkout.repository;


import com.badals.checkout.domain.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.jpa.repository.QueryHints;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;


/**
 * Spring Data  repository for the Order entity.
 */
@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
   Optional<Order> findByReferenceAndConfirmationKey(String reference, String confirmationKey);

   @Query(value="select t.id " +
           "from  (select :s0 as id " +
           "union all select :s1 " +
           "union all select :s2 " +
           "union all select :s3 " +
           "union all select :s4 " +
           "union all select :s5 " +
           "union all select :s6 " +
           "union all select :s7 " +
           "union all select :s8 " +
           "union all select :s9 " +
           ") as t  " +
           "where not exists ( " +
           "select 1 from  " +
           "jhi_order o " +
           "where o.reference = t.id " +
           ")  " +
           "limit 1 ", nativeQuery = true)
   String getFirstUnused(
           @Param("s0") String option0,
           @Param("s1") String option1,
           @Param("s2") String option2,
           @Param("s3") String option3,
           @Param("s4") String option4,
           @Param("s5") String option5,
           @Param("s6") String option6,
           @Param("s7") String option7,
           @Param("s8") String option8,
           @Param("s9") String option9);
}
