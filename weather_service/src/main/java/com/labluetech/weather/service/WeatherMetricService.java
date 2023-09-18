package com.labluetech.weather.service;

import com.labluetech.weather.domain.*;
import com.labluetech.weather.dto.GetWeatherMetricsRequestDto;
import com.labluetech.weather.dto.GetWeatherStatisticsRequestDto;
import com.labluetech.weather.dto.SensorData;
import com.labluetech.weather.repository.HumidityRepository;
import com.labluetech.weather.repository.TemperatureRepository;
import com.labluetech.weather.repository.WeatherMetricRepository;
import com.labluetech.weather.repository.WindSpeedRepository;
import com.labluetech.weather.resource.WeatherMetricResource;
import com.labluetech.weather.resource.WeatherStatisticResource;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.time.Instant;
import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class WeatherMetricService {
    private final Logger LOGGER = LoggerFactory.getLogger(WeatherMetricService.class);
    private final HumidityRepository humidityRepository;
    private final TemperatureRepository temperatureRepository;
    private final WindSpeedRepository windSpeedRepository;


    @Autowired
    public WeatherMetricService(HumidityRepository humidityRepository, TemperatureRepository temperatureRepository, WindSpeedRepository windSpeedRepository) {
        this.humidityRepository = humidityRepository;
        this.temperatureRepository = temperatureRepository;
        this.windSpeedRepository = windSpeedRepository;
    }

    public Collection<WeatherMetricResource> getMetrics(GetWeatherMetricsRequestDto request) {
        List<WeatherMetricResource> resources = new ArrayList<WeatherMetricResource>();

        if(request.getMetricNames().contains(WeatherMetricType.HUMIDITY)){
            List<Humidity> metrics= (List<Humidity>) getMetrics(humidityRepository,request);
            if (metrics != null) {
                for (Humidity metric : metrics) {
                    WeatherMetricResource resource = new WeatherMetricResource();
                    resource.setMetric(WeatherMetricType.HUMIDITY);
                    resource.setTimestamp(metric.getTimestamp());
                    resource.setValue(metric.getValue());
                    resource.setUnit(metric.getUnit().name());
                    resources.add(resource);
                }
            }

        }

        if(request.getMetricNames().contains(WeatherMetricType.TEMPERATURE)){

            List<Temperature> metrics= (List<Temperature>) getMetrics(temperatureRepository,request);
            if (metrics != null) {
                for (Temperature metric : metrics) {
                    WeatherMetricResource resource = new WeatherMetricResource();
                    resource.setMetric(WeatherMetricType.TEMPERATURE);
                    resource.setTimestamp(metric.getTimestamp());
                    resource.setValue(metric.getValue());
                    resource.setUnit(metric.getUnit().name());
                    resources.add(resource);
                }
            }
        }

        if(request.getMetricNames().contains(WeatherMetricType.WIND_SPEED)){
            List<WindSpeed> metrics= (List<WindSpeed>) getMetrics(windSpeedRepository,request);
            if (metrics != null) {
                for (WindSpeed metric : metrics) {
                    WeatherMetricResource resource = new WeatherMetricResource();
                    resource.setMetric(WeatherMetricType.WIND_SPEED);
                    resource.setTimestamp(metric.getTimestamp());
                    resource.setValue(metric.getValue());
                    resource.setUnit(metric.getUnit().name());
                    resources.add(resource);
                }
            }
        }

        return resources;
    }



    public Collection<WeatherStatisticResource> getStatistics(GetWeatherStatisticsRequestDto request) {
        List<WeatherStatisticResource> resources = new ArrayList<WeatherStatisticResource>();

        if(request.getMetricNames().contains(WeatherMetricType.HUMIDITY)){
            List<? extends WeatherMetric> metrics= getMetrics(humidityRepository,request);
            List<Double> metricsValues=  metrics.stream()
                    .map(WeatherMetric::getValue)
                    .collect(Collectors.toList());
            WeatherStatisticResource resource = new WeatherStatisticResource();
            resource.setMetric(WeatherMetricType.HUMIDITY);
            addStatistics(request.getStatisticNames(),metricsValues, resource);
            resources.add(resource);
        }

        if(request.getMetricNames().contains(WeatherMetricType.TEMPERATURE)){
            List<? extends WeatherMetric> metrics=  getMetrics(temperatureRepository,request);

            List<Double> metricsValues=  metrics.stream()
                    .map(WeatherMetric::getValue)
                    .collect(Collectors.toList());

           WeatherStatisticResource resource = new WeatherStatisticResource();
            resource.setMetric(WeatherMetricType.TEMPERATURE);
            addStatistics(request.getStatisticNames(),metricsValues, resource);
            resources.add(resource);
        }

        if(request.getMetricNames().contains(WeatherMetricType.WIND_SPEED)){
            List<? extends WeatherMetric> metrics=getMetrics(windSpeedRepository,request);
            List<Double> metricsValues=  metrics.stream()
                    .map(WeatherMetric::getValue)
                    .collect(Collectors.toList());
            WeatherStatisticResource resource = new WeatherStatisticResource();
            resource.setMetric(WeatherMetricType.WIND_SPEED);
            addStatistics(request.getStatisticNames(),metricsValues, resource);
            resources.add(resource);
        }

        return resources;
    }



    private List<? extends WeatherMetric> getMetrics(WeatherMetricRepository repository, GetWeatherMetricsRequestDto request){

        if(request.getStart_date() != null && request.getEnd_date() != null && request.getSensorIds() !=null && !request.getSensorIds().isEmpty())   {
            return getMetrics(repository,request.getStart_date(), request.getEnd_date(),request.getSensorIds());
        }
        if(request.getStart_date() != null && request.getEnd_date() != null )   {
            return getMetrics(repository,request.getStart_date(), request.getEnd_date());
        }
        if(request.getSensorIds() !=null && !request.getSensorIds().isEmpty() )   {
            return getMetrics(repository,1000,request.getSensorIds());
        }
        return getMetrics(repository,1000);
    }

    private List<? extends WeatherMetric> getMetrics(WeatherMetricRepository repository, GetWeatherStatisticsRequestDto request){

        if(request.getStart_date() != null && request.getEnd_date() != null && request.getSensorIds() !=null && !request.getSensorIds().isEmpty())   {
            return getMetrics(repository,request.getStart_date(), request.getEnd_date(),request.getSensorIds());
        }
        if(request.getStart_date() != null && request.getEnd_date() != null )   {
            return getMetrics(repository,request.getStart_date(), request.getEnd_date());
        }
        if(request.getSensorIds() !=null && !request.getSensorIds().isEmpty() )   {
            return getMetrics(repository,1000,request.getSensorIds());
        }
        return getMetrics(repository,1000);
    }

    private List<? extends WeatherMetric> getMetrics(WeatherMetricRepository repository, ZonedDateTime startDate, ZonedDateTime endDate, List<String> sensorIds){
        List<? extends WeatherMetric> metrics = null;
        StringBuilder deviceIdsBuilder = new StringBuilder(" ");
        for (String sensorId : sensorIds) {
            deviceIdsBuilder.append("'").append(sensorId).append("',");
        }
        deviceIdsBuilder.setLength(deviceIdsBuilder.length() - 1); // Remove the trailing comma
        String query = "{'deviceId': {$in: ["+deviceIdsBuilder.toString()+"]}, 'timestamp': {$gte: ISODate('"+startDate.toString()+"'), $lte: ISODate('"+endDate.toString()+"')}}";
        metrics=repository.findByQuery(query);

        return metrics;
    }

    private List<? extends WeatherMetric> getMetrics(WeatherMetricRepository repository, ZonedDateTime startDate, ZonedDateTime endDate){
        List<? extends WeatherMetric> metrics = null;
        String query = "{'timestamp': {$gte: ISODate('"+startDate.toString()+"'), $lte: ISODate('"+endDate.toString()+"')}}";
        metrics=repository.findByQuery(query);
        return metrics;
    }

    private List<? extends WeatherMetric> getMetrics(WeatherMetricRepository repository, Integer numberOfLastValues , List<String> sensorIds){
        List<? extends WeatherMetric> metrics = null;
        Pageable pageable = PageRequest.of(0, numberOfLastValues);

        Page page=repository.findByDeviceIdIn(sensorIds,pageable);
        if(page!= null ){
            metrics = page.getContent().stream().toList();
        }
        return metrics;
    }

    private List<? extends WeatherMetric> getMetrics(WeatherMetricRepository repository, Integer numberOfLastValues ){
        List<? extends WeatherMetric> metrics = null;
        Pageable pageable = PageRequest.of(0, numberOfLastValues);
        Page page= repository.findAll(pageable);
        if(page!= null ){
            metrics = page.getContent().stream().toList();
        }

        return metrics;
    }

    private void addStatistics(List<DataStatisticType> statisticTypes,List<Double> metricsValues, WeatherStatisticResource resource ){

        if(statisticTypes.contains(DataStatisticType.SUM)){
            Double sum = getSum(metricsValues);
            resource.setSum(sum);
        }
        if(statisticTypes.contains(DataStatisticType.AVERAGE)){
            Double average = getAverage(metricsValues);
            resource.setAverage(average);
        }
        if(statisticTypes.contains(DataStatisticType.MAX)){
            Double max = getMax(metricsValues);
            resource.setMax(max);
        }
        if(statisticTypes.contains(DataStatisticType.MIN)){
            Double min = getMin(metricsValues);
            resource.setMin(min);
        }
    }

    private Double getSum(List<Double> values){
        return values.stream()
                .mapToDouble(Double::doubleValue)
                .sum();
    }

    private Double getAverage(List<Double> values){
        if (values.isEmpty()) {
            return null;
        }

        return values.stream()
                .mapToDouble(Double::doubleValue)
                .average().getAsDouble();
    }

    private Double getMax(List<Double> values){
        if (values.isEmpty()) {
            return null;
        }

        return values.stream()
                .mapToDouble(Double::doubleValue)
                .max().getAsDouble();
    }

    private Double getMin(List<Double> values){
        if (values.isEmpty()) {
        return null;
        }

        return values.stream()
                .mapToDouble(Double::doubleValue)
                .min().getAsDouble();
    }


    public void processSensorData(SensorData sensorData) {

        if(sensorData.getTemperature()!= null){
            temperatureRepository.save(
                    Temperature.getBuilder()
                            .deviceId(sensorData.getDeviceId())
                            .timestamp(Date.from(sensorData.getTimestamp().toInstant()))
                            .ttl(Date.from(Instant.now().plusSeconds(180)))
                            .value(sensorData.getTemperature())
                            .unit(TemperatureUnitType.CELSIUS)
                            .build()
            );
        }
        if(sensorData.getHumidity()!= null){

            humidityRepository.save(
                Humidity.getBuilder()
                        .deviceId(sensorData.getDeviceId())
                        .timestamp(Date.from(sensorData.getTimestamp().toInstant()))
                        .ttl(Date.from(Instant.now().plusSeconds(180)))
                        .value(sensorData.getHumidity())
                        .unit(HumidityUnitType.PERCENT)
                        .build()
            );

        }
        if(sensorData.getWindSpeed()!= null){
            windSpeedRepository.save(
                    WindSpeed.getBuilder()
                            .deviceId(sensorData.getDeviceId())
                            .timestamp(Date.from(sensorData.getTimestamp().toInstant()))
                            .ttl(Date.from(Instant.now().plusSeconds(180)))
                            .value(sensorData.getTemperature())
                            .unit(WindSpeedUnitType.KM_PER_HOUR)
                            .build()
            );

        }


    }















}
