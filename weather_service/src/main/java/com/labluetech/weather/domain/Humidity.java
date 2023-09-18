package com.labluetech.weather.domain;

import org.springframework.data.mongodb.core.mapping.Document;

import static org.springframework.util.Assert.notNull;


@Document(collection = "humidities")
public class Humidity extends WeatherMetric{

    private HumidityUnitType unit;

    private Humidity(Humidity.Builder builder) {
        super(builder);
        this.unit = builder.unit;
    }
    public Humidity(){}

    public static Humidity.Builder getBuilder() {
        return new Humidity.Builder();
    }
    public HumidityUnitType getUnit() {
        return unit;
    }


    public static class Builder extends WeatherMetric.Builder<Humidity, Humidity.Builder> {
        private HumidityUnitType unit;


        public Humidity.Builder unit(HumidityUnitType unit) {
            this.unit = unit;
            return this;
        }
        public Humidity build(){
            Humidity build = new Humidity(this);
            build.checkWeatherMetric(build.getTimestamp(), build.getDeviceId());
            build.checkValueAndUnit(build.getValue(), build.getUnit());
            return build;
        }

        protected Humidity.Builder getThis() {
            return this;
        }
    }

    private void checkValueAndUnit(Double value, HumidityUnitType unit) {
        notNull(value, "value cannot be null");
        notNull(unit, "unit cannot be null");
    }

}
