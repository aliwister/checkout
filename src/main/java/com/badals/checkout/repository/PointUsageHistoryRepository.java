package com.badals.checkout.repository;

import com.badals.checkout.domain.PointUsageHistory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointUsageHistoryRepository extends JpaRepository<PointUsageHistory, Long> {
    List<PointUsageHistory> findAllByCustomerId(Long customerId);
    PointUsageHistory findByCheckoutIdAndRewardId(Long checkoutId, Long rewardId);
    void deleteByCheckoutIdAndRewardId(Long checkoutId, Long rewardId);

}
