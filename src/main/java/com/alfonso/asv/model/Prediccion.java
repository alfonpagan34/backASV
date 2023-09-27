package com.alfonso.asv.model;

import lombok.Data;
import lombok.Getter;

import java.util.ArrayList;

@Data
public class Prediccion {
    public ArrayList<Dium> dia;

    public Dium getDia(int index) {
        return this.dia.get(index);
    }

}
