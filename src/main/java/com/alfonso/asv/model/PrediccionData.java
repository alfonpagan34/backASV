package com.alfonso.asv.model;

import lombok.Data;
import java.util.Date;

@Data
public class PrediccionData {
    public Origen origen;
    public Date elaborado;
    public String nombre;
    public String provincia;
    public Prediccion prediccion;
    public String id;
    public String version;
}

