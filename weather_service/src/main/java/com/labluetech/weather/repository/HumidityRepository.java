package com.labluetech.weather.repository;


import com.labluetech.weather.domain.Humidity;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface HumidityRepository extends WeatherMetricRepository<Humidity, String> {


}

