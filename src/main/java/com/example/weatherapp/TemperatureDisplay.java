package com.example.weatherapp;

import javafx.scene.control.Label;

class TemperatureDisplay implements WeatherDisplay {
    private final Label label;

    public TemperatureDisplay(Label label) {
        this.label = label;
    }

    @Override
    public void display(String temperature, String currentConditions, String forecast) {
        label.setText("Temperature: " + temperature + "°C");
    }
}
