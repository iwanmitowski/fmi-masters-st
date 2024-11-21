package com.example.p1_rent_a_car.services;

import com.example.p1_rent_a_car.database.repositories.CarRepository;
import com.example.p1_rent_a_car.database.repositories.CityRepository;
import com.example.p1_rent_a_car.database.repositories.ClientRepository;
import org.springframework.stereotype.Service;

@Service
public class CityService {
    private CityRepository cityRepository;

    public CityService(CityRepository cityRepository) {
        this.cityRepository = cityRepository;
    }

    public boolean doesCityExist(int cityId) {
        var cities = this.cityRepository.getAllCities();

        var cityExists = cities.stream()
            .anyMatch(city -> city.getId() == cityId);

        return cityExists;
    }
}
