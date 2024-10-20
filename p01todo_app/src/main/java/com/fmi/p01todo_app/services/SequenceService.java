package com.fmi.p01todo_app.services;

import org.springframework.stereotype.Service;

@Service
public class SequenceService {
    private int counter = 1;
    public int GetNextId() {
        return counter++;
    }
}
