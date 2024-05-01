package com.josko.passenger.service.mappers;

import com.josko.passenger.persistence.entity.PassengerDetailsEntity;
import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.update.dto.UpdateMetaData;
import com.josko.passenger.update.slices.PassengerDetailsData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class PassengerDetailsMapper {

	@Mapping(target = "createdTs", ignore = true)
	@Mapping(target = "updatedTs", ignore = true)
	@Mapping(target = "detailsId", ignore = true)
	public abstract PassengerDetailsEntity fromDto(PassengerEntity passenger, PassengerDetailsData data, UpdateMetaData meta);

	public abstract PassengerDetailsData toDto(PassengerDetailsEntity entity);

	public abstract void update(@MappingTarget PassengerDetailsEntity detailsEntity, PassengerDetailsData data, UpdateMetaData meta);
}
