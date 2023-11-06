package com.badals.checkout.repository;

import com.badals.checkout.domain.Point;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PointRepository extends JpaRepository<Point, Long>{
    List<Point> findAllByCustomerId(Long customerId);
}
