package com.labluetech.weather.controller;


import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.labluetech.weather.domain.CustomZonedDateTimeDeserializer;
import com.labluetech.weather.dto.SensorData;
import com.labluetech.weather.service.WeatherMetricService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.ResultActions;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;

import static org.mockito.BDDMockito.given;
import static org.mockito.BDDMockito.willAnswer;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;

@WebMvcTest(controllers = WeatherMetricsController.class)
@AutoConfigureMockMvc(addFilters = false)
@ExtendWith(MockitoExtension.class)
public class WeatherMetricsControllerTests {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private WeatherMetricService weatherMetricService;

    @Autowired
    private ObjectMapper objectMapper;



    @BeforeEach
    public void init() {

    }

    @Test
    public void WeatherMetricsController_createWeatherMetric_ReturnCreated() throws Exception {
        SensorData sensorData = new SensorData();
        sensorData.setDeviceId("112233");
        sensorData.setTimestamp(ZonedDateTime.now(ZoneId.of("UTC")));
        sensorData.setTemperature(15.0);
        sensorData.setWindSpeed(29.0);
        ResultActions response = mockMvc.perform(post("/weather/metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorData)));

        response.andExpect(MockMvcResultMatchers.status().isCreated());
    }

    @Test
    public void WeatherMetricsController_createWeatherMetric_ReturnBadRequestStatus() throws Exception {
        SensorData sensorData2 = new SensorData();
        sensorData2.setTimestamp(ZonedDateTime.now(ZoneId.of("UTC")));
        sensorData2.setTemperature(15.0);
        sensorData2.setWindSpeed(29.0);

        ResultActions response = mockMvc.perform(post("/weather/metrics")
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(sensorData2)));

        response.andExpect(MockMvcResultMatchers.status().isBadRequest());
    }

}
