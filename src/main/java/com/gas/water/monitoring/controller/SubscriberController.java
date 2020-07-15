package com.gas.water.monitoring.controller;

import com.gas.water.monitoring.model.Subscriber;
import com.gas.water.monitoring.model.dto.SubscriberDto;
import com.gas.water.monitoring.processor.SubscriberProcessor;
import com.gas.water.monitoring.service.SubscriberService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

/**
 * Controller intended to create gas/water subscriber
 */
@Slf4j
@RestController
@RequestMapping("/api/subscriber")
public class SubscriberController {

    private final SubscriberProcessor processor;

    private final SubscriberService subscriberService;

    public SubscriberController(SubscriberProcessor processor, SubscriberService subscriberService) {
        this.processor = processor;
        this.subscriberService = subscriberService;
    }

    @PostMapping(value = "/create", consumes = "application/json")
    @ResponseStatus(HttpStatus.CREATED)
    public Subscriber createSubscriber(@RequestBody SubscriberDto subscriberDto) {
        return processor.createNewSubscriber(subscriberDto);
    }

    @GetMapping(value = "/get/{id}")
    public Subscriber findSubscriberById(@PathVariable("id") Long id) {
        Subscriber subscriber = subscriberService.findById(id);
        log.info(subscriber.toString());
        return subscriber;
    }

}
