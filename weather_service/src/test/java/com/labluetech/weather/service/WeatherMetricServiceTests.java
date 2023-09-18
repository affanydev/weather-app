package com.labluetech.weather.service;


import com.labluetech.weather.domain.*;
import com.labluetech.weather.dto.GetWeatherMetricsRequestDto;
import com.labluetech.weather.repository.HumidityRepository;
import com.labluetech.weather.repository.TemperatureRepository;
import com.labluetech.weather.repository.WeatherMetricRepository;
import com.labluetech.weather.resource.WeatherMetricResource;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import static org.mockito.Mockito.when;

@DataMongoTest
@ExtendWith(MockitoExtension.class)
public class WeatherMetricServiceTests {

    @Mock
    private TemperatureRepository temperatureRepository;
    @Mock
    private HumidityRepository humidityRepository;

    @Mock
    private WeatherMetricRepository weatherMetricRepository;


    @InjectMocks
    private WeatherMetricService weatherMetricService;


    @Test
    public void WeatherMetricService_GetMetricsForAllDevices() {
        Temperature temperature = Temperature.getBuilder()
                .deviceId("df246631-f7a7-4407-a8ce-8a761093416c")
                .name(WeatherMetricType.TEMPERATURE)
                .timestamp(Date.from(ZonedDateTime.parse("2023-09-05T00:00:00Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC)).toInstant()))
                .value(11.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(TemperatureUnitType.CELSIUS)
                .build();
        Temperature temperature2 = Temperature.getBuilder()
                .deviceId("c92c9937-9b18-43a6-b800-17912614ff46")
                .name(WeatherMetricType.TEMPERATURE)
                .timestamp(Date.from(ZonedDateTime.parse("2023-09-01T01:00:00Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC)).toInstant()))
                .value(19.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(TemperatureUnitType.CELSIUS)
                .build();

        Humidity humidity = Humidity.getBuilder()
                .deviceId("df246631-f7a7-4407-a8ce-8a761093416c")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(ZonedDateTime.parse("2023-09-02T00:13:00Z", DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC)).toInstant()))
                .value(29.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();


        Pageable pageable = PageRequest.of(0, 1000);


        when(weatherMetricRepository.findAll(Mockito.any(Pageable.class))).thenAnswer(invocation -> {
            if (invocation.getArgument(0) == temperatureRepository) {
                return new PageImpl<>(List.of(temperature,temperature2), pageable, 2);
            } else if (invocation.getArgument(0) == humidityRepository) {
                return new PageImpl<>(List.of(humidity), pageable, 1);
            } else {
                return new PageImpl<>(List.of(), pageable, 0);
            }
        });

        when(temperatureRepository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(List.of(temperature,temperature2), pageable, 2));
        when(humidityRepository.findAll(Mockito.any(Pageable.class))).thenReturn(new PageImpl<>(List.of(humidity), pageable, 1));

        GetWeatherMetricsRequestDto request = new GetWeatherMetricsRequestDto();
        request.setMetricNames(List.of(WeatherMetricType.TEMPERATURE, WeatherMetricType.HUMIDITY));


        Collection<WeatherMetricResource> resources   = weatherMetricService.getMetrics( request);

        Assertions.assertThat(resources).isNotNull();
        Assertions.assertThat(resources.size()).isEqualTo(3);

    }
}
