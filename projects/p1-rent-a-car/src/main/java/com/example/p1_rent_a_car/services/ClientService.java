package com.example.p1_rent_a_car.services;

import com.example.p1_rent_a_car.database.repositories.ClientRepository;
import com.example.p1_rent_a_car.entities.Client;
import org.springframework.stereotype.Service;

@Service
public class ClientService {
    private ClientRepository clientRepository;

    public ClientService(ClientRepository clientRepository) {
        this.clientRepository = clientRepository;
    }

    public Client getById(int id) {
        return this.clientRepository.getById(id);
    }
}
