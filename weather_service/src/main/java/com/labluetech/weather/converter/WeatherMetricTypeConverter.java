package com.labluetech.weather.converter;

import com.labluetech.weather.domain.WeatherMetricType;

import java.beans.PropertyEditorSupport;


public class WeatherMetricTypeConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String capitalized = text.toUpperCase();
        WeatherMetricType value = WeatherMetricType.valueOf(capitalized);
        setValue(value);
    }
}