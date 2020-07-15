package com.gas.water.monitoring.processor;

import com.gas.water.monitoring.exception.DataValidationException;
import com.gas.water.monitoring.model.Subscriber;
import com.gas.water.monitoring.model.WaterUsage;
import com.gas.water.monitoring.model.dto.WaterDataSubmitDto;
import com.gas.water.monitoring.repository.SubscriberRepository;
import com.gas.water.monitoring.repository.WaterUsageRepository;
import com.gas.water.monitoring.service.validation.DataValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class intended to process updating subscriber's water usage data
 */
@Service
@Slf4j
public class WaterUsageProcessor {

    private final DataValidator<WaterDataSubmitDto> dataValidator;
    private final SubscriberRepository subscriberRepository;
    private final WaterUsageRepository waterUsageRepository;
    private final ModelMapper mapper;

    public WaterUsageProcessor(@Qualifier("waterDtoValidator") DataValidator<WaterDataSubmitDto> dataValidator, SubscriberRepository subscriberRepository, WaterUsageRepository waterUsageRepository, ModelMapper mapper) {
        this.dataValidator = dataValidator;
        this.subscriberRepository = subscriberRepository;
        this.waterUsageRepository = waterUsageRepository;
        this.mapper = mapper;
    }

    @Transactional
    public void updateWaterData(Long subscriberId, WaterDataSubmitDto submittedWaterData) {
        log.info("Processing water usage update request");
        if (subscriberId == null) {
            throw new DataValidationException("Update request must contain ID of subscriber");
        }
        dataValidator.validateData(submittedWaterData);
        WaterUsage waterUsage = mapper.map(submittedWaterData, WaterUsage.class);
        Subscriber subscriber = subscriberRepository.findById(subscriberId).orElseThrow(() -> new IllegalArgumentException("No subscriber found by given ID"));
        waterUsage.setSubscriber(subscriber);
        waterUsageRepository.save(waterUsage);
    }
}
