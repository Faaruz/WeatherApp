package com.example.weatherapp;

import javafx.scene.control.Label;

class ForecastDisplay implements WeatherDisplay {
    private final Label label;

    public ForecastDisplay(Label label) {
        this.label = label;
    }

    @Override
    public void display(String temperature, String currentConditions, String forecast) {
        label.setText("Forecast: " + forecast);
    }
}
