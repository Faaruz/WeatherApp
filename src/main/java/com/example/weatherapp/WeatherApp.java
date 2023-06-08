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
        Stage dialogStage = new Stage();
        dialogStage.setTitle("Update Weather Data");

        TextField temperatureField = createTextField(weatherData.getTemperatureData());
        TextField currentConditionsField = createTextField(weatherData.getCurrentConditionsData());
        TextField forecastField = createTextField(weatherData.getForecastData());

        Button updateButton = createButton("Update");
        updateButton.setOnAction(e -> {
            String temperature = temperatureField.getText();
            String currentConditions = currentConditionsField.getText();
            String forecast = forecastField.getText();

            weatherData.updateWeatherData(temperature, currentConditions, forecast);

            updateInputFields(weatherData);
        });

        Button subscribeTemperatureButton = createButton("Subscribe Temperature");
        subscribeTemperatureButton.setOnAction(e -> {
            System.out.println("Temperature Observer added");
            weatherData.registerObserver(temperatureDisplay);
        });

        Button subscribeConditionsButton = createButton("Subscribe Conditions");
        subscribeConditionsButton.setOnAction(e -> {
            System.out.println("Current Conditions Observer added");
            weatherData.registerObserver(currentConditionsDisplay);
        });

        Button subscribeForecastButton = createButton("Subscribe Forecast");
        subscribeForecastButton.setOnAction(e -> {
            System.out.println("Forecast Observer added");
            weatherData.registerObserver(forecastDisplay);
        });

        Button removeTemperatureButton = createButton("Remove Temperature");
        removeTemperatureButton.setOnAction(e -> {
            System.out.println("Temperature Observer removed");
            weatherData.removeObserver(temperatureDisplay);
        });

        Button removeConditionsButton = createButton("Remove Conditions");
        removeConditionsButton.setOnAction(e -> {
            System.out.println("Current Conditions Observer removed");
            weatherData.removeObserver(currentConditionsDisplay);
        });

        Button removeForecastButton = createButton("Remove Forecast");
        removeForecastButton.setOnAction(e -> {
            System.out.println("Forecast Observer removed");
            weatherData.removeObserver(forecastDisplay);
        });

        VBox dialogLayout = new VBox(10);
        dialogLayout.setPadding(new Insets(10));
        dialogLayout.getChildren().addAll(
                createLabel("Temperature:"), temperatureField,
                createLabel("Current Conditions:"), currentConditionsField,
                createLabel("Forecast:"), forecastField,
                updateButton,
                subscribeTemperatureButton, subscribeConditionsButton, subscribeForecastButton,
                removeTemperatureButton, removeConditionsButton, removeForecastButton
        );

        Scene dialogScene = new Scene(dialogLayout, 300, 500);
        dialogStage.setScene(dialogScene);

        temperatureFields.add(temperatureField);
        currentConditionsFields.add(currentConditionsField);
        forecastFields.add(forecastField);

        dialogStage.showAndWait();

        temperatureFields.remove(temperatureField);
        currentConditionsFields.remove(currentConditionsField);
        forecastFields.remove(forecastField);
    }

    private TextField createTextField(String initialValue) {
        TextField textField = new TextField(initialValue);
        textField.setPrefWidth(200);
        return textField;
    }

    private Button createButton(String text) {
        Button button = new Button(text);
        button.setPrefWidth(200);
        return button;
    }

    private Label createLabel(String text) {
        Label label = new Label(text);
        label.setStyle("-fx-font-weight: bold");
        return label;
    }

    private void updateInputFields(WeatherData weatherData) {
        for (TextField textField : temperatureFields) {
            textField.setText(weatherData.getTemperatureData());
        }
        for (TextField textField : currentConditionsFields) {
            textField.setText(weatherData.getCurrentConditionsData());
        }
        for (TextField textField : forecastFields) {
            textField.setText(weatherData.getForecastData());
        }
    }
}
