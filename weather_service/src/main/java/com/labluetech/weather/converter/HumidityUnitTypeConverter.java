package com.labluetech.weather.converter;

import com.labluetech.weather.domain.HumidityUnitType;

import java.beans.PropertyEditorSupport;


public class HumidityUnitTypeConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String capitalized = text.toUpperCase();
        HumidityUnitType value = HumidityUnitType.valueOf(capitalized);
        setValue(value);
    }
}