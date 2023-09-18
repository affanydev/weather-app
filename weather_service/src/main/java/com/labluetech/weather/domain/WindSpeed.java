package com.labluetech.weather.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import static org.springframework.util.Assert.notNull;


@Document(collection = "windSpeeds")
public class WindSpeed extends WeatherMetric{
    private WindSpeedUnitType unit;

    private WindSpeed(Builder builder) {
        super(builder);
        this.unit = builder.unit;
    }
    public WindSpeed(){}

    public static Builder getBuilder() {
        return new Builder();
    }
    public WindSpeedUnitType getUnit() {
        return unit;
    }


    public static class Builder extends WeatherMetric.Builder<WindSpeed, WindSpeed.Builder> {
        private WindSpeedUnitType unit;


        public Builder unit(WindSpeedUnitType unit) {
            this.unit = unit;
            return this;
        }
        public WindSpeed build(){
            WindSpeed build = new WindSpeed(this);
            build.checkWeatherMetric(build.getTimestamp(), build.getDeviceId());
            build.checkValueAndUnit(build.getValue(), build.getUnit());
            return build;
        }

        protected Builder getThis() {
            return this;
        }
    }

    private void checkValueAndUnit(Double value, WindSpeedUnitType unit) {
        notNull(value, "value cannot be null");
        notNull(unit, "unit cannot be null");
    }
}