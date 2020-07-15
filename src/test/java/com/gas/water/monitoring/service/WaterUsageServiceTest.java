package com.gas.water.monitoring.service;

import com.gas.water.monitoring.exception.DataNotFoundException;
import com.gas.water.monitoring.model.WaterType;
import com.gas.water.monitoring.model.WaterUsage;
import com.gas.water.monitoring.repository.WaterUsageRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(MockitoExtension.class)
class WaterUsageServiceTest {

    @Mock
    WaterUsageRepository waterUsageRepository;

    @InjectMocks
    WaterUsageService waterUsageService;

    @Test
    void testFindRecentWaterUsageData() {
        WaterUsage waterUsage = WaterUsage.builder().clientId(1L).waterType(WaterType.HOT).waterAmount(BigDecimal.TEN)
                .submittedTime(LocalDateTime.now()).build();
        Mockito.when(waterUsageRepository.findTopByWaterTypeAndSubscriberClientIdOrderBySubmittedTimeDesc(WaterType.HOT, 1L))
                .thenReturn(Optional.of(waterUsage));
        WaterUsage persistedData = waterUsageService.findRecentWaterUsageData(1L, WaterType.HOT);
        assertEquals(waterUsage, persistedData);
    }

    @Test
    void testFindRecentWaterUsageDataNotFound() {
        Mockito.when(waterUsageRepository.findTopByWaterTypeAndSubscriberClientIdOrderBySubmittedTimeDesc(WaterType.HOT, 1L))
                .thenReturn(Optional.empty());
        assertThrows(
                DataNotFoundException.class, () -> waterUsageService.findRecentWaterUsageData(1L, WaterType.HOT)
        );
    }
}