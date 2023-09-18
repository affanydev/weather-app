package com.labluetech.weather.controller;

import com.labluetech.weather.converter.*;
import com.labluetech.weather.domain.*;
import com.labluetech.weather.dto.GetWeatherMetricsRequestDto;
import com.labluetech.weather.dto.GetWeatherStatisticsRequestDto;
import com.labluetech.weather.dto.SensorData;
import com.labluetech.weather.resource.WeatherMetricResource;
import com.labluetech.weather.resource.WeatherStatisticResource;
import com.labluetech.weather.service.WeatherMetricService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.*;

import java.util.Collection;


@RestController
@RequestMapping(value = "/weather/metrics")
public class WeatherMetricsController {
    private final Logger LOGGER = LoggerFactory.getLogger(WeatherMetricsController.class);

    private final WeatherMetricService  service;


    @Autowired
    public WeatherMetricsController(WeatherMetricService service) {
        this.service = service;
    }

    @InitBinder
    public void initBinder(WebDataBinder dataBinder) {
        dataBinder.registerCustomEditor(DataStatisticType.class, new DataStatisticTypeConverter());
        dataBinder.registerCustomEditor(HumidityUnitType.class, new HumidityUnitTypeConverter());
        dataBinder.registerCustomEditor(TemperatureUnitType.class, new TemperatureUnitTypeConverter());
        dataBinder.registerCustomEditor(WeatherMetricType.class, new WeatherMetricTypeConverter());
        dataBinder.registerCustomEditor(WindSpeedUnitType.class, new WindSpeedUnitTypeConverter());
    }


    @RequestMapping(value="", method= RequestMethod.GET)
    public ResponseEntity<?> getWeatherMetrics(GetWeatherMetricsRequestDto request) {
        Collection<WeatherMetricResource> resources = service.getMetrics(request);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }


    @RequestMapping(value="statistics", method= RequestMethod.GET)
    public ResponseEntity<?> getWeatherStatistics(GetWeatherStatisticsRequestDto request) {

        if (request.getStatisticNames() == null || request.getStatisticNames().isEmpty() ) {
            return ResponseEntity.badRequest().body("Invalid or missing statistic names");
        }

        Collection<WeatherStatisticResource> resources = service.getStatistics(request);
        return new ResponseEntity<>(resources, HttpStatus.OK);
    }



    @RequestMapping(value="", method= RequestMethod.POST)
    public ResponseEntity<String> createWeatherMetric(@RequestBody SensorData data) {
System.out.print("createWeatherMetric ");
        System.out.print(data);
        System.out.print("createWeatherMetric ");
        if (data.getDeviceId() == null ) {
            return ResponseEntity.badRequest().body("Invalid or missing device ID");
        }

        service.processSensorData(data);
        return new ResponseEntity<>("Weather metrics received successfully", HttpStatus.CREATED);

    }


}
