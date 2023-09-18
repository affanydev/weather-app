package com.labluetech.weather.repository;


import com.labluetech.weather.domain.Humidity;
import com.labluetech.weather.domain.HumidityUnitType;
import com.labluetech.weather.domain.WeatherMetricType;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.data.mongo.DataMongoTest;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.time.Instant;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@DataMongoTest
@ExtendWith(SpringExtension.class)
public class WeatherMetricRepositoryTests {

    @Autowired
    private HumidityRepository humidityRepository;


    @Test
    public void HumidityRepository_SaveAll_ReturnSavedHumidity() {


        //Arrange
        Humidity humidity = Humidity.getBuilder()
                .deviceId("996d99f1-9948-42aa-b072-6fe570472299")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(Instant.now()))
                .value(67.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();

        //Act
        Humidity savedHumidity = humidityRepository.save(humidity);

        //Assert
        Assertions.assertThat(savedHumidity).isNotNull();
        Assertions.assertThat(savedHumidity.getId()).isNotNull();
        Assertions.assertThat(savedHumidity.getUnit()).isEqualByComparingTo(HumidityUnitType.PERCENT);

        //clear
        humidityRepository.deleteById(savedHumidity.getId());
    }

    @Test
    public void HumidityRepository_GetAll_ReturnMoreThenOneHumidity() {

        //clear
        humidityRepository.deleteAll();


        //Arrange
        Humidity humidity = Humidity.getBuilder()
                .deviceId("996d99f1-9948-42aa-b072-6fe570472299")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(Instant.now()))
                .value(67.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();


        Humidity humidity2 = Humidity.getBuilder()
                .deviceId("996d99f1-9948-42aa-b072-6fe570472299")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(Instant.now()))
                .value(70.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();


        //Act
        Humidity savedHumidity = humidityRepository.save(humidity);
        Humidity savedHumidity2 = humidityRepository.save(humidity2);

        List<Humidity> HumiditiesList = humidityRepository.findAll();

        Assertions.assertThat(HumiditiesList).isNotNull();
        Assertions.assertThat(HumiditiesList.size()).isEqualTo(2);


        //clear
        humidityRepository.deleteAll();
    }



    @Test
    public void HumidityRepository_findByDeviceIdIn_WithSizeInPageable() {

        //clear
        humidityRepository.deleteAll();


        //Arrange

        List<String> deviceIds =new ArrayList<>();
        deviceIds.add("ec808e97-13db-49bf-9b89-cdeb813889e5");
        Pageable pageable = PageRequest.of(0, 2);

        Humidity humidity = Humidity.getBuilder()
                .deviceId("996d99f1-9948-42aa-b072-6fe570472299")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(Instant.now()))
                .value(67.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();


        Humidity humidity2 = Humidity.getBuilder()
                .deviceId("ec808e97-13db-49bf-9b89-cdeb813889e5")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(Instant.now()))
                .value(70.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();

        Humidity humidity3 = Humidity.getBuilder()
                .deviceId("ec808e97-13db-49bf-9b89-cdeb813889e5")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(Instant.now()))
                .value(70.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();

        Humidity humidity4 = Humidity.getBuilder()
                .deviceId("ec808e97-13db-49bf-9b89-cdeb813889e5")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(Instant.now()))
                .value(72.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();

        Humidity humidity5 = Humidity.getBuilder()
                .deviceId("ec808e97-13db-49bf-9b89-cdeb813889e5")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(Instant.now()))
                .value(17.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();


        //Act
        Humidity savedHumidity = humidityRepository.save(humidity);
        Humidity savedHumidity2 = humidityRepository.save(humidity2);
        Humidity savedHumidity3 = humidityRepository.save(humidity3);
        Humidity savedHumidity4 = humidityRepository.save(humidity4);
        Humidity savedHumidity5 = humidityRepository.save(humidity5);


        Page<Humidity> HumiditiesPage = humidityRepository.findByDeviceIdIn(deviceIds,pageable);

        Assertions.assertThat(HumiditiesPage).isNotNull();
        Assertions.assertThat(HumiditiesPage.getContent()).isNotNull();
        Assertions.assertThat(HumiditiesPage.getContent().size()).isEqualTo(2);
        Assertions.assertThat(HumiditiesPage.getContent().get(0).getUnit()).isEqualByComparingTo(HumidityUnitType.PERCENT);


        //clear
        humidityRepository.deleteAll();
    }


    @Test
    public void HumidityRepository_findByTimestampBetween_WithSizeInPageable() {

        //clear
        humidityRepository.deleteAll();


        //Arrange
        ZonedDateTime start_date = ZonedDateTime.parse("2023-09-14T00:00:00Z",DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC));
        ZonedDateTime end_date = ZonedDateTime.parse("2023-09-16T00:00:00Z",DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC));

        Humidity humidity = Humidity.getBuilder()
                .deviceId("996d99f1-9948-42aa-b072-6fe570472299")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(ZonedDateTime.parse("2023-09-15T00:00:00Z",DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC)).toInstant()))
                .value(67.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();


        Humidity humidity2 = Humidity.getBuilder()
                .deviceId("ec808e97-13db-49bf-9b89-cdeb813889e5")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(ZonedDateTime.parse("2023-09-11T00:00:00Z",DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC)).toInstant()))
                .value(70.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();

        Humidity humidity3 = Humidity.getBuilder()
                .deviceId("ec808e97-13db-49bf-9b89-cdeb813889e5")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(ZonedDateTime.parse("2023-09-14T05:00:00Z",DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC)).toInstant()))
                .value(70.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();

        Humidity humidity4 = Humidity.getBuilder()
                .deviceId("ec808e97-13db-49bf-9b89-cdeb813889e5")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(ZonedDateTime.parse("2023-09-04T00:00:00Z",DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC)).toInstant()))
                .value(72.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();

        Humidity humidity5 = Humidity.getBuilder()
                .deviceId("ec808e97-13db-49bf-9b89-cdeb813889e5")
                .name(WeatherMetricType.HUMIDITY)
                .timestamp(Date.from(ZonedDateTime.parse("2023-09-05T00:00:00Z",DateTimeFormatter.ofPattern("yyyy-MM-dd'T'HH:mm:ss'Z'").withZone(ZoneOffset.UTC)).toInstant()))
                .value(17.0)
                .ttl(Date.from(Instant.now().plusSeconds(60*60*24)))
                .unit(HumidityUnitType.PERCENT)
                .build();


        //Act
        Humidity savedHumidity = humidityRepository.save(humidity);
        Humidity savedHumidity2 = humidityRepository.save(humidity2);
        Humidity savedHumidity3 = humidityRepository.save(humidity3);
        Humidity savedHumidity4 = humidityRepository.save(humidity4);
        Humidity savedHumidity5 = humidityRepository.save(humidity5);



        //findByTimestampBetween
        String query = "{'timestamp': {$gte: ISODate('"+start_date.toString()+"'), $lte: ISODate('"+end_date.toString()+"')}}";
        List<Humidity> HumiditiesList = humidityRepository.findByQuery(query);

        Assertions.assertThat(HumiditiesList).isNotNull();
        Assertions.assertThat(HumiditiesList.size()).isEqualTo(2);
        Assertions.assertThat(HumiditiesList.get(0).getUnit()).isEqualByComparingTo(HumidityUnitType.PERCENT);


        //clear
        humidityRepository.deleteAll();
    }


}
