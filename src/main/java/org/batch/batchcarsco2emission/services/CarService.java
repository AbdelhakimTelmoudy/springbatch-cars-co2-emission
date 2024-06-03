package org.batch.batchcarsco2emission.services;

import org.batch.batchcarsco2emission.entities.Car;

import java.util.List;

public interface CarService {

    List<Car> getAllCars();

    Car getCarById(Long id);

    Car addCar(Car car);

    Car updateCar(Long id, Car car);

    void deleteCar(Long id);
}
