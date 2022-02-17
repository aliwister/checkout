package com.badals.checkout.repository;

import com.badals.checkout.domain.City;
import com.badals.checkout.domain.State;
import com.badals.checkout.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface ZoneRepository extends JpaRepository<Zone, Long> {
    List<Zone> findAllByLevelAndActiveIsTrue(Zone.ZoneLevel level);
}
