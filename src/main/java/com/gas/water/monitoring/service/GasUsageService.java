package com.gas.water.monitoring.service;

import com.gas.water.monitoring.exception.DataNotFoundException;
import com.gas.water.monitoring.model.GasUsage;
import com.gas.water.monitoring.repository.GasUsageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service intended to retrieve gas data from db
 */
@Service
@Transactional(readOnly = true)
public class GasUsageService {

    private final GasUsageRepository repository;

    public GasUsageService(GasUsageRepository repository) {
        this.repository = repository;
    }

    /**
     * Returns last submitted gas usage data of subscriber
     * @param subscriberId
     * @return
     */
    public GasUsage findResentGasUsageData(Long subscriberId) {
        return repository.findFirstBySubscriber_ClientIdOrderBySubmittedTimeDesc(subscriberId).orElseThrow(
                () -> new DataNotFoundException(String.format("Gas usage data not found by given subscriber ID(%d)", subscriberId)));
    }

    /**
     * Returns gas usage data of subscriber(pageable)
     *
     * @param id
     * @param pageable
     * @return
     */
    public List<GasUsage> findBySubscriberId(Long id, Pageable pageable) {
        Page<GasUsage> gasUsages = repository.findAllBySubscriber_ClientId(id, pageable);
        if (gasUsages.isEmpty()) {
            throw new DataNotFoundException(String.format("Gas usage records not found by given subscriber ID(%d)", id));
        }
        return gasUsages.toList();
    }

    /**
     * Returns gas usage data of subscriber
     *
     * @param id
     * @return
     */
    public List<GasUsage> findAllBySubscriberId(Long id) {
        List<GasUsage> gasUsages = repository.findAllBySubscriber_ClientId(id);
        if (gasUsages.isEmpty()) {
            throw new DataNotFoundException(String.format("All history of gas usage not found fro given subscriber ID(%d)", id));
        }
        return gasUsages;
    }

}
