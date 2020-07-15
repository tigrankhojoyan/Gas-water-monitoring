package com.gas.water.monitoring.service;

import com.gas.water.monitoring.exception.DataNotFoundException;
import com.gas.water.monitoring.model.WaterType;
import com.gas.water.monitoring.model.WaterUsage;
import com.gas.water.monitoring.repository.WaterUsageRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * Service intended to retrieve water data from db
 */
@Service
@Transactional(readOnly = true)
public class WaterUsageService {

    private final WaterUsageRepository repository;

    public WaterUsageService(WaterUsageRepository repository) {
        this.repository = repository;
    }

    /**
     *  Retrieves last submitted water data(by type)
     *
     * @param id
     * @param waterType
     * @return
     */
    public WaterUsage findRecentWaterUsageData(Long id, WaterType waterType) {
        return repository.findTopByWaterTypeAndSubscriberClientIdOrderBySubmittedTimeDesc(waterType, id).orElseThrow(
                () -> new DataNotFoundException(String.format("Water usage data not found by given subscriber ID(%d)", id)));
    }

    /**
     * Retrieves subscriber water usage data(can be filtered by type and and limited)
     *
     * @param id
     * @param waterType
     * @param pageable
     * @return
     */
    public List<WaterUsage> findWaterDataUsages(Long id, WaterType waterType, Pageable pageable) {
        Page<WaterUsage> waterUsages = repository.findAllByWaterTypeAndSubscriberClientId(waterType, id, pageable);
        if (waterUsages.isEmpty()) {
            throw new DataNotFoundException(String.format("Water usage records not found by given subscriber ID(%d)", id));
        }
        return waterUsages.toList();
    }

    /**
     * Retrieves all water usage data of subscriber(by type)
     *
     * @param id
     * @param waterType
     * @return
     */
    public List<WaterUsage> findAllWaterUsageDataByType(Long id, WaterType waterType) {
        List<WaterUsage> allTypedWaterData = repository.findAllBySubscriber_ClientIdAndWaterType(id, waterType);
        if (allTypedWaterData.isEmpty()) {
            throw new DataNotFoundException(String.format("Water usage records(ALL, by type) not found by given subscriber ID(%d)", id));
        }
        return allTypedWaterData;
    }

    /**
     * Retrieves all water usage data of subscriber
     *
     * @param id
     * @return
     */
    public List<WaterUsage> findAllWaterUsageData(Long id) {
        List<WaterUsage> allWaterUsageData = repository.findAllBySubscriber_ClientId(id);
        if (allWaterUsageData.isEmpty()) {
            throw new DataNotFoundException(String.format("Water usage records(ALL) not found by given subscriber ID(%d)", id));
        }
        return allWaterUsageData;
    }

}
