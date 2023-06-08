package com.example.weatherapp;

// WeatherDisplay interface with template method
interface WeatherDisplay {
    void display(String temperature, String currentConditions, String forecast);
}