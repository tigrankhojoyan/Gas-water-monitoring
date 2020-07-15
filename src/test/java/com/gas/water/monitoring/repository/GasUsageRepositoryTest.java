package com.gas.water.monitoring.repository;

import com.gas.water.monitoring.model.GasUsage;
import com.gas.water.monitoring.model.Subscriber;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class GasUsageRepositoryTest {

    @Autowired
    GasUsageRepository gasUsageRepository;

    @Autowired
    SubscriberRepository subscriberRepository;

    @Test
    void testFindFirstBySubscriber_ClientIdOrderBySubmittedTimeDesc() {
        Subscriber subscriber = Subscriber.builder().firstName("Fname").lastName("Lname").build();

        GasUsage gasUsage1 = GasUsage.builder().submittedTime(LocalDateTime.of(2020, 1, 8, 15, 56)).subscriber(subscriber).usedGasContent(BigDecimal.valueOf(5.2)).build();
        GasUsage gasUsage2 = GasUsage.builder().subscriber(subscriber).submittedTime(LocalDateTime.now()).usedGasContent(BigDecimal.valueOf(5.5)).build();
        subscriber.addGasUsages(gasUsage1).addGasUsages(gasUsage2);
        subscriberRepository.save(subscriber);

        Optional<GasUsage> persistedRecord = gasUsageRepository.findFirstBySubscriber_ClientIdOrderBySubmittedTimeDesc(subscriber.getClientId());
        assertTrue(persistedRecord.isPresent());
        assertEquals(gasUsage2.getSubmittedTime(), persistedRecord.get().getSubmittedTime());
        assertEquals(gasUsage2.getUsedGasContent(), persistedRecord.get().getUsedGasContent());
    }

    @Test
    void testFindAllBySubscriber_ClientId() {
        Subscriber subscriber = Subscriber.builder().firstName("Fname").lastName("Lname").build();

        GasUsage gasUsage1 = GasUsage.builder().submittedTime(LocalDateTime.now()).subscriber(subscriber).usedGasContent(BigDecimal.valueOf(5.2)).build();
        GasUsage gasUsage2 = GasUsage.builder().subscriber(subscriber).submittedTime(LocalDateTime.now()).usedGasContent(BigDecimal.valueOf(6.8)).build();
        GasUsage gasUsage3 = GasUsage.builder().subscriber(subscriber).submittedTime(LocalDateTime.now()).usedGasContent(BigDecimal.valueOf(4.2)).build();
        subscriber.addGasUsages(gasUsage1).addGasUsages(gasUsage2).addGasUsages(gasUsage3);

        subscriberRepository.save(subscriber);
        List<GasUsage> gasUsages = gasUsageRepository.findAllBySubscriber_ClientId(subscriber.getClientId());
        assertNotNull(gasUsages);
        assertEquals(3, gasUsages.size());
    }

    @Test
    void testFindAllBySubscriber_ClientIdPaged() {
        Subscriber subscriber = Subscriber.builder().firstName("Fname").lastName("Lname").build();

        GasUsage gasUsage1 = GasUsage.builder().submittedTime(LocalDateTime.now()).subscriber(subscriber).usedGasContent(BigDecimal.valueOf(5.2)).build();
        GasUsage gasUsage2 = GasUsage.builder().subscriber(subscriber).submittedTime(LocalDateTime.now()).usedGasContent(BigDecimal.valueOf(6.8)).build();
        GasUsage gasUsage3 = GasUsage.builder().subscriber(subscriber).submittedTime(LocalDateTime.now()).usedGasContent(BigDecimal.valueOf(4.2)).build();
        subscriber.addGasUsages(gasUsage1).addGasUsages(gasUsage2).addGasUsages(gasUsage3);

        subscriberRepository.save(subscriber);
        Pageable pageable = PageRequest.of(0,1);
        Page<GasUsage> gasUsages = gasUsageRepository.findAllBySubscriber_ClientId(subscriber.getClientId(), pageable);
        assertNotNull(gasUsages);
        assertEquals(1, gasUsages.getContent().size());
    }
}