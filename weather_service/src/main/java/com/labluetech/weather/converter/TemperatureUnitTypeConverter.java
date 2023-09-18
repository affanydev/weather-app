package com.labluetech.weather.converter;

import com.labluetech.weather.domain.TemperatureUnitType;

import java.beans.PropertyEditorSupport;


public class TemperatureUnitTypeConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String capitalized = text.toUpperCase();
        TemperatureUnitType value = TemperatureUnitType.valueOf(capitalized);
        setValue(value);
    }
}