package com.badals.checkout.repository;

import com.badals.checkout.domain.CarrierRateZone;
import com.badals.checkout.domain.Zone;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarrierRateZoneRepository extends JpaRepository<CarrierRateZone, Long> {
    List<Zone> findAllByZoneIn(Zone zone);
}
