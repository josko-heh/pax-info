package com.josko.passenger.service.mappers;

import com.josko.passenger.presentation.dto.slices.PassengerDetailsDTO;
import com.josko.passenger.presentation.dto.slices.SliceDTO;
import com.josko.passenger.presentation.dto.slices.SliceDataDTO;
import com.josko.passenger.update.slices.PassengerDetailsData;
import com.josko.passenger.update.slices.Slice;
import com.josko.passenger.update.slices.SliceData;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;

@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class SliceMapper {

    public SliceDataDTO toDataDTO(Slice slice) {
		return switch (slice.getName()) {
			case DETAILS -> toPaxDetails((PassengerDetailsData) slice.getContent());
		};
	}

	public abstract List<SliceDTO> toDTOs(Set<Slice> slices);

	@Mapping(target = "type", expression = "java(map(slice.getName()))")
	@Mapping(target = "data", expression = "java(toDataDTO(slice))")
	public abstract SliceDTO toDTO(Slice slice);
	
	@ValueMapping(source = "DETAILS", target = "PASSENGER_DETAILS")
	public abstract SliceDTO.Type map(SliceData.Type type);

	@InheritInverseConfiguration
	public abstract SliceData.Type map(SliceDTO.Type type);

	public abstract List<SliceData.Type> map(List<SliceDTO.Type> typeDTOs);

	protected abstract PassengerDetailsDTO toPaxDetails(PassengerDetailsData data);
}
