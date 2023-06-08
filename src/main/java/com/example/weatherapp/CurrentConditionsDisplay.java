package com.example.weatherapp;

import javafx.scene.control.Label;

// Concrete display classes
class CurrentConditionsDisplay implements WeatherDisplay {
    private final Label label;

    public CurrentConditionsDisplay(Label label) {
        this.label = label;
    }

    @Override
    public void display(String temperature, String currentConditions, String forecast) {
        label.setText("Current conditions: " + currentConditions);
    }
}
