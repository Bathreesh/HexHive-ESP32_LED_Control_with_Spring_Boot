package com.example.hexhive.service;

import com.example.hexhive.model.LedState;
import com.example.hexhive.repository.LedRepository;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class LedService {

    @Autowired
    private LedRepository repository;

    private LedState state;

    @PostConstruct
    public void init() {
        // Ensure initial state exists in database
        state = repository.findById(1L).orElse(new LedState());
        repository.save(state);
    }

    public LedState getState() {
        // Fetch from DB every time to sync with manual database updates
        state = repository.findById(1L).orElse(state);
        return state;
    }

    public void setDigital(boolean digital) {
        state.setDigital(digital);
        repository.save(state);
    }

    public void setPwm(int pwm) {
        state.setPwm(pwm);
        repository.save(state);
    }

    public void setAnalog(int analog) {
        state.setAnalog(analog);
        repository.save(state);
    }

    public void setSerialMessage(String message) {
        state.setSerialMessage(message);
        repository.save(state);
    }
}
