package com.labluetech.weather.converter;

import com.labluetech.weather.domain.WindSpeedUnitType;

import java.beans.PropertyEditorSupport;


public class WindSpeedUnitTypeConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String capitalized = text.toUpperCase();
        WindSpeedUnitType value = WindSpeedUnitType.valueOf(capitalized);
        setValue(value);
    }
}