package com.badals.checkout.repository;

import com.badals.checkout.domain.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    Reward findByRewardType(String rewardType);
}
