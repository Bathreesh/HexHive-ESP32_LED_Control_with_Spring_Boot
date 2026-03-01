package com.example.hexhive.model;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;

/**
 * LedState — Simple in-memory state object for Virtual LED Control.
 *
 * No JPA/database needed for this version.
 * The state is held in-memory inside LedService (singleton bean).
 *
 * Fields:
 * digital → boolean : LED ON (true) / OFF (false)
 * pwm → int : Brightness 0–255 (PWM duty cycle)
 * analog → int : Simulated sensor reading 0–1023
 */
@Entity
public class LedState {

    @Id
    private Long id = 1L; // Always use ID 1 for our single state record

    private boolean digital; // Digital ON/OFF signal
    private int pwm; // PWM duty cycle (0–255)
    private int analog; // Analog sensor value (0–1023)
    private String serialMessage; // Latest serial log from ESP32

    public LedState() {
        this.digital = false;
        this.pwm = 0;
        this.analog = 512;
        this.serialMessage = "System Starting...";
    }

    public LedState(boolean digital, int pwm, int analog, String serialMessage) {
        this.digital = digital;
        this.pwm = pwm;
        this.analog = analog;
        this.serialMessage = serialMessage;
    }

    // --- Getters & Setters ---

    public boolean isDigital() {
        return digital;
    }

    public void setDigital(boolean digital) {
        this.digital = digital;
    }

    public int getPwm() {
        return pwm;
    }

    public void setPwm(int pwm) {
        this.pwm = pwm;
    }

    public int getAnalog() {
        return analog;
    }

    public void setAnalog(int analog) {
        this.analog = analog;
    }

    public String getSerialMessage() {
        return serialMessage;
    }

    public void setSerialMessage(String serialMessage) {
        this.serialMessage = serialMessage;
    }
}
