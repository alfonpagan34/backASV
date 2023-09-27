package com.alfonso.asv.service;

import com.alfonso.asv.model.Municipio;
import com.alfonso.asv.model.PrediccionData;
import com.alfonso.asv.model.PrediccionResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AsvService {

    @Autowired
    AemetService aemetService;

    public List<Municipio> getMunicipiosByNombre(String nombre) {
        return aemetService.getMunicipios().stream().filter(municipio -> municipio.getNombre().contains(nombre))
                .collect(Collectors.toList());
    }

    public PrediccionResponse getPrediccion(String codigoMunicipio, String unidad) {
        return aemetService.getPrediccion(codigoMunicipio,unidad);
    }


}
