package com.labluetech.weather.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum WindSpeedUnitType {
    KM_PER_HOUR, MILE_PER_HOUR;

    @JsonCreator
    public static WindSpeedUnitType fromValue(String value) {
        for (WindSpeedUnitType unitType : WindSpeedUnitType.values()) {
            if (unitType.name().equalsIgnoreCase(value)) {
                return unitType;
            }
        }
        throw new IllegalArgumentException("Invalid or unsupported wind speed unit: " + value);

    }

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
