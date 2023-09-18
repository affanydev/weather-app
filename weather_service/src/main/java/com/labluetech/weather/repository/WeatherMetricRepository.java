package com.labluetech.weather.repository;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.Query;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;
import java.time.ZonedDateTime;
import java.util.Date;
import java.util.List;

@NoRepositoryBean
public interface WeatherMetricRepository<T, ID extends Serializable>   extends MongoRepository<T, ID>{

    Page findByDeviceIdIn(List<String> deviceIds,Pageable pageable);



    @Query("?0")
    List<T> findByQuery(String quey);

}


