package org.batch.batchcarsco2emission.entities;


import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Entity
@Data
@ToString
@AllArgsConstructor
@NoArgsConstructor
public class Car {

    @Id
    private Long id;
    private String model;
    private int modelYear;
    private String make;
    private String vehicleClass;
    private double engineSize;
    private int cylinders;
    private String transmission;
    private String fuelType;
    private double fuelConsumptionCity;
    private double fuelConsumptionHwy;
    private double fuelConsumptionComb;
    private int fuelConsumptionCombMpg;
    private int co2Emissions;

}
