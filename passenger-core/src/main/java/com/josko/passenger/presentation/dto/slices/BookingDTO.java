package com.josko.passenger.presentation.dto.slices;

import com.fasterxml.jackson.annotation.JsonTypeName;
import lombok.*;

import java.io.Serial;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@JsonTypeName("BOOKING")
@EqualsAndHashCode(of = {"locator", "tattoo"})
public class BookingDTO implements SliceDataDTO {
    @Serial
    private static final long serialVersionUID = 1L;

    private String locator;
    private Integer tattoo;
    private String pointOfSale;

    @Override
    public boolean isSliceDataEmpty() {
        return isBlank(locator);
    }
}
