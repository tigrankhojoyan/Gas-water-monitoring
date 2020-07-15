package com.gas.water.monitoring.controller;

import com.gas.water.monitoring.model.Subscriber;
import com.gas.water.monitoring.model.WaterType;
import com.gas.water.monitoring.model.WaterUsage;
import com.gas.water.monitoring.repository.SubscriberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.context.annotation.Bean;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.HttpClientErrorException;
import org.springframework.web.client.RestTemplate;

import java.math.BigDecimal;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class DataGetControllerTest {

    @LocalServerPort
    int randomServerPort;

    @Autowired
    SubscriberRepository subscriberRepository;

    @Test
    void testRecentWaterData() {
        Subscriber subscriber = Subscriber.builder().firstName("Fname").lastName("Lname").build();
        WaterUsage waterUsage1 = WaterUsage.builder().waterAmount(new BigDecimal("11.12")).waterType(WaterType.HOT).subscriber(subscriber)
                .submittedTime(LocalDateTime.of(2017, 3, 13, 15, 56)).build();
        WaterUsage waterUsage2 = WaterUsage.builder().waterAmount(new BigDecimal("11.12")).waterType(WaterType.HOT).subscriber(subscriber)
                .submittedTime(LocalDateTime.of(2017, 2, 13, 15, 56)).build();
        subscriber.addWaterUsage(waterUsage1).addWaterUsage(waterUsage2);
        subscriber = subscriberRepository.save(subscriber);

        RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:" + randomServerPort).build();
        ResponseEntity<String> waterUsageData = restTemplate.exchange("/api/get/recent/water?id={1}&type={2}", HttpMethod.GET, null, String.class, subscriber.getClientId(), "HOT");
        assertEquals(HttpStatus.OK, waterUsageData.getStatusCode());
    }

    @Test
    void testRecentWaterDataBadRequest() {
        RestTemplate restTemplate = new RestTemplateBuilder().rootUri("http://localhost:" + randomServerPort).build();
        try {
            restTemplate.exchange("/api/get/recent/water", HttpMethod.GET, null, String.class);
        } catch (HttpClientErrorException ex) {
            assertEquals(HttpStatus.BAD_REQUEST.value(), ex.getRawStatusCode());
        }
    }

}