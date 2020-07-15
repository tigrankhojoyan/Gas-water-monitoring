package com.gas.water.monitoring.service;

import com.gas.water.monitoring.exception.DataValidationException;
import com.gas.water.monitoring.model.WaterType;

import javax.persistence.AttributeConverter;

/**
 * Class intended to map enum value to number(before persisting to db) and
 * vise versa(when retrieve data from db)
 */
public class WaterTypeAttributeConverter implements AttributeConverter<WaterType, Integer> {
    @Override
    public Integer convertToDatabaseColumn(WaterType attribute) {
        if(attribute != null) {
            switch (attribute) {
                case HOT:
                    return 1;
                case COLD:
                    return 2;
            }
        }
        throw new DataValidationException("Invalid water identifier");
    }

    @Override
    public WaterType convertToEntityAttribute(Integer dbData) {
        if(dbData != null) {
            switch (dbData) {
                case 1:
                    return WaterType.HOT;
                case 2:
                    return WaterType.COLD;
            }
        }
        throw new DataValidationException("Invalid water identifier is persisted in DB");
    }
}
