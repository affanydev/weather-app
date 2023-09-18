package com.labluetech.weather.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.labluetech.weather.domain.WeatherMetricType;

import java.util.Date;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherMetricResource {
    private WeatherMetricType metric;
    private Date timestamp;
    private Double value;
    private String unit;

    public WeatherMetricType getMetric() {
        return metric;
    }

    public void setMetric(WeatherMetricType metric) {
        this.metric = metric;
    }

    public Date getTimestamp() {
        return timestamp;
    }

    public void setTimestamp(Date timestamp) {
        this.timestamp = timestamp;
    }

    public Double getValue() {
        return value;
    }

    public void setValue(Double value) {
        this.value = value;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }
}
