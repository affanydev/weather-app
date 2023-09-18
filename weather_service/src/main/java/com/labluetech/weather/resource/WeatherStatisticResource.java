package com.labluetech.weather.resource;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.labluetech.weather.domain.WeatherMetricType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class WeatherStatisticResource {

    private WeatherMetricType metric;
    private Double average;
    private Double sum;
    private Double max;
    private Double min;

    public WeatherMetricType getMetric() {
        return metric;
    }

    public void setMetric(WeatherMetricType metric) {
        this.metric = metric;
    }


    public Double getAverage() {
        return average;
    }

    public void setAverage(Double average) {
        this.average = average;
    }

    public Double getSum() {
        return sum;
    }

    public void setSum(Double sum) {
        this.sum = sum;
    }

    public Double getMax() {
        return max;
    }

    public void setMax(Double max) {
        this.max = max;
    }

    public Double getMin() {
        return min;
    }

    public void setMin(Double min) {
        this.min = min;
    }
}
