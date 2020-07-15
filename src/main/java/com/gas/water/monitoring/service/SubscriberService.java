package com.gas.water.monitoring.service;

import com.gas.water.monitoring.model.Subscriber;
import com.gas.water.monitoring.repository.SubscriberRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * Class intended to retrieve subscriber
 */
@Service
public class SubscriberService {

    private final SubscriberRepository subscriberRepository;

    public SubscriberService(SubscriberRepository subscriberRepository) {
        this.subscriberRepository = subscriberRepository;
    }

    @Transactional
    public Subscriber save(Subscriber subscriber) {
        return subscriberRepository.save(subscriber);
    }

    @Transactional(readOnly = true)
    public Subscriber findById(Long id) {
        return subscriberRepository.findById(id).orElseThrow(()->new IllegalArgumentException("No entity found by given id"));
    }

}
