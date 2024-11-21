package com.example.p1_rent_a_car.controllers;

import com.example.p1_rent_a_car.entities.Offer;
import com.example.p1_rent_a_car.http.AppResponse;
import com.example.p1_rent_a_car.services.OfferService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
public class OfferController {
    private OfferService offerService;

    public OfferController(OfferService offerService) {
        this.offerService = offerService;
    }

    @PostMapping("/offers")
    public ResponseEntity<?> createOffer(@RequestBody Offer offer) {
        try {
            var result = this.offerService.create(offer);;

            if (!result) {
                return AppResponse.error()
                        .withMessage("Problem during insert")
                        .withCode(HttpStatus.BAD_REQUEST)
                        .build();
            }

            return AppResponse.success()
                    .withMessage("Offer created successfully")
                    .withCode(HttpStatus.CREATED)
                    .build();
        } catch (IllegalArgumentException e) {
            return AppResponse.error()
                    .withMessage(e.getMessage())
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
    }

    @GetMapping("/offers/client/{clientId}")
    public ResponseEntity<?> getAllOffersByClient(@PathVariable int clientId) {
        try {
            List<Offer> offers = offerService.getAllOffers(clientId);
            return AppResponse.success()
                    .withMessage("Offers retrieved successfully")
                    .withData(offers)
                    .build();
        } catch (IllegalArgumentException e) {
        return AppResponse.error()
                .withMessage(e.getMessage())
                .withCode(HttpStatus.BAD_REQUEST)
                .build();
        }
    }

    @GetMapping("/offers/{id}")
    public ResponseEntity<?> getOfferById(@PathVariable int id) {
        Offer offer = offerService.getById(id);
        if (offer == null) {
            return AppResponse.error()
                    .withMessage("Offer not found")
                    .withCode(HttpStatus.NOT_FOUND)
                    .build();
        }
        return AppResponse.success()
                .withMessage("Offer retrieved successfully")
                .withData(offer)
                .build();
    }

    @PutMapping("/offers/{offerId}/accept")
    public ResponseEntity<?> acceptOffer(@PathVariable int offerId) {
        boolean accepted = offerService.acceptOffer(offerId);
        if (!accepted) {
            return AppResponse.error()
                    .withMessage("Failed to accept offer")
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        return AppResponse.success()
                .withMessage("Offer accepted successfully")
                .build();
    }

    @DeleteMapping("/offers/{id}")
    public ResponseEntity<?> deleteOffer(@PathVariable int id) {
        boolean deleted = offerService.delete(id);
        if (!deleted) {
            return AppResponse.error()
                    .withMessage("Failed to delete offer")
                    .withCode(HttpStatus.BAD_REQUEST)
                    .build();
        }
        return AppResponse.success()
                .withMessage("Offer deleted successfully")
                .build();
    }
}