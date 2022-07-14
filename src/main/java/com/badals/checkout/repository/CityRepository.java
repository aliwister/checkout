package com.badals.checkout.repository;

import com.badals.checkout.domain.City;
import com.badals.checkout.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface CityRepository extends JpaRepository<City, Long> {
    List<City> findAllByState(State state);
}
