package com.badals.checkout.service.mapper;

import com.badals.checkout.domain.pojo.AdjustmentProfile;
import com.badals.checkout.domain.pojo.PriceMap;
import com.badals.checkout.service.dto.AdjustmentProfileDto;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.ObjectWriter;
import org.mapstruct.Mapper;

import java.io.IOException;

@Mapper(componentModel = "spring", uses = {})
public interface AdjustmentProfileMapper extends EntityMapper<AdjustmentProfileDto, AdjustmentProfile>{

    ObjectWriter ow = new ObjectMapper().writer();
    ObjectMapper om = new ObjectMapper();
    AdjustmentProfileDto toDto(AdjustmentProfile adjustmentProfile);

    AdjustmentProfile toEntity(AdjustmentProfileDto adjustmentProfileDto);

    default String priceMapToString(PriceMap priceMap) {
        try {
            return ow.writeValueAsString(priceMap);
        } catch (JsonProcessingException e) {
            // Handle the exception according to your needs
            e.printStackTrace();
            return null;
        }
    }

    default PriceMap stringToPriceMap(String priceMapString) {
        try {
            return om.readValue(priceMapString, PriceMap.class);
        } catch (IOException e) {
            // Handle the exception according to your needs
            e.printStackTrace();
            return null;
        }
    }
}
