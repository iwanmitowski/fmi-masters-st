package com.example.p1_rent_a_car.services;

import com.example.p1_rent_a_car.database.repositories.OfferRepository;
import com.example.p1_rent_a_car.entities.Car;
import com.example.p1_rent_a_car.entities.Client;
import com.example.p1_rent_a_car.entities.Offer;
import org.springframework.stereotype.Service;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class OfferService {
    private final int ACCEPT_OFFER_STATUS_ID = 2;

    private OfferRepository offerRepository;
    private CarService carService;
    private ClientService clientService;

    public OfferService(
        OfferRepository offerRepository,
        CarService carService,
        ClientService clientService
        ) {
        this.offerRepository = offerRepository;
        this.carService = carService;
        this.clientService = clientService;
    }

    public boolean create(Offer offer) throws IllegalArgumentException {
        var client = this.clientService.getById(offer.getClientId());
        if (client == null) {
            throw new IllegalArgumentException("Invalid client id");
        }

        var car = this.carService.getById(offer.getCarId());
        if (car == null) {
            throw new IllegalArgumentException("Invalid car id");
        }

        if (!car.isAvailability()) {
            throw new IllegalArgumentException("Car is not available");
        }

        car.setAvailability(false);
        this.carService.update(car);

        if (offer.getStartDate() == null || offer.getEndDate() == null) {
            throw new IllegalArgumentException("Missing start or end date");
        }

        var startDate = LocalDate.parse(offer.getStartDate());
        var endDate = LocalDate.parse(offer.getEndDate());

        if (endDate.isBefore(startDate)) {
            throw new IllegalArgumentException("End date cannot be before start date");
        }

        var totalPrice = calculateTotalPrice(offer, car, client);
        offer.setTotalPrice(totalPrice);

        return this.offerRepository.create(offer);
    }

    public List<Offer> getAllOffers(int clientId) {
        var client = this.clientService.getById(clientId);

        if (client == null) {
            throw new IllegalArgumentException("Invalid client id");
        }

        return this.offerRepository.getAll(clientId);
    }

    public Offer getById(int id) {
        return this.offerRepository.getById(id);
    }

    public boolean acceptOffer(int offerId) {
        return this.offerRepository.updateStatus(offerId, ACCEPT_OFFER_STATUS_ID);
    }

    public boolean delete(int id) {
        return this.offerRepository.delete(id);
    }

    private double calculateTotalPrice(Offer offer, Car car, Client client) {
        var startDate = LocalDate.parse(offer.getStartDate());
        var endDate = LocalDate.parse(offer.getEndDate());

        var totalDays = ChronoUnit.DAYS.between(startDate, endDate) + 1;
        var basePrice = car.getPricePerDay() * totalDays;

        var weekendDays = countWeekendDays(startDate, endDate);
        var weekendTax = weekendDays * car.getPricePerDay() * 0.1;
        var accidentTax = client.isAccidentHistory() ? 200.0 : 0.0;

        return basePrice + weekendTax + accidentTax;
    }

    private long countWeekendDays(LocalDate startDate, LocalDate endDate) {
        return startDate.datesUntil(endDate.plusDays(1))
            .filter(date ->
                date.getDayOfWeek() == DayOfWeek.SATURDAY ||
                date.getDayOfWeek() == DayOfWeek.SUNDAY)
            .count();
    }
}
