package com.gas.water.monitoring.processor;

import com.gas.water.monitoring.model.GasUsage;
import com.gas.water.monitoring.model.Subscriber;
import com.gas.water.monitoring.model.dto.GasDataSubmitDto;
import com.gas.water.monitoring.repository.GasUsageRepository;
import com.gas.water.monitoring.repository.SubscriberRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertAll;
import static org.junit.jupiter.api.Assertions.assertEquals;

@SpringBootTest
//@Transactional
class GasUsageProcessorTest {

    @Autowired
    GasUsageProcessor gasUsageProcessor;

    @Autowired
    SubscriberRepository subscriberRepository;

    @Autowired
    GasUsageRepository gasUsageRepository;

    @Test
    void testUpdateGasData() {
        Subscriber subscriber = Subscriber.builder().lastName("Test").firstName("Test").build();
        subscriber = subscriberRepository.save(subscriber);
        GasDataSubmitDto gasDataSubmitDto = new GasDataSubmitDto();
        gasDataSubmitDto.setSubmittedTime(LocalDateTime.now());
        gasDataSubmitDto.setUsedGasContent(new BigDecimal("11.10"));

        gasUsageProcessor.updateGasData(subscriber.getClientId(), gasDataSubmitDto);
        List<GasUsage> gasUsageList = gasUsageRepository.findAllBySubscriber_ClientId(subscriber.getClientId());
        assertAll("Checking data",
                () -> assertEquals(1, gasUsageList.size()),
                () -> assertEquals(gasDataSubmitDto.getUsedGasContent(), gasUsageList.get(0).getUsedGasContent())
        );

    }

}