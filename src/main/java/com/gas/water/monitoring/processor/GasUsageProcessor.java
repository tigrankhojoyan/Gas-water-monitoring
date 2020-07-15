package com.gas.water.monitoring.processor;

import com.gas.water.monitoring.exception.DataValidationException;
import com.gas.water.monitoring.model.GasUsage;
import com.gas.water.monitoring.model.Subscriber;
import com.gas.water.monitoring.model.dto.GasDataSubmitDto;
import com.gas.water.monitoring.repository.GasUsageRepository;
import com.gas.water.monitoring.repository.SubscriberRepository;
import com.gas.water.monitoring.service.validation.DataValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class intended to process updating subscriber's gas usage data
 */
@Slf4j
@Service
public class GasUsageProcessor {

    private final DataValidator<GasDataSubmitDto> dataValidator;
    private final SubscriberRepository subscriberRepository;
    private final GasUsageRepository gasUsageRepository;
    private final ModelMapper mapper;

    public GasUsageProcessor(@Qualifier("gasDtoValidator") DataValidator dataValidator, SubscriberRepository subscriberRepository, GasUsageRepository gasUsageRepository, ModelMapper mapper) {
        this.dataValidator = dataValidator;
        this.subscriberRepository = subscriberRepository;
        this.gasUsageRepository = gasUsageRepository;
        this.mapper = mapper;
    }

    @Transactional
    public void updateGasData(Long subscriberId, GasDataSubmitDto submittedGasData) {
        log.info("Processing gas usage update request");
        if (subscriberId == null) {
            throw new DataValidationException("Update request must contain ID of subscriber");
        }
        dataValidator.validateData(submittedGasData);
        GasUsage gasUsage = mapper.map(submittedGasData, GasUsage.class);
        Subscriber subscriber = subscriberRepository.findById(subscriberId).orElseThrow(() -> new IllegalArgumentException("No subscriber found by given ID"));
        gasUsage.setSubscriber(subscriber);
        gasUsageRepository.save(gasUsage);
    }
}
