package com.badals.checkout.repository;

import com.badals.checkout.domain.City;
import com.badals.checkout.domain.State;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Spring Data  repository for the Address entity.
 */
@SuppressWarnings("unused")
@Repository
public interface StateRepository extends JpaRepository<State, Long> {
    //List<City> findAllByState(State state);
}
