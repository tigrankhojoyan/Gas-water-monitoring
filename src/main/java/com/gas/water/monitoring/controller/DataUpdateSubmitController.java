package com.gas.water.monitoring.controller;

import com.gas.water.monitoring.model.dto.GasDataSubmitDto;
import com.gas.water.monitoring.model.dto.WaterDataSubmitDto;
import com.gas.water.monitoring.processor.GasUsageProcessor;
import com.gas.water.monitoring.processor.WaterUsageProcessor;
import org.springframework.web.bind.annotation.*;

/**
 * Controller intended to submit new gas/water usage data(for specified subscriber)
 */
@RestController
@RequestMapping("/api/update")
public class DataUpdateSubmitController {

    private final GasUsageProcessor gasUsageProcessor;
    private final WaterUsageProcessor waterUsageProcessor;

    public DataUpdateSubmitController(GasUsageProcessor gasUsageProcessor, WaterUsageProcessor waterUsageProcessor) {
        this.gasUsageProcessor = gasUsageProcessor;
        this.waterUsageProcessor = waterUsageProcessor;
    }

    @PutMapping("/gas/{id}")
    public void updateGasUsageData(@RequestBody GasDataSubmitDto gasDataSubmitDto, @PathVariable("id") Long id) {
        gasUsageProcessor.updateGasData(id, gasDataSubmitDto);
    }

    @PutMapping("/water/{id}")
    public void updateWaterUsageData(@RequestBody WaterDataSubmitDto waterDataSubmitDto, @PathVariable("id") Long id) {
        waterUsageProcessor.updateWaterData(id, waterDataSubmitDto);
    }

}
