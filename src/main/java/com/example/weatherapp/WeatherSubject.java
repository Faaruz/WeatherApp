package com.example.weatherapp;

// Subject interface for Observer Pattern
interface WeatherSubject {
    void registerObserver(WeatherDisplay observer);

    void removeObserver(WeatherDisplay observer);

    void notifyObservers();
}
