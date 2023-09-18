package com.labluetech.weather.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WeatherMetricType {

    TEMPERATURE, HUMIDITY, WIND_SPEED;

    @JsonCreator
    public static WeatherMetricType fromValue(String value) {
        for (WeatherMetricType unitType : WeatherMetricType.values()) {
            if (unitType.name().equalsIgnoreCase(value)) {
                return unitType;
            }
        }
        throw new IllegalArgumentException("Invalid or unsupported weather metric type: " + value);

    }

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
