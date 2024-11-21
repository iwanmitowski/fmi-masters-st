package com.example.p1_rent_a_car.services;

import com.example.p1_rent_a_car.database.repositories.CarRepository;
import com.example.p1_rent_a_car.entities.Car;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CarService {
    private CarRepository carRepository;
    private ClientService clientService;
    private CityService cityService;

    public CarService(
        CarRepository carRepository,
        ClientService clientService,
        CityService cityService) {
        this.carRepository = carRepository;
        this.clientService = clientService;
        this.cityService = cityService;
    }

    public boolean create(Car car) throws IllegalArgumentException {
        boolean cityExists = this.cityService.doesCityExist(car.getCityId());

        if (!cityExists) {
            throw new IllegalArgumentException("Invalid city with id: " + car.getCityId());
        }

        return this.carRepository.create(car);
    }

    public List<Car> getAllCars(int clientId) {
        var client = this.clientService.getById(clientId);

        if (client == null) {
            throw new IllegalArgumentException("Invalid client id");
        }

        return this.carRepository.getAllCars(clientId);
    }

    public Car getById(int id) {
        return this.carRepository.getById(id);
    }

    public Car update(Car car) {
        boolean cityExists = this.cityService.doesCityExist(car.getCityId());

        if (!cityExists) {
            throw new IllegalArgumentException("Invalid city with id: " + car.getCityId());
        }

        return this.carRepository.update(car);
    }

    public boolean delete(int id) {
        return this.carRepository.delete(id);
    }


}
