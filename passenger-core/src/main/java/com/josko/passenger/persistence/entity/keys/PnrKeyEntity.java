package com.josko.passenger.persistence.entity.keys;

import jakarta.persistence.*;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;

import static com.josko.passenger.persistence.entity.keys.Key.Type.PNR;


@Entity
@IdClass(PnrKeyEntity.CompositeId.class)
@Table(name = "pax_data_passenger_key_pnr")
@Getter
@Setter
@EqualsAndHashCode(callSuper = false)
public class PnrKeyEntity extends KeyEntity {

    @Override
    public Type getType() {
        return PNR;
    }

    @Data
    public static class CompositeId {
        private String locator;
        private String firstName;
        private String lastName;
    }

    @Id
    @Column(name = "locator")
    private String locator;

    @Id
    @Column(name = "first_name")
    private String firstName;

    @Id
    @Column(name = "last_name")
    private String lastName;

    @Override
    public String toString() {
        return "{<" + firstName + " " + lastName + ">@" + locator+ '}';
    }
}
