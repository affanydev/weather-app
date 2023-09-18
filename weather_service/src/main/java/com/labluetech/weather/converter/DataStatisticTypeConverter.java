package com.labluetech.weather.converter;

import com.labluetech.weather.domain.DataStatisticType;

import java.beans.PropertyEditorSupport;

public class DataStatisticTypeConverter extends PropertyEditorSupport {
    @Override
    public void setAsText(String text) throws IllegalArgumentException {
        String capitalized = text.toUpperCase();
        DataStatisticType value = DataStatisticType.valueOf(capitalized);
        setValue(value);
    }
}