package com.josko.passenger.service.mappers;

import com.josko.passenger.persistence.entity.BookingEntity;
import com.josko.passenger.persistence.entity.PassengerEntity;
import com.josko.passenger.update.dto.UpdateMetaData;
import com.josko.passenger.update.slices.BookingData;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingConstants;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class BookingMapper {

	@Mapping(target = "createdTs", ignore = true)
	@Mapping(target = "updatedTs", ignore = true)
	@Mapping(target = "purgeTs", ignore = true)
	public abstract BookingEntity fromDto(PassengerEntity passenger, BookingData data, UpdateMetaData meta);

	public abstract BookingData toDto(BookingEntity entity);

	public abstract void update(@MappingTarget BookingEntity detailsEntity, BookingData data, UpdateMetaData meta);
}
