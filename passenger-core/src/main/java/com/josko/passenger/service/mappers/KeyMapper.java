package com.josko.passenger.service.mappers;

import com.josko.passenger.persistence.entity.keys.Key;
import com.josko.passenger.persistence.entity.keys.KeyEntity;
import com.josko.passenger.persistence.entity.keys.TicketNumberKeyEntity;
import com.josko.passenger.update.dto.keys.KeyDTO;
import com.josko.passenger.update.dto.keys.TicketNumberKeyDTO;
import org.mapstruct.*;

import java.util.List;
import java.util.Set;


@Mapper(componentModel = MappingConstants.ComponentModel.SPRING)
public abstract class KeyMapper {

    public KeyEntity toEntity(KeyDTO dto) {
        return switch (dto.getType()) {
            case TICKET_NUMBER -> toEntity((TicketNumberKeyDTO) dto);
        };
    }

    public KeyDTO toDTO(KeyEntity entity) {
        if (entity instanceof TicketNumberKeyEntity ticketNumberKeyEntity) {
            return toDTO(ticketNumberKeyEntity);
        }

        return null;
    }

    public abstract Set<KeyEntity> toEntities(List<KeyDTO> dtos);
    public abstract Set<KeyDTO> toDTOs(List<KeyEntity> entities);

    protected abstract TicketNumberKeyEntity toEntity(TicketNumberKeyDTO dto);
    protected abstract TicketNumberKeyDTO toDTO(TicketNumberKeyEntity entity);

    @ValueMapping(source = "TKNE", target = "TICKET_NUMBER")
    protected abstract KeyDTO.Type map(Key.Type type);
}
