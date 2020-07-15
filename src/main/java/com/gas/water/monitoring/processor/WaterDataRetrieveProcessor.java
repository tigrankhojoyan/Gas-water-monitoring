package com.gas.water.monitoring.processor;

import com.gas.water.monitoring.exception.DataValidationException;
import com.gas.water.monitoring.model.WaterType;
import com.gas.water.monitoring.model.WaterUsage;
import com.gas.water.monitoring.service.WaterUsageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class intended to retrieve water usage data
 */
@Service
public class WaterDataRetrieveProcessor {

    private final WaterUsageService waterUsageService;

    public WaterDataRetrieveProcessor(WaterUsageService waterUsageService) {
        this.waterUsageService = waterUsageService;
    }

    /**
     * Retrieves water usage data of subscriber(can be limited by type and count)
     *
     * @param subscriberId
     * @param waterType
     * @param recordsCount
     * @return
     */
    public List<WaterUsage> retrieveWaterUsageDataByLimit(Long subscriberId, Optional<String> waterType, Optional<Integer> recordsCount) {
        validateIdAndType(subscriberId, waterType, true);
        return waterUsageService.findWaterDataUsages(subscriberId, WaterType.valueOf(waterType.get()), PageRequest.of(0, recordsCount.get()));
    }

    /**
     * Retrieves water usage data of specified type(last submitted)
     *
     * @param subscriberId
     * @param waterType
     * @return
     */
    public WaterUsage retrieveRecentWaterUsageData(Long subscriberId, Optional<String> waterType) {
        validateIdAndType(subscriberId, waterType, true);
        return waterUsageService.findRecentWaterUsageData(subscriberId, WaterType.valueOf(waterType.get()));
    }

    /**
     * Retrieves all water usage data of specified type
     *
     * @param subscriberId
     * @param waterType
     * @return
     */
    public List<WaterUsage> retrieveAllWaterUsageDataByType(Long subscriberId, Optional<String> waterType) {
        validateIdAndType(subscriberId, waterType, true);
        return waterUsageService.findAllWaterUsageDataByType(subscriberId, WaterType.valueOf(waterType.get()));
    }

    /**
     * Retrieves all water usage data of specified subscriber
     *
     * @param subscriberId
     * @return
     */
    public List<WaterUsage> retrieveAllWaterUsageData(Long subscriberId) {
        validateIdAndType(subscriberId, null, false);
        return waterUsageService.findAllWaterUsageData(subscriberId);
    }

    /**
     * Processes water data usage retrieving request
     *
     * @param subscriberId
     * @param waterType
     * @param limit
     * @return
     */
    public List<WaterUsage> retrieveWaterUsageDataOfSubscriber(Long subscriberId, Optional<String> waterType, Optional<Integer> limit) {
        if (!limit.isPresent() && !waterType.isPresent()) {
            return retrieveAllWaterUsageData(subscriberId);
        }
        if (waterType.isPresent() && !limit.isPresent()) {
            return retrieveAllWaterUsageDataByType(subscriberId, waterType);
        }
        return retrieveWaterUsageDataByLimit(subscriberId, waterType, limit);
    }


    //TODO can be done by centralized validation

    /**
     * Validates ID and water type
     *
     * @param id
     * @param waterType
     */
    private static void validateIdAndType(Long id, Optional<String> waterType, boolean validateWaterType) {
        if (id == null || id < 0) {
            throw new DataValidationException("Subscriber ID must be specified");
        }
        if (validateWaterType) {
            if (waterType.isPresent()) {
                WaterType.valueOf(waterType.get());
            } else {
                throw new DataValidationException("Subscriber Water type value is invalid");
            }
        }
    }

}
