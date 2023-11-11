package com.badals.checkout.repository;

import com.badals.checkout.domain.Reward;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface RewardRepository extends JpaRepository<Reward, Long> {
    Reward findByRewardType(String rewardType);
    List<Reward> findAllByPointsLessThanEqual(Integer points);
}
