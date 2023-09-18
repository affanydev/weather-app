package com.labluetech.weather.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum HumidityUnitType {

    PERCENT;

    @JsonCreator
    public static HumidityUnitType fromValue(String value) {
        for (HumidityUnitType unitType : HumidityUnitType.values()) {
            if (unitType.name().equalsIgnoreCase(value)) {
                return unitType;
            }
        }
        throw new IllegalArgumentException("Invalid or unsupported humidity unit: " + value);

    }

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }

}
