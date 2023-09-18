package com.labluetech.weather.dto;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.labluetech.weather.domain.CustomZonedDateTimeDeserializer;
import com.labluetech.weather.domain.DataStatisticType;
import com.labluetech.weather.domain.WeatherMetricType;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.ZonedDateTime;
import java.util.List;


@JsonInclude(JsonInclude.Include.NON_NULL)
public class GetWeatherStatisticsRequestDto {
    private List<WeatherMetricType> metricNames;
    private List<DataStatisticType> statisticNames;
    private List<String> sensorIds;
    @JsonDeserialize(using = CustomZonedDateTimeDeserializer.class)
    private ZonedDateTime start_date;
    @JsonDeserialize(using = CustomZonedDateTimeDeserializer.class)
    private ZonedDateTime end_date;


    public List<WeatherMetricType> getMetricNames() {
        return metricNames;
    }

    public void setMetricNames(List<WeatherMetricType> metricNames) {
        this.metricNames = metricNames;
    }

    public List<DataStatisticType> getStatisticNames() {
        return statisticNames;
    }

    public void setStatisticNames(List<DataStatisticType> statisticNames) {
        this.statisticNames = statisticNames;
    }

    public List<String> getSensorIds() {
        return sensorIds;
    }

    public void setSensorIds(List<String> sensorIds) {
        this.sensorIds = sensorIds;
    }

    public ZonedDateTime getStart_date() {
        return start_date;
    }

    public void setStart_date(ZonedDateTime start_date) {
        this.start_date = start_date;
    }

    public ZonedDateTime getEnd_date() {
        return end_date;
    }

    public void setEnd_date(ZonedDateTime end_date) {
        this.end_date = end_date;
    }
}
