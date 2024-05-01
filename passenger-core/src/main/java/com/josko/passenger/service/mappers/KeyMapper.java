package com.josko.passenger.service.mappers;

import com.josko.passenger.persistence.entity.keys.Key;
import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.persistence.entity.keys.PnrKeyEntity;
import com.josko.passenger.persistence.entity.keys.TicketNumberKeyEntity;
import com.josko.passenger.update.dto.keys.KeyDTO;
import com.josko.passenger.update.dto.keys.PnrKeyDTO;
import com.josko.passenger.update.dto.keys.TicketNumberKeyDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class KeyMapper {

    public KeyEntity toEntity(KeyDTO dto) {
        return switch (dto.getType()) {
            case TICKET_NUMBER -> toEntity((TicketNumberKeyDTO) dto);
			case PNR -> toEntity((PnrKeyDTO) dto);
		};
    }

    public KeyDTO toDTO(KeyEntity entity) {
        if (entity instanceof TicketNumberKeyEntity ticketNumberKeyEntity) {
            return toDTO(ticketNumberKeyEntity);
        } else if (entity instanceof PnrKeyEntity pnrKeyEntity) {
            return toDTO(pnrKeyEntity);
        }

        return null;
    }

    public abstract Set<KeyEntity> toEntities(List<KeyDTO> dtos);
    public abstract Set<KeyDTO> toDTOs(Set<KeyEntity> entities);

    protected abstract TicketNumberKeyEntity toEntity(TicketNumberKeyDTO dto);
    protected abstract TicketNumberKeyDTO toDTO(TicketNumberKeyEntity entity);
    protected abstract PnrKeyEntity toEntity(PnrKeyDTO dto);
    protected abstract PnrKeyDTO toDTO(PnrKeyEntity entity);

    @ValueMapping(source = "TKNE", target = "TICKET_NUMBER")
    protected abstract KeyDTO.Type map(Key.Type type);
}
