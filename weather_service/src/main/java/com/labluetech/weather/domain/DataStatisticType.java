package com.labluetech.weather.domain;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

public enum DataStatisticType {
    AVERAGE, MAX, MIN, SUM;

    @JsonCreator
    public static DataStatisticType fromValue(String value) {
        for (DataStatisticType unitType : DataStatisticType.values()) {
            if (unitType.name().equalsIgnoreCase(value)) {
                return unitType;
            }
        }
        throw new IllegalArgumentException("Invalid or unsupported data statistic type : " + value);

    }

    @JsonValue
    public String toJson() {
        return name().toLowerCase();
    }
}
