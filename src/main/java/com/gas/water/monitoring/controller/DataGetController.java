package com.gas.water.monitoring.controller;

import com.gas.water.monitoring.model.GasUsage;
import com.gas.water.monitoring.model.WaterUsage;
import com.gas.water.monitoring.processor.GasDataRetrieveProcessor;
import com.gas.water.monitoring.processor.WaterDataRetrieveProcessor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

/**
 * Controller intended to retrieve data(gas/water) from db
 */
@RestController
@RequestMapping("/api/get")
@Slf4j
public class DataGetController {

    private final GasDataRetrieveProcessor gasDataRetrieveProcessor;
    private final WaterDataRetrieveProcessor waterDataRetrieveProcessor;

    public DataGetController(GasDataRetrieveProcessor gasDataRetrieveProcessor, WaterDataRetrieveProcessor waterDataRetrieveProcessor) {
        this.gasDataRetrieveProcessor = gasDataRetrieveProcessor;
        this.waterDataRetrieveProcessor = waterDataRetrieveProcessor;
    }

    /**
     * Retrieves water usage data(may be filtered by type(@param type))
     * and by count(@param limit)
     *
     * @param clientId
     * @param waterType
     * @param limit
     * @return
     */
    @RequestMapping("/water")
    public List<WaterUsage> getWaterUsagesData(@RequestParam("id") Long clientId,
                                               @RequestParam("type") Optional<String> waterType,
                                               @RequestParam("limit") Optional<Integer> limit) {
        log.info("Retrieving water usage data");
        return waterDataRetrieveProcessor.retrieveWaterUsageDataOfSubscriber(clientId, waterType, limit);
    }

    /**
     * Retrieves last submitted water usage data(by time)
     *
     * @param clientId
     * @param waterType
     * @return
     */
    @RequestMapping("/recent/water")
    public WaterUsage getRecentWaterUsageData(@RequestParam("id") Long clientId,
                                              @RequestParam("type") Optional<String> waterType) {
        log.info("Retrieving last submitted water usage data");
        return waterDataRetrieveProcessor.retrieveRecentWaterUsageData(clientId, waterType);
    }

    /**
     * Retrieves gas usage data by count(@param limit)
     *
     * @param clientId
     * @param limit
     * @return
     */
    @RequestMapping("/gas")
    public List<GasUsage> getGasUsagesData(@RequestParam("id") Long clientId,
                                           @RequestParam("limit") Optional<Integer> limit) {
        log.info("Retrieving gas usage data");
        return gasDataRetrieveProcessor.retrieveGasUsageDataOfSubscriber(clientId, limit);
    }

    /**
     * Retrieves last submitted gas usage data(by time)
     *
     * @param clientId
     * @return
     */
    @RequestMapping("/recent/gas")
    public GasUsage getRecentGasUsageData(@RequestParam("id") Long clientId) {
        log.info("Retrieving last submitted gas data");
        return gasDataRetrieveProcessor.retrieveRecentGasUsageData(clientId);
    }

}
