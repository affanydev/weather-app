package com.labluetech.weather.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import static org.springframework.util.Assert.notNull;


@Document(collection = "temperatures")
public class Temperature extends WeatherMetric{
    private TemperatureUnitType unit;

    private Temperature(Temperature.Builder builder) {
        super(builder);
        this.unit = builder.unit;
    }
    public Temperature(){}

    public static Temperature.Builder getBuilder() {
        return new Temperature.Builder();
    }
    public TemperatureUnitType getUnit() {
        return unit;
    }


    public static class Builder extends WeatherMetric.Builder<Temperature, Temperature.Builder> {
        private TemperatureUnitType unit;


        public Temperature.Builder unit(TemperatureUnitType unit) {
            this.unit = unit;
            return this;
        }
        public Temperature build(){
            Temperature build = new Temperature(this);
            build.checkWeatherMetric(build.getTimestamp(), build.getDeviceId());
            build.checkValueAndUnit(build.getValue(), build.getUnit());
            return build;
        }

        protected Temperature.Builder getThis() {
            return this;
        }
    }

    private void checkValueAndUnit(Double value, TemperatureUnitType unit) {
        notNull(value, "value cannot be null");
        notNull(unit, "unit cannot be null");
    }
}