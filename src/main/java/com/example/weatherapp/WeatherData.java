package com.example.weatherapp;

import java.util.ArrayList;
import java.util.List;

// Concrete subject class
class WeatherData implements WeatherSubject {
    private final List<WeatherDisplay> observers;
    private String temperatureData;
    private String currentConditionsData;
    private String forecastData;

    public WeatherData() {
        this.observers = new ArrayList<>();
        this.temperatureData = "";
        this.currentConditionsData = "";
        this.forecastData = "";
    }

    // Getter for temperatureData
    public String getTemperatureData() {
        return temperatureData;
    }

    // Getter for currentConditionsData
    public String getCurrentConditionsData() {
        return currentConditionsData;
    }

    // Getter for forecastData
    public String getForecastData() {
        return forecastData;
    }

    @Override
    public void registerObserver(WeatherDisplay observer) {
        observers.add(observer);
    }

    @Override
    public void removeObserver(WeatherDisplay observer) {
        observers.remove(observer);
    }

    @Override
    public void notifyObservers() {
        for (WeatherDisplay observer : observers) {
            observer.display(temperatureData, currentConditionsData, forecastData);
        }
    }

    // Method to update weather data and notify observers
    public void updateWeatherData(String temperature, String currentConditions, String forecast) {
        temperatureData = temperature;
        currentConditionsData = currentConditions;
        forecastData = forecast;

        // Notify observers
        notifyObservers();
    }
}
