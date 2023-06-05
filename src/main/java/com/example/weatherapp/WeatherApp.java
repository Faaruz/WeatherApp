package com.example.weatherapp;

import javafx.application.Application;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Alert;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.util.ArrayList;
import java.util.List;

// WeatherDisplay interface with template method
interface WeatherDisplay {
    void display(String temperature, String currentConditions, String forecast);
}

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

// Subject interface for Observer Pattern
interface WeatherSubject {
    void registerObserver(WeatherDisplay observer);

    void removeObserver(WeatherDisplay observer);

    void notifyObservers();
}

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

public class WeatherApp extends Application {
    private List<TextField> temperatureFields;
    private List<TextField> currentConditionsFields;
    private List<TextField> forecastFields;

    // Create labels for displaying weather data
    private Label temperatureLabel = new Label();
    private Label currentConditionsLabel = new Label();
    private Label forecastLabel = new Label();

    // Create display objects with labels
    private WeatherDisplay temperatureDisplay = new TemperatureDisplay(temperatureLabel);
    private WeatherDisplay currentConditionsDisplay = new CurrentConditionsDisplay(currentConditionsLabel);
    private WeatherDisplay forecastDisplay = new ForecastDisplay(forecastLabel);

    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage primaryStage) {
        temperatureFields = new ArrayList<>();
        currentConditionsFields = new ArrayList<>();
        forecastFields = new ArrayList<>();

        primaryStage.setTitle("Weather App");

        // Create subject object
        WeatherData weatherData = new WeatherData();

        // Register observers
        weatherData.registerObserver(temperatureDisplay);
        weatherData.registerObserver(currentConditionsDisplay);
        weatherData.registerObserver(forecastDisplay);

        // Set initial test data
        String initialTemperature = "25";
        String initialCurrentConditions = "Sunny";
        String initialForecast = "Warm and clear";
        weatherData.updateWeatherData(initialTemperature, initialCurrentConditions, initialForecast);


        // Update button
        Button updateButton = new Button("Update Weather Data");
        updateButton.setOnAction(e -> showUpdateDialog(weatherData));

        // Create main layout
        VBox layout = new VBox(10);
        layout.setPadding(new Insets(10));
        layout.getChildren().addAll(updateButton, temperatureLabel, currentConditionsLabel, forecastLabel);

        Scene scene = new Scene(layout, 300, 200);
        primaryStage.setScene(scene);
        primaryStage.show();
    }

    private void showUpdateDialog(WeatherData weatherData) {
        // Create dialog stage
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Update Weather Data");

        // Create input fields with initial values from WeatherData
        TextField temperatureField = new TextField(weatherData.getTemperatureData());
        TextField currentConditionsField = new TextField(weatherData.getCurrentConditionsData());
        TextField forecastField = new TextField(weatherData.getForecastData());

        // Create update button
        Button updateButton = new Button("Update");
        updateButton.setOnAction(e -> {
            String temperature = temperatureField.getText();
            String currentConditions = currentConditionsField.getText();
            String forecast = forecastField.getText();

            // Update weather data
            weatherData.updateWeatherData(temperature, currentConditions, forecast);

            // Update input fields with new data in all dialogs
            for (TextField textField : temperatureFields) {
                textField.setText(weatherData.getTemperatureData());
            }
            for (TextField textField : currentConditionsFields) {
                textField.setText(weatherData.getCurrentConditionsData());
            }
            for (TextField textField : forecastFields) {
                textField.setText(weatherData.getForecastData());
            }
        });

        // Create remove buttons
        Button removeTemperatureButton = new Button("Remove Temperature");
        removeTemperatureButton.setOnAction(e -> {
            weatherData.removeObserver(temperatureDisplay);
        });

        Button removeConditionsButton = new Button("Remove Conditions");
        removeConditionsButton.setOnAction(e -> {
            weatherData.removeObserver(currentConditionsDisplay);
        });

        Button removeForecastButton = new Button("Remove Forecast");
        removeForecastButton.setOnAction(e -> {
            weatherData.removeObserver(forecastDisplay);
        });

        // Create dialog layout
        VBox dialogLayout = new VBox(10);
        dialogLayout.setPadding(new Insets(10));
        dialogLayout.getChildren().addAll(
                new Label("Temperature:"),
                temperatureField,
                new Label("Current Conditions:"),
                currentConditionsField,
                new Label("Forecast:"),
                forecastField,
                updateButton,
                removeTemperatureButton,
                removeConditionsButton,
                removeForecastButton
        );

        Scene dialogScene = new Scene(dialogLayout, 300, 300);
        dialogStage.setScene(dialogScene);

        // Add the text fields to the corresponding lists
        temperatureFields.add(temperatureField);
        currentConditionsFields.add(currentConditionsField);
        forecastFields.add(forecastField);

        // Show the dialog and wait until it is closed
        dialogStage.showAndWait();

        // Remove the text fields from the lists after the dialog is closed
        temperatureFields.remove(temperatureField);
        currentConditionsFields.remove(currentConditionsField);
        forecastFields.remove(forecastField);
    }
}
