package com.alfonso.asv.model;

import com.alfonso.asv.model.ProbPrecipitacion;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.ArrayList;

@Data
@AllArgsConstructor
public class PrediccionResponse {
    private float temperatura;
    private String unidadTemperatura;
    private ArrayList<ProbPrecipitacion> probPrecipitacion;
}
