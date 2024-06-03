package org.batch.batchcarsco2emission.batch;

import org.batch.batchcarsco2emission.entities.Car;
import org.springframework.batch.item.ItemProcessor;

public class CartProcessor implements ItemProcessor<Car,Car> {

    @Override
    public Car process(Car car) {
        return car;
    }
}