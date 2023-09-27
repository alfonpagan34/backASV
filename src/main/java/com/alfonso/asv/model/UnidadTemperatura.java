package com.alfonso.asv.model;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public enum UnidadTemperatura {
    CELSIUS("G_CEL",0),
    FAHRENHEIT("G_FAH",32),
    KELVIN("G_KEL",273);

    private String id;
    private int value;
}
