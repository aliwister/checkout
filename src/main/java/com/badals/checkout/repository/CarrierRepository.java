package com.badals.checkout.repository;

import com.badals.checkout.domain.Carrier;
import com.badals.checkout.domain.pojo.projection.ShipRate;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CarrierRepository extends JpaRepository<Carrier, Long> {

   public static final String CARRIER_RATE = "CARRIER_RATE";

   //@Cacheable(cacheNames = com.badals.checkout.repository.CarrierRepository.CARRIER_RATE)
   @Query(nativeQuery = true, value = "select czr.id,   " +
           "czr.carrier_ref as `carrierRef`,   " +
           "c.name as `carrierName`,   " +
           "c.logo as `carrierLogo`,   " +
           "czr.carrier_zone_code as `carrierZoneCode`,   " +
           "czr.price,  czr.rate_name as `rateName`,  " +
           "czr.condition_min as `conditionMin`,   " +
           "czr.condition_max as `conditionMax`,  " +
           "czr.handling_fee as `handlingFee`,  " +
           "z.level,  " +
           "czr.is_free as `isFree`  " +
           "from profileshop.carrier_zone_rate czr  " +
           "join profileshop.carrier c on czr.carrier_ref = c.`ref`   " +
           "join profileshop.carrier_zone cz on czr.carrier_zone_code = cz.code and cz.active = 1  " +
           "join profileshop.carrier_zone_zone czz on cz.code = czz.carrier_zone_code    " +
           "join profileshop.`zone` z on z.code = czz.zone_code or z.part_of = czz.zone_code and z.active = 1   " +
           "where czr.active = 1  and czr.condition_min <= :weight and czr.condition_max > :weight and (z.code = :code) " +
           "order by level desc, price asc")
   public List<ShipRate> getShippingRates(@Param("code") String code, @Param("weight") String weight);
   
}

