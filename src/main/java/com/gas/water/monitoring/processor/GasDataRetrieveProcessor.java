package com.gas.water.monitoring.processor;

import com.gas.water.monitoring.exception.DataValidationException;
import com.gas.water.monitoring.model.GasUsage;
import com.gas.water.monitoring.service.GasUsageService;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Class intended to retrieve gas usage data
 */
@Service
public class GasDataRetrieveProcessor {

    private final GasUsageService gasUsageService;

    public GasDataRetrieveProcessor(GasUsageService gasUsageService) {
        this.gasUsageService = gasUsageService;
    }

    /**
     * Retrieves gas usage data from db(specified count 0...@param recordsCount)
     *
     * @param subscriberId
     * @param recordsCount
     * @return
     */
    public List<GasUsage> retrieveGasUsageDataByLimit(Long subscriberId, Integer recordsCount) {
        validateId(subscriberId);
        return gasUsageService.findBySubscriberId(subscriberId, PageRequest.of(0, recordsCount));
    }

    /**
     * Retrieves last submitted(by time)  gas usage data
     *
     * @param subscriberId
     * @return
     */
    public GasUsage retrieveRecentGasUsageData(Long subscriberId) {
        validateId(subscriberId);
        return gasUsageService.findResentGasUsageData(subscriberId);
    }

    /**
     * Retrieves all gas usage data of subscriber
     *
     * @param subscriberId
     * @return
     */
    public List<GasUsage> retrieveAllGasUsageData(Long subscriberId) {
        validateId(subscriberId);
        return gasUsageService.findAllBySubscriberId(subscriberId);
    }

    /**
     * Validates given parameters
     *
     * @param subscriberId
     * @param recordsCount
     * @return
     */
    public List<GasUsage> retrieveGasUsageDataOfSubscriber(Long subscriberId, Optional<Integer> recordsCount) {
        if (!recordsCount.isPresent()) {
            return retrieveAllGasUsageData(subscriberId);
        }

        return retrieveGasUsageDataByLimit(subscriberId, recordsCount.get());
    }


    //TODO can be done by centralized validation
    private static void validateId(Long id) {
        if (id == null || id < 0) {
            throw new DataValidationException("Subscriber ID must be specified");
        }
    }

}
