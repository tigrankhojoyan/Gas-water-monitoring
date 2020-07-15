package com.gas.water.monitoring.processor;

import com.gas.water.monitoring.model.Subscriber;
import com.gas.water.monitoring.model.dto.SubscriberDto;
import com.gas.water.monitoring.service.SubscriberService;
import com.gas.water.monitoring.service.validation.DataValidator;
import lombok.extern.slf4j.Slf4j;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class intended to process new subscriber creating
 */
@Service
@Slf4j
public class SubscriberProcessor {

    private final DataValidator<SubscriberDto> dataValidator;
    private final SubscriberService subscriberService;
    private final ModelMapper mapper;

    public SubscriberProcessor(@Qualifier("subscriberDtoValidator") DataValidator dataValidator, SubscriberService subscriberService, ModelMapper mapper) {
        this.dataValidator = dataValidator;
        this.subscriberService = subscriberService;
        this.mapper = mapper;
    }

    @Transactional
    public Subscriber createNewSubscriber(SubscriberDto subscriberDto) {
        dataValidator.validateData(subscriberDto);
        Subscriber subscriber = mapper.map(subscriberDto, Subscriber.class);
        return subscriberService.save(subscriber);
    }
}
