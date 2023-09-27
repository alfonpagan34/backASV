package com.alfonso.asv.model;

import lombok.Data;
import lombok.Value;

import java.util.ArrayList;
import java.util.Date;

@Data
public class Dium {
    public ArrayList<EstadoCielo> estadoCielo;
    public ArrayList<Precipitacion> precipitacion;
    public ArrayList<ProbPrecipitacion> probPrecipitacion;
    public ArrayList<ProbTormentum> probTormenta;
    public ArrayList<Nieve> nieve;
    public ArrayList<ProbNieve> probNieve;
    public ArrayList<Temperatura> temperatura;
    public ArrayList<SensTermica> sensTermica;
    public ArrayList<HumedadRelativa> humedadRelativa;
    public ArrayList<VientoAndRachaMax> vientoAndRachaMax;
    public Date fecha;
    public String orto;
    public String ocaso;

    public float calcularTemperaturaMedia() {
        float temperaturaMedia = 0;
        for (Temperatura temperatura1 : temperatura) {
            temperaturaMedia+=Float.valueOf(temperatura1.value);
        }
        return temperaturaMedia/temperatura.size();
    }
}
