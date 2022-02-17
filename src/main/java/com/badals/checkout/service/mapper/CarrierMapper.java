package com.badals.checkout.service.mapper;

import com.badals.checkout.domain.Carrier;
import com.badals.checkout.domain.pojo.projection.ShipRate;
import com.badals.checkout.service.dto.CarrierDTO;
import org.mapstruct.AfterMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

import java.util.List;
import java.util.stream.Collectors;


@Mapper(componentModel = "spring", uses = {})
public interface CarrierMapper extends EntityMapper<CarrierDTO, Carrier> {

    @Mapping(ignore=true, target = "name")
    @Mapping(source = "ref", target = "value")
    CarrierDTO toDto(Carrier Carrier);


    @Mapping(source="carrierName", target = "name")
    @Mapping(source="carrierLogo", target = "logo")
    @Mapping(source="price", target = "cost")
    @Mapping(source="rateName", target = "value")
    @Mapping(source="id", target = "id")
    CarrierDTO toDtoFromShipRate(ShipRate rate);


    @Mapping(ignore=true, target = "name")
    Carrier toEntity(CarrierDTO CarrierDTO);

    default Carrier fromId(Long id) {
        if (id == null) {
            return null;
        }
        Carrier Carrier = new Carrier();
        Carrier.setId(id);
        return Carrier;
    }
    @AfterMapping
    default void afterMapping(@MappingTarget CarrierDTO dto, Carrier source) {
        dto.setName(source.getName().stream().findFirst().get().getValue());
    }
}

