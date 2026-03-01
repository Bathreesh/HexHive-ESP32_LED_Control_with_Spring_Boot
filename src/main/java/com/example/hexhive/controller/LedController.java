package com.example.hexhive.controller;

import com.example.hexhive.model.LedState;
import com.example.hexhive.service.LedService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api")
public class LedController {

    @Autowired
    private LedService service;

    @GetMapping("/state")
    public LedState getState() {
        return service.getState();
    }

    @PostMapping("/digital")
    public void setDigital(@RequestParam boolean value) {
        service.setDigital(value);
    }

    @PostMapping("/pwm")
    public void setPwm(@RequestParam int value) {
        service.setPwm(value);
    }

    @PostMapping("/analog")
    public void setAnalog(@RequestParam int value) {
        service.setAnalog(value);
    }

    @PostMapping("/log")
    public void setSerialMessage(@RequestParam String message) {
        service.setSerialMessage(message);
    }
}
