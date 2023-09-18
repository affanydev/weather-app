package com.labluetech.weather.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum TemperatureUnitType {

    CELSIUS;

    @JsonCreator
    public static TemperatureUnitType fromValue(String value) {
        for (TemperatureUnitType unitType : TemperatureUnitType.values()) {
            if (unitType.name().equalsIgnoreCase(value)) {
                return unitType;
            }
        }
        throw new IllegalArgumentException("Invalid or unsupported temperature unit: " + value);

    }

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }


}
