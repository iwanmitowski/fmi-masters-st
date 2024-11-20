package com.example.p1_rent_a_car.services;

import com.example.p1_rent_a_car.database.repositories.CarRepository;
import com.example.p1_rent_a_car.database.repositories.CityRepository;
import com.example.p1_rent_a_car.entities.Car;
import com.example.p1_rent_a_car.entities.City;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private CarRepository carRepository;
    private CityRepository cityRepository;

    public CarService(
        CarRepository carRepository,
        CityRepository cityRepository) {
        this.carRepository = carRepository;
        this.cityRepository = cityRepository;
    }

    public boolean create(Car car) throws IllegalArgumentException {
        boolean cityExists = doesCityExist(car);

        if (!cityExists) {
            throw new IllegalArgumentException("Invalid city with id: " + car.getCityId());
        }

        return this.carRepository.create(car);
    }

    public List<Car> getAllCars() {
       return this.carRepository.getAllCars();
    }

    public Car getById(int id) {
        return this.carRepository.getById(id);
    }

    public Car update(Car car) {
        boolean cityExists = doesCityExist(car);

        if (!cityExists) {
            throw new IllegalArgumentException("Invalid city with id: " + car.getCityId());
        }

        return this.carRepository.update(car);
    }

    public boolean delete(int id) {
        return this.carRepository.delete(id);
    }

    private boolean doesCityExist(Car car) {
        var cities = this.cityRepository.getAllCities();

        boolean cityExists = cities.stream()
            .anyMatch(city -> city.getId() == car.getCityId());

        return cityExists;
    }
}
