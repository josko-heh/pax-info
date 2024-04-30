package com.josko.passenger.update.slices;

import lombok.*;

import java.io.Serial;

import static org.apache.commons.lang3.StringUtils.isBlank;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@EqualsAndHashCode(of = {"locator", "tattoo"})
public class BookingData implements SliceData {
    @Serial
    private static final long serialVersionUID = 1L;

    private String locator;
    private Integer tattoo;
    private String pointOfSale;

    @Override
    public boolean isEmpty() {
        return isBlank(locator);
    }
}
