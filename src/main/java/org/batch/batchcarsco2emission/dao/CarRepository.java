package org.batch.batchcarsco2emission.dao;

import org.batch.batchcarsco2emission.entities.Car;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CarRepository extends JpaRepository<Car, Long> {
}
